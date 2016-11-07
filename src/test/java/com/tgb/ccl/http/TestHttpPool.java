package com.tgb.ccl.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;

import com.tgb.ccl.http.common.HttpConfig;
import com.tgb.ccl.http.common.HttpHeader;
import com.tgb.ccl.http.exception.HttpProcessException;
import com.tgb.ccl.http.httpclient.HttpClientUtil;
import com.tgb.ccl.http.httpclient.builder.HCB;

/**
 * 测试启用http连接池
 * 
 * @author arron
 * @date 2016年11月7日 下午1:08:07 
 * @version 1.0
 */
public class TestHttpPool {
	
	// 设置header信息
	private static final Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0").from("http://blog.csdn.net/newest.html").build();
	
	// URL列表数组，GET请求
	private static final String[] urls = {
			"http://blog.csdn.net/xiaoxian8023/article/details/49883113",
			"http://blog.csdn.net/xiaoxian8023/article/details/49909359",
			"http://blog.csdn.net/xiaoxian8023/article/details/49910127",
			"http://blog.csdn.net/xiaoxian8023/article/details/49910885",
			"http://blog.csdn.net/xiaoxian8023/article/details/51606865",
	};
	
	// 图片URL列表数组，Down操作
	private static final String[] imgurls ={
			"http://ss.bdimg.com/static/superman/img/logo/logo_white_fe6da1ec.png",
			"https://scontent-hkg3-1.xx.fbcdn.net/hphotos-xaf1/t39.2365-6/11057093_824152007634067_1766252919_n.png"
	};
	
	private static StringBuffer buf1=new StringBuffer();
	private static StringBuffer buf2=new StringBuffer();
	
	//多线程get请求
	public static void testMultiGet(HttpConfig cfg, int count) throws HttpProcessException{
	        try {
				int pagecount = urls.length;
				ExecutorService executors = Executors.newFixedThreadPool(pagecount);
				CountDownLatch countDownLatch = new CountDownLatch(count);   
				//启动线程抓取
				for(int i = 0; i< count;i++){
				    executors.execute(new GetRunnable(countDownLatch,cfg.headers(headers).url(urls[i%pagecount])));
				}
				countDownLatch.await();
				executors.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
	        }
	}
	
	//多线程下载
	public static void testMultiDown(HttpConfig cfg, int count) throws HttpProcessException{
		try {
			int pagecount = imgurls.length;
			ExecutorService executors = Executors.newFixedThreadPool(pagecount);
			CountDownLatch countDownLatch = new CountDownLatch(count);   
			//启动线程抓取
			for(int i = 0; i< count;i++){
			    executors.execute(new GetRunnable(countDownLatch, cfg.url(imgurls[i%2]), new FileOutputStream(new File("d://aaa//"+(i+1)+".png"))));
			}
			countDownLatch.await();
			executors.shutdown();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	 static class GetRunnable implements Runnable {
	        private CountDownLatch countDownLatch;
	        private HttpConfig config = null;
	        private FileOutputStream out = null;

	        public GetRunnable(CountDownLatch countDownLatch,HttpConfig config){
	           this(countDownLatch, config, null);
	        }
	        public GetRunnable(CountDownLatch countDownLatch,HttpConfig config,FileOutputStream out){
	        	this.countDownLatch = countDownLatch;
	        	this.config = config;
	        	this.out = out;
	        }
	        
	        @Override
	        public void run() {
	            try {
	            	config.out(out);
	            	if(config.out()==null){
	            		String response = null;
	            		response =  HttpClientUtil.get(config);
	            		System.out.println(Thread.currentThread().getName()+"--获取内容长度："+response.length());
	            		response = null;

	            	}else{
	            		HttpClientUtil.down(config);
	            		try {
							config.out().flush();
							config.out().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
	            		System.out.println(Thread.currentThread().getName()+"--下载完毕");
	            	}
	            } catch (HttpProcessException e) {
					e.printStackTrace();
				} finally {
	                countDownLatch.countDown();
	            }
	        }
	    }  


	/**
	 * 测试不启用http连接池，get100次，down20次的执行时间
	 * @throws HttpProcessException
	 */
	private static void testNoPool(int getCount, int downCount) throws HttpProcessException {
		long start = System.currentTimeMillis();

		if(getCount>0){
			HttpConfig cfg1 = HttpConfig.custom().client(HCB.custom().build()).headers(headers);
			testMultiGet(cfg1, getCount);
		}
		if(downCount>0){
			HttpConfig cfg2 = HttpConfig.custom().client(HCB.custom().build());
			testMultiDown(cfg2, downCount);
		}
		
		System.out.println("-----所有线程已完成！------");
        long end = System.currentTimeMillis();
        System.out.println("总耗时（毫秒）： -> " + (end - start));
        buf1.append("\t").append((end-start));
	}

	
	/**
	 * 测试启用http连接池，get100次，down20次的执行时间
	 * @throws HttpProcessException
	 */
	private static void testByPool(int getCount, int downCount) throws HttpProcessException {
		long start = System.currentTimeMillis();
		
		HCB hcb= HCB.custom().timeout(10000).pool(10, 10).ssl();
		
		if(getCount>0){
			HttpConfig cfg3 = HttpConfig.custom().hcb(hcb);
			testMultiGet(cfg3, getCount);
		}
		if(downCount>0){
			HttpConfig cfg4 = HttpConfig.custom().hcb(hcb);
			testMultiDown(cfg4, downCount);
		}

		System.out.println("-----所有线程已完成！------");
        long end = System.currentTimeMillis();
        System.out.println("总耗时（毫秒）： -> " + (end - start));
        buf2.append("\t").append((end-start));
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("d://aaa");
		if(!file.exists() && file.isDirectory()){
			file.mkdir();
		}
		
		//-------------------------------------------
		//  以下2个方法
		//  分别测试 （get次数，down次数） 
		//  {100,0},{200,0},{500,0},{1000,0}
		//  {0,10},{0,20},{0,50},{0,100}
		//  {100,10},{200,20},{500,50},{1000,100}
		//-------------------------------------------
		
		int[][] times1 = {{100,0} ,{ 200,0 },{ 500,0 },{ 1000,0}};
		int[][] times2 = {{0,10},{0,20},{0,50},{0,100}};
		int[][] times3 = {{100,10},{200,20},{500,50},{1000,100}};
		List<int[][]> list = Arrays.asList(times1,times2,times3);
		int n=5;
		
		int t=0;
		//测试未启用http连接池，
		for (int[][] time : list) {
			buf1.append("\n");
			for (int i = 0; i < time.length; i++) {
				for (int j = 0; j < n; j++) {
					testNoPool(time[i][0],time[i][1]);
					Thread.sleep(100);
					System.gc();
					Thread.sleep(100);
				}
				buf1.append("\n");
			}
		}

		t=0;
		//测试启用http连接池
		for (int[][] time : list) {
			buf2.append("\n");
			for (int i = 0; i < time.length; i++) {
				for (int j = 0; j < n; j++) {
					testByPool(time[i][0],time[i][1]);
					Thread.sleep(100);
					System.gc();
					Thread.sleep(100);
				}
				buf2.append("\n");
			}
			t++;
		}
		
		//把结果打印到Console中
		String[] results1 = buf1.toString().split("\n");
		String[] results2 = buf2.toString().split("\n");
		
		for (int i = 0; i < results1.length; i++) {
			System.out.println(results1[i]);
			System.out.println(results2[i]);
		}
		
	}
}