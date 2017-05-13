package com.arronlong.httpclientutil.common;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

/** 
 * 封装Cookie
 * 
 * @author arron
 * @date 2016年1月12日 上午8:42:13 
 * @version 1.0 
 */
public class HttpCookies {

	/**
	 * 使用httpcontext，用于设置和携带Cookie
	 */
	private HttpClientContext context ;
	
	/**
	 * 储存Cookie
	 */
	private CookieStore cookieStore;
	
	public static HttpCookies custom(){
		return new HttpCookies();
	}
	
	private HttpCookies(){
		this.context = new HttpClientContext();
		this.cookieStore = new BasicCookieStore();
		this.context.setCookieStore(cookieStore);
	}
	
	public HttpClientContext getContext() {
		return context;
	}
	
	public HttpCookies setContext(HttpClientContext context) {
		this.context = context;
		return this;
	}
	
	public CookieStore getCookieStore() {
		return cookieStore;
	}
	
	public HttpCookies setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
		return this;
	}
	
	
}
