package com.tgb.ccl.http.common;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;

import com.tgb.ccl.http.httpclient.HttpAsyncClientUtil.IHandler;

/** 
 * 请求配置类
 * 
 * @author arron
 * @date 2016年2月2日 下午3:14:32 
 * @version 1.0 
 */
public class HttpConfig {
	
	private HttpConfig(){};
	
	/**
	 * 获取实例
	 * @return
	 */
	public static HttpConfig custom(){
		return new HttpConfig();
	}

	/**
	 * HttpClient对象
	 */
	private HttpClient client;

	/**
	 * CloseableHttpAsyncClient对象
	 */
	private CloseableHttpAsyncClient asynclient;
	
	/**
	 * 资源url
	 */
	private String url;

	/**
	 * Header头信息
	 */
	private Header[] headers;
	
	/**
	 * 是否返回response的headers
	 */
	private boolean isReturnRespHeaders;

	/**
	 * 请求方法
	 */
	private HttpMethods method=HttpMethods.GET;
	
	/**
	 * 请求方法名称
	 */
	private String methodName;

	/**
	 * 用于cookie操作
	 */
	private HttpContext context;

	/**
	 * 传递参数
	 */
	private Map<String, Object> map;

	/**
	 * 输入输出编码
	 */
	private String encoding=Charset.defaultCharset().displayName();

	/**
	 * 输入编码
	 */
	private String inenc;

	/**
	 * 输出编码
	 */
	private String outenc;
	
	/**
	 * 输出流对象
	 */
	private OutputStream out;
	
	/**
	 * 异步操作回调执行器
	 */
	private IHandler handler;

	/**
	 * HttpClient对象
	 */
	public HttpConfig client(HttpClient client) {
		this.client = client;
		return this;
	}
	
	/**
	 * CloseableHttpAsyncClient对象
	 */
	public HttpConfig asynclient(CloseableHttpAsyncClient asynclient) {
		this.asynclient = asynclient;
		return this;
	}
	
	/**
	 * 资源url
	 */
	public HttpConfig url(String url) {
		this.url = url;
		return this;
	}
	
	/**
	 * Header头信息
	 */
	public HttpConfig headers(Header[] headers) {
		this.headers = headers;
		return this;
	}
	
	/**
	 * Header头信息(是否返回response中的headers)
	 */
	public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
		this.headers = headers;
		this.isReturnRespHeaders=isReturnRespHeaders;
		return this;
	}
	
	/**
	 * 请求方法
	 */
	public HttpConfig method(HttpMethods method) {
		this.method = method;
		return this;
	}
	
	/**
	 * 请求方法
	 */
	public HttpConfig methodName(String methodName) {
		this.methodName = methodName;
		return this;
	}
	
	/**
	 * cookie操作相关
	 */
	public HttpConfig context(HttpContext context) {
		this.context = context;
		return this;
	}
	
	/**
	 * 传递参数
	 */
	public HttpConfig map(Map<String, Object> map) {
		this.map = map;
		return this;
	}
	
	/**
	 * 输入输出编码
	 */
	public HttpConfig encoding(String encoding) {
		//设置输入输出
		inenc(encoding);
		outenc(encoding);
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * 输入编码
	 */
	public HttpConfig inenc(String inenc) {
		this.inenc = inenc;
		return this;
	}
	
	/**
	 * 输出编码
	 */
	public HttpConfig outenc(String outenc) {
		this.outenc = outenc;
		return this;
	}
	
	/**
	 * 输出流对象
	 */
	public HttpConfig out(OutputStream out) {
		this.out = out;
		return this;
	}
	
	/**
	 * 异步操作回调执行器
	 */
	public HttpConfig handler(IHandler handler) {
		this.handler = handler;
		return this;
	}


	public HttpClient client() {
		return client;
	}
	
	public CloseableHttpAsyncClient asynclient() {
		return asynclient;
	}
	
	public Header[] headers() {
		return headers;
	}
	public boolean isReturnRespHeaders() {
		return isReturnRespHeaders;
	}
	
	public String url() {
		return url;
	}

	public HttpMethods method() {
		return method;
	}

	public String methodName() {
		return methodName;
	}

	public HttpContext context() {
		return context;
	}

	public Map<String, Object> map() {
		return map;
	}

	public String encoding() {
		return encoding;
	}

	public String inenc() {
		return inenc == null ? encoding : inenc;
	}

	public String outenc() {
		return outenc == null ? encoding : outenc;
	}
	
	public OutputStream out() {
		return out;
	}
	
	public IHandler handler() {
		return handler;
	}
}