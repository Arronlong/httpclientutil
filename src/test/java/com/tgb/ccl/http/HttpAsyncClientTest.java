package com.tgb.ccl.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.log4j.Logger;

import com.tgb.ccl.http.common.HttpConfig;
import com.tgb.ccl.http.common.HttpHeader;
import com.tgb.ccl.http.common.HttpHeader.Headers;
import com.tgb.ccl.http.exception.HttpProcessException;
import com.tgb.ccl.http.httpclient.HttpAsyncClientUtil;
import com.tgb.ccl.http.httpclient.HttpAsyncClientUtil.IHandler;
import com.tgb.ccl.http.httpclient.builder.HACB;

/** 
 * 
 * @author arron
 * @date 2015年11月1日 下午2:23:18 
 * @version 1.0 
 */
public class HttpAsyncClientTest {
	private static final Logger logger = Logger.getLogger(HttpAsyncClientTest.class);
	private static AsyncHandler handler = new AsyncHandler();
	
	static class AsyncHandler implements IHandler{
	    private CountDownLatch countDownLatch;
		public AsyncHandler(){
		}
		public AsyncHandler(CountDownLatch countDownLatch){
			this.countDownLatch = countDownLatch;
		}
		public AsyncHandler setCountDownLatch(CountDownLatch countDownLatch){
			this.countDownLatch = countDownLatch;
			return this;
		}
		public void countDown(){
			if(countDownLatch==null) return;
			countDownLatch.countDown();
		}
		@Override
		public Object failed(Exception e) {
			logger.error(Thread.currentThread().getName()+"--失败了--"+e.getClass().getName()+"--"+e.getMessage());
			countDown();
			return null;
		}
		@Override
		public Object completed(String respBody) {
			logger.info(Thread.currentThread().getName()+"--获取内容长度："+respBody.length());
			countDown();
			return null;
		}
		@Override
		public Object cancelled() {
			logger.info(Thread.currentThread().getName()+"--取消了");
			countDown();
			return null;
		}
		@Override
		public Object down(OutputStream out) {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info(Thread.currentThread().getName()+"--下载完成");
			return null;
		}
	}
	
	
	public static void testOne() throws HttpProcessException{
		System.out.println("---------简单测试---------"); 
		String url="http://blog.csdn.net/xiaoxian8023";
		//设置header信息
		Header[] headers2=HttpHeader.custom().userAgent("Mozilla/5.0 Chrome/48.0.2564.109 Safari/537.36").build();
		//执行
		HttpAsyncClientUtil.get(HttpConfig.custom().url(url).headers(headers2).handler(handler));//推荐使用get方法，而非模糊的get，get默认使用get方法提交
		
		//测试代理
		System.out.println("---------测试代理---------"); 
		url="https://www.facebook.com/";
		//自定义CloseableHttpAsyncClient，设置超时、代理、ssl
		CloseableHttpAsyncClient client= HACB.custom().timeout(10000).proxy("127.0.0.1", 8087).ssl().build();
		//设置header信息
		Header[] headers=HttpHeader.custom().keepAlive("false").contentType(Headers.APP_FORM_URLENCODED).build();
		//执行
//		CopyOfHttpAsyncClientUtil.get(client, url, headers, handler.setCountDownLatch(null));
		HttpAsyncClientUtil.get(HttpConfig.custom().asynclient(client).url(url).headers(headers).handler(handler));
		
		//测试下载
		System.out.println("-----------测试下载-------------------");
		url="https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png";
		try {
			FileOutputStream out = new FileOutputStream(new File("d://aaa//0.png"));
			HttpAsyncClientUtil.down(HttpConfig.custom().url(url).handler(handler).out(out));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("---------the end---------"); 
	}
	
	
	
	public static void testMutilTask() throws HttpProcessException{
		// URL列表数组
		String[] urls = {
				"http://blog.csdn.net/xiaoxian8023/article/details/49862725",
				"http://blog.csdn.net/xiaoxian8023/article/details/49834643",
				"http://blog.csdn.net/xiaoxian8023/article/details/49834615",
				"http://blog.csdn.net/xiaoxian8023/article/details/49834589",
				"http://blog.csdn.net/xiaoxian8023/article/details/49785417",
				
//				"http://blog.csdn.net/xiaoxian8023/article/details/48679609",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48681987",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48710653",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48729479",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48733249",
//
//				"http://blog.csdn.net/xiaoxian8023/article/details/48806871",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48826857",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49663643",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49619777",
//				"http://blog.csdn.net/xiaoxian8023/article/details/47335659",
//
//				"http://blog.csdn.net/xiaoxian8023/article/details/47301245",
//				"http://blog.csdn.net/xiaoxian8023/article/details/47057573",
//				"http://blog.csdn.net/xiaoxian8023/article/details/45601347",
//				"http://blog.csdn.net/xiaoxian8023/article/details/45569441",
//				"http://blog.csdn.net/xiaoxian8023/article/details/43312929",
				};
		
		String[] imgurls ={"http://ss.bdimg.com/static/superman/img/logo/logo_white_fe6da1ec.png",
		"https://scontent-hkg3-1.xx.fbcdn.net/hphotos-xaf1/t39.2365-6/11057093_824152007634067_1766252919_n.png"};
		
		// 设置header信息
		Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.1").build();
		long start = System.currentTimeMillis();
            try {
				int pagecount = urls.length;
				ExecutorService executors = Executors.newFixedThreadPool(pagecount);
				CountDownLatch countDownLatch = new CountDownLatch(pagecount*10);
				handler.setCountDownLatch(countDownLatch);
				for(int i = 0; i< pagecount*10;i++){
					CloseableHttpAsyncClient client= HACB.custom().timeout(10000).proxy("127.0.0.1", 8087).ssl().build();
					FileOutputStream out = new FileOutputStream(new File("d://aaa//"+(i+1)+".png"));
				    //启动线程抓取
				    executors.execute(new GetRunnable(HttpConfig.custom().url(urls[i%pagecount]).headers(headers).handler(handler)));
				    executors.execute(new GetRunnable(HttpConfig.custom().asynclient(client).url(imgurls[i%2]).headers(headers).out(out).handler(handler)));
				}
				countDownLatch.await();
				executors.shutdown();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
	        } finally {
	            System.out.println("线程" + Thread.currentThread().getName() + ", 所有线程已完成，开始进入下一步！");
	        }
	         
	        long end = System.currentTimeMillis();
	        System.out.println("总耗时（毫秒）： -> " + (end - start));
	        //(7715+7705+7616)/3= 23 036/3= 7 678.66---150=51.2
	        //(8250+8038+8401)/3=24 689/3=8 229.66--150
	        //(8244+8378+8188)/3=24 810/3= 8 270---150
	        //(9440+9601+9798+10139+16299)
	}
	
	 static class GetRunnable implements Runnable {
	        private HttpConfig config = null;
	        public GetRunnable(HttpConfig config){
	        	this.config = config;
	        }
	        
	        @Override
	        public void run() {
	            try {
	            	if(config.out()==null){
	            		HttpAsyncClientUtil.get(config);
	            	}else{
	            		HttpAsyncClientUtil.down(config);
	            	}
	            } catch (HttpProcessException e) {
	            }
	        }
	    }  
	
	public static void main(String[] args) throws Exception {
		File file = new File("d://aaa");
		if(!file.exists() && file.isDirectory()){
			file.mkdir();
		}
//		testOne();
		testMutilTask();
	}
}