package com.arronlong.httpclientutil.common;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

//import com.tgb.ccl.http.exception.HttpProcessException;
//import com.tgb.ccl.http.httpclient.builder.HCB;

/** 
 * 请求配置类
 * 
 * @author arron
 * @version 1.0 
 */
public class HttpConfig {
	
	private HttpConfig(){};
	
	/**
	 * 获取实例
	 * @return	返回当前对象
	 */
	public static HttpConfig custom(){
		return new HttpConfig();
	}

	/**
	 * HttpClient对象
	 */
	private HttpClient client;
	
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
	 * 用于cookie操作
	 */
	private HttpContext context;

	/**
	 * 传递参数
	 */
	private Map<String, Object> map = new HashMap<String, Object>(8);
	
	/**
	 * 以json格式作为输入参数
	 */
	private String json;

	/**
	 * 链接
	 */
	private String url;

	/**
	 * 输入输出编码
	 */
	private String encoding=Charset.defaultCharset().displayName();

	/**
	 * 输入编码
	 */
	private String inputEncoding;

	/**
	 * 输出编码
	 */
	private String outputEncoding;
	
	/**
	 * 设置RequestConfig
	 */
	private RequestConfig requestConfig;

	/**
	 * 解决多线程下载时，strean被close的问题
	 */
	private final ThreadLocal<OutputStream> outs = new ThreadLocal<OutputStream>();
	
	/**
	 * @param client	HttpClient对象
	 * @return 返回当前对象
	 */
	public HttpConfig client(HttpClient client) {
		this.client = client;
		return this;
	}
	
	/**
	 * @param url	资源url
	 * @return	返回当前对象
	 */
	public HttpConfig url(String url) {
		this.url = url;
		return this;
	}
	
	/**
	 * @param headers	Header头信息
	 * @return	返回当前对象
	 */
	public HttpConfig headers(Header[] headers) {
		this.headers = headers;
		return this;
	}
	
	/**
	 * Header头信息(是否返回response中的headers)
	 * 
	 * @param headers	Header头信息
	 * @param isReturnRespHeaders	是否返回response中的headers
	 * @return	返回当前对象
	 */
	public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
		this.headers = headers;
		this.isReturnRespHeaders=isReturnRespHeaders;
		return this;
	}
	
	/**
	 * @param method	请求方法
	 * @return	返回当前对象
	 */
	public HttpConfig method(HttpMethods method) {
		this.method = method;
		return this;
	}
	
	/**
	 * @param context	cookie操作相关
	 * @return	返回当前对象
	 */
	public HttpConfig context(HttpContext context) {
		this.context = context;
		return this;
	}
	
	/**
	 * @param map	传递参数
	 * @return	返回当前对象
	 */
	public HttpConfig map(Map<String, Object> map) {
		this.map = map;
		return this;
	}

	/**
	 * @param json	以json格式字符串作为参数
	 * @return	返回当前对象
	 */
	public HttpConfig json(String json) {
		this.json = json;
		return this;
	}
	
	/**
	 * @param filePaths	待上传文件所在路径
	 * @return	返回当前对象
	 */
	public HttpConfig files(String[] filePaths) {
		return files(filePaths, "file");
	}
	/**
	 * 上传文件时用到
	 * @param filePaths	待上传文件所在路径
	 * @param inputName	即file input 标签的name值，默认为file
	 * @return	返回当前对象
	 */
	public HttpConfig files(String[] filePaths, String inputName) {
		return files(filePaths, inputName, false);
	}
	/**
	 * 上传文件时用到
	 * @param filePaths		待上传文件所在路径
	 * @param inputName		即file input 标签的name值，默认为file
	 * @param forceRemoveContentTypeChraset	是否强制一处content-type中设置的编码类型
	 * @return	返回当前对象
	 */
	public HttpConfig files(String[] filePaths, String inputName, boolean forceRemoveContentTypeChraset) {
		this.map.put(Utils.ENTITY_MULTIPART, filePaths);
		this.map.put(Utils.ENTITY_MULTIPART+".name", inputName);
		this.map.put(Utils.ENTITY_MULTIPART+".rmCharset", forceRemoveContentTypeChraset);
		return this;
	}
	
	/**
	 * @param encoding	输入输出编码
	 * @return	返回当前对象
	 */
	public HttpConfig encoding(String encoding) {
		//设置输入输出
		inputEncoding(encoding);
		outputEncoding(encoding);
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * @param inputEncoding	输入编码
	 * @return	返回当前对象
	 */
	public HttpConfig inputEncoding(String inputEncoding) {
		this.inputEncoding = inputEncoding;
		return this;
	}
	
	/**
	 * @param outputEncoding	输出编码
	 * @return	返回当前对象
	 */
	public HttpConfig outputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
		return this;
	}
	
	/**
	 * @param out	输出流对象
	 * @return	返回当前对象
	 */
	public HttpConfig out(OutputStream out) {
		outs.set(out);
		return this;
	}
	
	/**
	 * 设置超时时间
	 * 
	 * @param timeout		超市时间，单位-毫秒
	 * @return	返回当前对象
	 */
	public HttpConfig timeout(int timeout){
		return timeout(timeout, true);
	}
	
	/**
	 * 设置超时时间以及是否允许网页重定向（自动跳转 302）
	 * 
	 * @param timeout		超时时间，单位-毫秒
	 * @param redirectEnable		自动跳转
	 * @return	返回当前对象
	 */
	public HttpConfig timeout(int timeout,  boolean redirectEnable){
		// 配置请求的超时设置
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.setRedirectsEnabled(redirectEnable)
				.build();
		return timeout(config);
	}
	
	/**
	 * 设置代理、超时时间、允许网页重定向等
	 * 
	 * @param requestConfig		超时时间，单位-毫秒
	 * @return	返回当前对象
	 */
	public HttpConfig timeout(RequestConfig requestConfig){
		this.requestConfig = requestConfig;
		return this;
	}
	
	public HttpClient client() {
		return client;
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


	public HttpContext context() {
		return context;
	}

	public Map<String, Object> map() {
		return map;
	}

	public String json() {
		return json;
	}
	
	public String encoding() {
		return encoding;
	}

	public String inputEncoding() {
		return inputEncoding == null ? encoding : inputEncoding;
	}

	public String outputEncoding() {
		return outputEncoding == null ? encoding : outputEncoding;
	}

	public OutputStream out() {
		return outs.get();
	}

	public RequestConfig requestConfig() {
		return requestConfig;
	}
}