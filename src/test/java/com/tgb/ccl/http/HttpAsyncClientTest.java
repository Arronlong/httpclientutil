package com.tgb.ccl.http;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.log4j.Logger;

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
	}
	
	
	public static void testOne() throws HttpProcessException{
		String url="https://www.facebook.com/";
		//自定义CloseableHttpAsyncClient，设置超时、代理、ssl
		CloseableHttpAsyncClient client= HACB.custom().timeout(10000).proxy("127.0.0.1", 8087).ssl().build();
		//设置header信息
		Header[] headers=HttpHeader.custom().keepAlive("false").contentType(Headers.APP_FORM_URLENCODED).build();
		//执行
		HttpAsyncClientUtil.send(client, url, headers, handler.setCountDownLatch(null));
		
		System.out.println("------------------------------");
		
		url="http://blog.csdn.net/xiaoxian8023";
		//设置header信息
		Header[] headers2=HttpHeader.custom().userAgent("Mozilla/5.0").build();
		//执行
		HttpAsyncClientUtil.send(url, headers2, handler);
		System.out.println("---------the end---------");
	}
	
	
	
	public static void testMutilTask(){
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
		// 设置header信息
		Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.1").build();
		//CloseableHttpAsyncClient client= MyHCB4HTTPS.custom().timeout(10000).build();
		long start = System.currentTimeMillis();
	        try {
	            int pagecount = urls.length;
	            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
	            CountDownLatch countDownLatch = new CountDownLatch(pagecount*10);
	            handler.setCountDownLatch(countDownLatch);
	            for(int i = 0; i< pagecount*10;i++){
	                //启动线程抓取
	                executors.execute(new GetRunnable(urls[i%pagecount], headers));
	            }
	            countDownLatch.await();
	            executors.shutdown();
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
	        private String url;
	        private Header[] headers;
	        private CloseableHttpAsyncClient client = null;
	        
	        public GetRunnable setClient(CloseableHttpAsyncClient client){
	        	this.client = client;
	        	return this;
	        }

	        public GetRunnable(String url, Header[] headers){
	        	this.url = url;
	        	this.headers = headers;
	        }
	        
	        @Override
	        public void run() {
	            try {
	            	if(client!=null){
	            		HttpAsyncClientUtil.send(client, url, headers, handler);
	            	}else{
	            		HttpAsyncClientUtil.send(url, headers, handler);
	            	}
	            } catch (HttpProcessException e) {
	            }
	        }
	    }  
	
	public static void main(String[] args) throws Exception {
//		testOne();
		testMutilTask();
	}
}