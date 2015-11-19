package com.tgb.ccl.http.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

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
	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	//默认采用的http协议的HttpClient对象
	private static  HttpClient client4HTTP;
	
	//默认采用的https协议的HttpClient对象
	private static HttpClient client4HTTPS;
	
	static{
		try {
			client4HTTP = HCB.custom().build();
			client4HTTPS = HCB.custom().ssl().build();
		} catch (HttpProcessException e) {
			logger.error("创建https协议的HttpClient对象出错：{}", e);
		}
	}
	
	/**
	 * 判断url是http还是https，直接返回相应的默认client对象
	 * 
	 * @return						返回对应默认的client对象
	 * @throws HttpProcessException 
	 */
	private static HttpClient create(String url) throws HttpProcessException  {
		if(url.toLowerCase().startsWith("https://")){
			return client4HTTPS;
		}else{
			return client4HTTP;
		}
	}
	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 默认使用Post方式，请求资源或服务
	 * 
	 * @param url					资源地址
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url) throws HttpProcessException {
		return send(url, Charset.defaultCharset().name());
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, String encoding) throws HttpProcessException {
		return send(url, new Header[]{},encoding);
	}

	/**
	 * 默认使用Post方式，请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Header[] headers) throws HttpProcessException {
		return send(url, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Header[] headers, String encoding) throws HttpProcessException {
		return send(url, new HashMap<String,String>(), headers, encoding);
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，传入请求参数
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Map<String,String>parasMap) throws HttpProcessException {
		return send(url, parasMap, Charset.defaultCharset().name());
	}

	/**
	 * 默认使用Post方式，请求资源或服务，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Map<String,String>parasMap, String encoding) throws HttpProcessException {
		return send(url, parasMap, new Header[]{}, encoding);
	}

	/**
	 * 默认使用Post方式，请求资源或服务，设定内容类型，并传入请求参数
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Map<String,String>parasMap, Header[] headers) throws HttpProcessException {
		return send(url, parasMap, headers, Charset.defaultCharset().name());
	}

	/**
	 * 默认使用Post方式，请求资源或服务，设定内容类型，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, Map<String,String>parasMap, Header[] headers, String encoding) throws HttpProcessException {
		return send(url, HttpMethods.POST, parasMap, headers, encoding);
	}
		
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url) throws HttpProcessException {
		return send(client, url, Charset.defaultCharset().name());
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, String encoding) throws HttpProcessException {
		return send(client, url, new Header[]{}, encoding);
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Header[] headers) throws HttpProcessException {
		return send(client, url, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Header[] headers, String encoding) throws HttpProcessException {
		return send(client, url, new HashMap<String, String>(), headers, encoding);
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，并传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Map<String,String>parasMap) throws HttpProcessException {
		return send(client, url, parasMap, Charset.defaultCharset().name());
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Map<String,String>parasMap, String encoding) throws HttpProcessException {
		return send(client, url, parasMap, new Header[]{}, encoding);
	}
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，设置内容类型，传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Map<String,String>parasMap, Header[] headers) throws HttpProcessException {
		return send(client, url, parasMap, headers, Charset.defaultCharset().name());
	}
	
	
	/**
	 * 默认使用Post方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.POST, parasMap, headers, encoding);
	}

	//------↑上面的方法，默认采用Post方式提交-------
	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------

	//--------↓下面的方法，需要指定请求方式----------

	/**
	 * 请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod) throws HttpProcessException {
		return send(url, httpMethod, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, String encoding) throws HttpProcessException {
		return send(url, httpMethod, new Header[]{},encoding);
	}
	
	/**
	 * 请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Header[] headers) throws HttpProcessException {
		return send(url, httpMethod, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Header[] headers, String encoding) throws HttpProcessException {
		return send(url, httpMethod, new HashMap<String, String>(), headers, encoding);
	}
	
	/**
	 * 请求资源或服务，传入请求参数
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Map<String,String>parasMap) throws HttpProcessException {
		return send(url, httpMethod, parasMap, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Map<String,String>parasMap, String encoding) throws HttpProcessException {
		return send(url, httpMethod, parasMap, new Header[]{}, encoding);
	}
	
	/**
	 * 请求资源或服务，设定内容类型，并传入请求参数
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers) throws HttpProcessException {
		return send(url, httpMethod, parasMap, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，设定内容类型，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers, String encoding) throws HttpProcessException {
		return send(create(url), url, httpMethod, parasMap, headers, encoding);
	}
	
	
	/**
	 * 请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @return						返回处理结果
	 * @throws HttpProcessException
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod) throws HttpProcessException {
		return send(client, url, httpMethod, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, String encoding) throws HttpProcessException {
		return send(client, url, httpMethod, new Header[]{}, encoding);
	}
	
	/**
	 * 请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Header[] headers) throws HttpProcessException {
		return send(client, url, httpMethod, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Header[] headers, String encoding) throws HttpProcessException {
		return send(client, url, httpMethod, new HashMap<String, String>(), headers, encoding);
	}
	
	/**
	 * 请求资源或服务，自定义client对象，并传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap) throws HttpProcessException {
		return send(client, url, httpMethod, parasMap, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，自定义client对象，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap, String encoding) throws HttpProcessException {
		return send(client, url, httpMethod, parasMap, new Header[]{}, encoding);
	}
	
	/**
	 * 请求资源或服务，自定义client对象，设置内容类型，传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers) throws HttpProcessException {
		return send(client, url, httpMethod, parasMap, headers, Charset.defaultCharset().name());
	}
	
	/**
	 * 请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
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
	public static String send(HttpClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		String body = "";
		try {
			//创建请求对象
			HttpRequestBase request = getRequest(url, httpMethod);
			
			//设置header信息
			request.setHeaders(headers);
			
			//判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
			if(HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				
				//检测url中是否存在参数
				url = Utils.checkHasParas(url, nvps);
				
				//装填参数
				Utils.map2List(nvps, parasMap);
				
				//设置参数到请求对象中
				((HttpEntityEnclosingRequestBase)request).setEntity(new UrlEncodedFormEntity(nvps, encoding));
				
				logger.info("请求地址："+url);
				if(nvps.size()>0){
					logger.info("请求参数："+nvps.toString());
				}
			}else{
				int idx = url.indexOf("?");
				logger.info("请求地址："+url.substring(0, (idx>0 ? idx-1:url.length()-1)));
				if(idx>0){
					logger.info("请求参数："+url.substring(idx+1));
				}
			}
			
			//调用发送请求
			body = execute(client, request, url, encoding);
			
		} catch (UnsupportedEncodingException e) {
			throw new HttpProcessException(e);
		}
		return body;
	}
	

	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String get(String url, Header[] headers,String encoding) throws HttpProcessException {
		return get(create(url), url, headers, encoding);
	}
	
	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String get(HttpClient client, String url, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.GET, headers, encoding);
	}
	
	/**
	 * 以Post方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String post(String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return post(create(url), url, parasMap, headers, encoding);
	}
	
	/**
	 * 以Post方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String post(HttpClient client, String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.POST, parasMap, headers, encoding);
	}
	
	/**
	 * 以Put方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String put(String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return put(create(url), url, parasMap, headers, encoding);
	}
	
	/**
	 * 以Put方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String put(HttpClient client, String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.PUT, parasMap, headers, encoding);
	}
	
	/**
	 * 以Delete方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String delete(String url, Header[] headers,String encoding) throws HttpProcessException {
		return delete(create(url), url, headers, encoding);
	}
	
	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String delete(HttpClient client, String url, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.DELETE, headers, encoding);
	}
	
	/**
	 * 以Patch方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String patch(String url, Map<String,String>parasMap,Header[] headers,String encoding) throws HttpProcessException {
		return patch(create(url), url, parasMap, headers, encoding);
	}
	
	/**
	 * 以Patch方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String patch(HttpClient client, String url, Map<String,String>parasMap, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.PATCH, parasMap, headers, encoding);
	}
	
	/**
	 * 以Head方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String head(String url, Header[] headers,String encoding) throws HttpProcessException {
		return head(create(url), url, headers, encoding);
	}
	
	/**
	 * 以Head方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String head(HttpClient client, String url, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.HEAD, headers, encoding);
	}
	
	/**
	 * 以Options方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String options(String url, Header[] headers,String encoding) throws HttpProcessException {
		return options(create(url), url, headers, encoding);
	}
	
	/**
	 * 以Options方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String options(HttpClient client, String url, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.OPTIONS, headers, encoding);
	}
	
	/**
	 * 以Trace方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String trace(String url, Header[] headers,String encoding) throws HttpProcessException {
		return trace(create(url), url, headers, encoding);
	}
	
	/**
	 * 以Trace方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static String trace(HttpClient client, String url, Header[] headers,String encoding) throws HttpProcessException {
		return send(client, url, HttpMethods.TRACE, headers, encoding);
	}

	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------

	
	/**
	 * 请求资源或服务，自定义client对象，设定请求方式，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param request			请求对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	private static String execute(HttpClient client, HttpRequestBase request,String url, String encoding) throws HttpProcessException {
		String body = "";
		HttpResponse response =null;
		try {
			
			//执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(request);
			
			//获取结果实体
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				//按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, encoding);
				logger.debug(body);
			}
			EntityUtils.consume(entity);
		} catch (ParseException | IOException e) {
			throw new HttpProcessException(e);
		} finally {
			close(response);
		}
		
		return body;
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
			logger.error(e);
		}
	}
	
	
	/**
	 * 枚举HttpMethods方法
	 * 
	 * @author arron
	 * @date 2015年11月17日 下午4:45:59 
	 * @version 1.0
	 */
	public enum HttpMethods{
		
		/**
		 * 求获取Request-URI所标识的资源
		 */
		GET(0, "GET"), 
		
		/**
		 * 向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。
		 * POST请求可能会导致新的资源的建立和/或已有资源的修改
		 */
		POST(1, "POST"),
		
		/**
		 * 向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。
		 * 这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息
		 * 只获取响应信息报头
		 */
		HEAD(2, "HEAD"),
		
		/**
		 * 向指定资源位置上传其最新内容（全部更新，操作幂等）
		 */
		PUT	(3, "PUT"), 
		
		/**
		 * 请求服务器删除Request-URI所标识的资源
		 */
		DELETE	(4, "DELETE"), 
		
		/**
		 * 请求服务器回送收到的请求信息，主要用于测试或诊断
		 */
		TRACE(5, "TRACE"), 
		
		/**
		 * 向指定资源位置上传其最新内容（部分更新，非幂等）
		 */
		PATCH	(6, "PATCH"),
		
		/**
		 * 返回服务器针对特定资源所支持的HTTP请求方法。
		 * 也可以利用向Web服务器发送'*'的请求来测试服务器的功能性
		 */
		OPTIONS	(7, "OPTIONS"), 
		
//		/**
//		 * HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器
//		 */
//		CONNECT(99, "CONNECT"),
		;
		
		private int code;
		private String name;
		
		private HttpMethods(int code, String name){
			this.code = code;
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public int getCode() {
			return code;
		}
	}
}