package com.arronlong.httpclientutil.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;

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
			"http://www.baidu.com/",
			"http://126.com",
	};
	
	// 图片URL列表数组，Down操作
	private static final String[] imgurls ={
			"http://ss.bdimg.com/static/superman/img/logo/logo_white_fe6da1ec.png",
			"https://scontent-hkg3-1.xx.fbcdn.net/hphotos-xaf1/t39.2365-6/11057093_824152007634067_1766252919_n.png"
	};
	
	private static StringBuffer buf=new StringBuffer();
	
	//多线程get请求
	public static void testMultiGet(HttpConfig cfg, int count) throws HttpProcessException{
	        try {
				int pagecount = urls.length;
				ExecutorService executors = Executors.newFixedThreadPool(pagecount);
				CountDownLatch countDownLatch = new CountDownLatch(count);   
				//启动线程抓取
				for(int i = 0; i< count;i++){
				    executors.execute(new GetRunnable(countDownLatch,cfg, urls[i%pagecount]));
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
			    executors.execute(new GetRunnable(countDownLatch, cfg, imgurls[i%2], new FileOutputStream(new File("d://aaa//"+(i+1)+".png"))));
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
	        private String url = null;

	        public GetRunnable(CountDownLatch countDownLatch,HttpConfig config, String url){
	           this(countDownLatch, config, url, null);
	        }
	        public GetRunnable(CountDownLatch countDownLatch,HttpConfig config, String url, FileOutputStream out){
	        	this.countDownLatch = countDownLatch;
	        	this.config = config;
	        	this.out = out;
	        	this.url = url;
	        }
	        
	        @Override
	        public void run() {
	            try {
	            	config.out(out);
	            	config.url(url);
	            	if(config.out()==null){
	            		String response = null;
	            		response =  HttpClientUtil.get(config);
	            		System.out.println(config.url());
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
	 * 测试启用http连接池，get100次，down20次的执行时间
	 * @throws HttpProcessException
	 */
	private static void testByPool(int getCount, int downCount) throws HttpProcessException {
		long start = System.currentTimeMillis();
		
		HCB hcb= HCB.custom().pool(100, 10).timeout(10000).ssl();
		if(getCount>0){
			HttpConfig cfg3 = HttpConfig.custom().client(hcb.build()).headers(headers);//使用一个client对象
			testMultiGet(cfg3, getCount);
		}
		if(downCount>0){
			HttpConfig cfg4 = HttpConfig.custom().client(hcb.build());
			testMultiDown(cfg4, downCount);
		}

		System.out.println("-----所有线程已完成！------");
        long end = System.currentTimeMillis();
        System.out.println("总耗时（毫秒）： -> " + (end - start));
        buf.append("\t").append((end-start));
	}
	
	/**
	 * 快速测试pool的应用(通过运行httpConn.bat监控http连接数，查看是否启用连接池)
	 * 
	 * @throws HttpProcessException
	 */
	public static void testquickConcurrent() throws HttpProcessException{
		//---------------------------
		//---  期望结果：
		//      由于urls中有3个域名，所以会为每个域名最多建立20个http链接，
		//      通过上面的监控，应该会看到http连接数会增加3-20=60个左右
		//---------------------------

		HttpClient client= HCB.custom().pool(100, 20).timeout(10000).build();//最多创建20个http链接
		final HttpConfig cfg = HttpConfig.custom().client(client).headers(headers);//为每次请求创建一个实例化对象
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						int idx = ((int) (Math.random() * 10)) % 5;
						HttpClientUtil.get(cfg.url(urls[idx]));
						System.out.println("---idx="+idx);
					} catch (HttpProcessException e) {
					}
				}
			}).start();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//以下测试通过使用线程池和未启用线程池2个方式测试http连接池
		//通过监控http链接查看连接池是否有效，脚本文件是httpConn.bat
		
		//未启用线程池，直接启用100个线程，通过监控http连接数，查看连接池是否跟配置的一致
		testquickConcurrent();
		
		//启用连接池，访问500次（线程数=urls的元素个数），测试连接池
		testByPool(500, 0);
	}
}