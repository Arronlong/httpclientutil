package com.tgb.ccl.http.httpclient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.tgb.ccl.http.common.HttpConfig;
import com.tgb.ccl.http.common.HttpMethods;
import com.tgb.ccl.http.common.Utils;
import com.tgb.ccl.http.exception.HttpProcessException;
import com.tgb.ccl.http.httpclient.builder.HCB;

/**
 * 使用HttpClient模拟发送（http/https）请求
 * 
 * @author arron
 * @date 2015年11月4日 下午4:10:59 
 * @version 1.0
 */
public class HttpClientUtil{
	
	//默认采用的http协议的HttpClient对象
	private static  HttpClient client4HTTP;
	
	//默认采用的https协议的HttpClient对象
	private static HttpClient client4HTTPS;
	
	static{
		try {
			client4HTTP = HCB.custom().build();
			client4HTTPS = HCB.custom().ssl().build();
		} catch (HttpProcessException e) {
			Utils.errorException("创建https协议的HttpClient对象出错：{}", e);
		}
	}
	
	/**
	 * 判定是否开启连接池、及url是http还是https <br>
	 * 		如果已开启连接池，则自动调用build方法，从连接池中获取client对象<br>
	 * 		否则，直接返回相应的默认client对象<br>
	 * 
	 * @throws HttpProcessException 
	 */
	private static void create(HttpConfig config) throws HttpProcessException  {
		if(config.hcb()!=null && config.hcb().isSetPool){ //如果设置了hcb对象，且配置了连接池，则直接从连接池取
			if(config.url().toLowerCase().startsWith("https://")){
				config.client(config.hcb().ssl().build());
			}else{
				config.client(config.hcb().build());
			}
		}else{
			if(config.client()==null){//如果为空，设为默认client对象
				if(config.url().toLowerCase().startsWith("https://")){
					config.client(client4HTTPS);
				}else{
					config.client(client4HTTP);
				}
			}
		}
	}
	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 以Get方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String get(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
		return get(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
	}
	/**
	 * 以Get方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String get(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.GET));
	}
	
	/**
	 * 以Post方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String post(HttpClient client, String url, Header[] headers, Map<String,Object>parasMap, HttpContext context, String encoding) throws HttpProcessException {
		return post(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
	}
	/**
	 * 以Post方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String post(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.POST));
	}
	
	/**
	 * 以Put方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String put(HttpClient client, String url, Map<String,Object>parasMap,Header[] headers, HttpContext context,String encoding) throws HttpProcessException {
		return put(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
	}
	/**
	 * 以Put方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String put(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.PUT));
	}
	
	/**
	 * 以Delete方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String delete(HttpClient client, String url, Header[] headers, HttpContext context,String encoding) throws HttpProcessException {
		return delete(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
	}
	/**
	 * 以Delete方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String delete(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.DELETE));
	}
	
	/**
	 * 以Patch方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String patch(HttpClient client, String url, Map<String,Object>parasMap, Header[] headers, HttpContext context,String encoding) throws HttpProcessException {
		return patch(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
	}
	/**
	 * 以Patch方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String patch(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.PATCH));
	}
	
	/**
	 * 以Head方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String head(HttpClient client, String url, Header[] headers, HttpContext context,String encoding) throws HttpProcessException {
		return head(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
	}
	/**
	 * 以Head方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String head(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.HEAD));
	}
	
	/**
	 * 以Options方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String options(HttpClient client, String url, Header[] headers, HttpContext context,String encoding) throws HttpProcessException {
		return options(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
	}
	/**
	 * 以Options方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String options(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.OPTIONS));
	}
	
	/**
	 * 以Trace方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String trace(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
		return trace(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
	}
	/**
	 * 以Trace方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @return
	 * @throws HttpProcessException
	 */
	public static String trace(HttpConfig config) throws HttpProcessException {
		return send(config.method(HttpMethods.TRACE));
	}
	
	/**
	 * 下载文件
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param out					输出流
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static OutputStream down(HttpClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
		return down(HttpConfig.custom().client(client).url(url).headers(headers).context(context).out(out));
	}
	
	/**
	 * 下载文件
	 * 
	 * @param config		请求参数配置
	 * @param out					输出流
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static OutputStream down(HttpConfig config) throws HttpProcessException {
		return fmt2Stream(execute(config.method(HttpMethods.GET)), config.out());
	}
	
	/**
	 * 上传文件
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String upload(HttpClient client, String url, Header[] headers, HttpContext context) throws HttpProcessException {
		return upload(HttpConfig.custom().client(client).url(url).headers(headers).context(context));
	}
	
	/**
	 * 上传文件
	 * 
	 * @param config		请求参数配置
	 * @return				返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String upload(HttpConfig config) throws HttpProcessException {
		if(config.method() != HttpMethods.POST  && config.method() != HttpMethods.PUT){
			config.method(HttpMethods.POST);
		}
		return send(config);
	}

	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 请求资源或服务
	 * 
	 * @param config
	 * @return
	 * @throws HttpProcessException
	 */
	public static String send(HttpConfig config) throws HttpProcessException {
		return fmt2String(execute(config), config.outenc());
	}
	
	/**
	 * 请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	private static HttpResponse execute(HttpConfig config) throws HttpProcessException {
		create(config);//获取链接
		HttpResponse resp = null;
		try {
			//创建请求对象
			HttpRequestBase request = getRequest(config.url(), config.method());
			
			//设置header信息
			request.setHeaders(config.headers());
			
			//判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
			if(HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				
				//检测url中是否存在参数
				config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));
				
				//装填参数
				HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());
				
				//设置参数到请求对象中
				((HttpEntityEnclosingRequestBase)request).setEntity(entity);
				
				Utils.info("请求地址："+config.url());
				if(nvps.size()>0){
					Utils.info("请求参数："+nvps.toString());
				}
			}else{
				int idx = config.url().indexOf("?");
				Utils.info("请求地址："+config.url().substring(0, (idx>0 ? idx : config.url().length())));
				if(idx>0){
					Utils.info("请求参数："+config.url().substring(idx+1));
				}
			}
			//执行请求操作，并拿到结果（同步阻塞）
			resp = (config.context()==null)?config.client().execute(request) : config.client().execute(request, config.context()) ;
			
			if(config.isReturnRespHeaders()){
				//获取所有response的header信息
				config.headers(resp.getAllHeaders());
			}
			
			//获取结果实体
			return resp;
			
		} catch (IOException e) {
			throw new HttpProcessException(e);
		}
	}
	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 转化为字符串
	 * 
	 * @param entity			实体
	 * @param encoding	编码
	 * @return
	 * @throws HttpProcessException 
	 */
	private static String fmt2String(HttpResponse resp, String encoding) throws HttpProcessException {
		String body = "";
		try {
			if (resp.getEntity() != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(resp.getEntity(), encoding);
				Utils.info(body);
			}
			EntityUtils.consume(resp.getEntity());
		} catch (IOException e) {
			throw new HttpProcessException(e);
		}finally{			
			close(resp);
		}
		return body;
	}
	
	/**
	 * 转化为流
	 * 
	 * @param entity			实体
	 * @param out				输出流
	 * @return
	 * @throws HttpProcessException 
	 */
	public static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws HttpProcessException {
		try {
			resp.getEntity().writeTo(out);
			EntityUtils.consume(resp.getEntity());
		} catch (IOException e) {
			throw new HttpProcessException(e);
		}finally{
			close(resp);
		}
		return out;
	}
	
	/**
	 * 根据请求方法名，获取request对象
	 * 
	 * @param url					资源地址
	 * @param method			请求方式
	 * @return
	 */
	private static HttpRequestBase getRequest(String url, HttpMethods method) {
		HttpRequestBase request = null;
		switch (method.getCode()) {
			case 0:// HttpGet
				request = new HttpGet(url);
				break;
			case 1:// HttpPost
				request = new HttpPost(url);
				break;
			case 2:// HttpHead
				request = new HttpHead(url);
				break;
			case 3:// HttpPut
				request = new HttpPut(url);
				break;
			case 4:// HttpDelete
				request = new HttpDelete(url);
				break;
			case 5:// HttpTrace
				request = new HttpTrace(url);
				break;
			case 6:// HttpPatch
				request = new HttpPatch(url);
				break;
			case 7:// HttpOptions
				request = new HttpOptions(url);
				break;
			default:
				request = new HttpPost(url);
				break;
		}
		return request;
	}
	
	/**
	 * 尝试关闭response
	 * 
	 * @param resp				HttpResponse对象
	 */
	private static void close(HttpResponse resp) {
		try {
			if(resp == null) return;
			//如果CloseableHttpResponse 是resp的父类，则支持关闭
			if(CloseableHttpResponse.class.isAssignableFrom(resp.getClass())){
				((CloseableHttpResponse)resp).close();
			}
		} catch (IOException e) {
			Utils.exception(e);
		}
	}
}