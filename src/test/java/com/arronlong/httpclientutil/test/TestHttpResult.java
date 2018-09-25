package com.arronlong.httpclientutil.test;

import org.apache.http.client.config.RequestConfig;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.HttpResult;
import com.arronlong.httpclientutil.exception.HttpProcessException;

public class TestHttpResult {

	public static void main(String[] args) throws HttpProcessException {
		
		final String url = "http://jd.com/?"+Math.random();
//		final String url2 = "https://www.facebook.com/?"+Math.random();
		// 配置请求的超时设置
		int timeout=1000*7;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();
		final HttpConfig config = HttpConfig.custom().headers(HttpHeader.custom().build(), true);
		config.timeout(requestConfig);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				long t1 = System.currentTimeMillis();
//				try {
//					HttpClientUtil.get(config.url(url));
//					long t2 = System.currentTimeMillis();
//					System.err.println("2耗费："+(t2-t1));
//					HttpClientUtil.get(config.url(url2));
//					long t3 = System.currentTimeMillis();
//					System.err.println("3耗费："+(t3-t1));
//				} catch (HttpProcessException e) {
////					e.printStackTrace();
//					System.err.println(e.getMessage());
//				} finally {
//					System.err.println("===finally===");
//				}
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				long t1 = System.currentTimeMillis();
//				try {
//					HttpClientUtil.get(config.url(url));
//					long t2 = System.currentTimeMillis();
//					System.err.println("2耗费："+(t2-t1));
//					HttpClientUtil.get(config.url(url2));
//					long t3 = System.currentTimeMillis();
//					System.err.println("3耗费："+(t3-t1));
//				} catch (HttpProcessException e) {
////					e.printStackTrace();
//					System.err.println(e.getMessage());
//				} finally {
//					System.err.println("===finally===");
//				}
//			}
//		}).start();
//		String result = HttpClientUtil.get(config);
//		System.out.println(result);
//		for (Header header : config.headers()) {
//			System.out.println(header);
//		}
		
		//测试HttpResult返回方式
		
		long t1 = System.currentTimeMillis();
		
		HttpResult result = HttpClientUtil.sendAndGetResp(config.url(url));
		
		long t2 = System.currentTimeMillis();
		System.out.println("返回结果（内容太长，仅打印字节数）："+result.getResult().length());
		System.out.println("状态码："+result.getStatusCode());
		System.out.println("使用协议："+result.getProtocolVersion());
		System.out.println("----------");
		System.out.println("耗时："+(t2-t1));
	}
	
}
