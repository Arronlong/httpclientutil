package com.tgb.ccl.http.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.tgb.ccl.http.common.Utils;
import com.tgb.ccl.http.exception.HttpProcessException;
import com.tgb.ccl.http.httpclient.HttpClientUtil.HttpMethods;
import com.tgb.ccl.http.httpclient.builder.HACB;


/**
 * 
 * @author arron
 * @date 2015年11月4日 下午10:12:11
 * @version 1.0
 */
public class HttpAsyncClientUtil{
	private static final Logger logger = Logger.getLogger(HttpAsyncClientUtil.class);
	
	/**
	 * 创建client对象
	 * 并设置全局的标准cookie策略
	 * @return
	 * @throws HttpProcessException 
	 */
	public static CloseableHttpAsyncClient create(String url) throws HttpProcessException{
		if(url.toLowerCase().startsWith("https://")){
			return HACB.custom().ssl().build();
		}else{
			return HACB.custom().build();
		}
	}

	/**
	 * post方式请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException
	 */
	public static void send(String url, IHandler handler) throws HttpProcessException{
		send(url, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，指定返回结果的编码
	 * 
	 * @param url					资源地址
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, String encoding, IHandler handler) throws HttpProcessException  {
		send(url, new Header[]{}, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, Header[] headers, IHandler handler) throws HttpProcessException{
		send(url, headers, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，指定返回结果的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, Header[] headers, String encoding, IHandler handler) throws HttpProcessException  {
		send(url, new HashMap<String, String>(),headers, encoding, handler);
	}
	
	
	/**
	 * post方式请求资源或服务，传入请求参数
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, Map<String,String>parasMap, IHandler handler) throws HttpProcessException {
		send(url, parasMap, Charset.defaultCharset().name(), handler);
	}

	/**
	 * post方式请求资源或服务，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, Map<String,String>parasMap, String encoding, IHandler handler) throws HttpProcessException {
		send(url, parasMap, new Header[]{}, encoding, handler);
	}

	/**
	 * post方式请求资源或服务，设定内容类型，并传入请求参数
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url,  Map<String,String>parasMap, Header[] headers, IHandler handler) throws HttpProcessException {
		send(url, parasMap, headers, Charset.defaultCharset().name(), handler);
	}

	/**
	 * post方式请求资源或服务，设定内容类型，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, Map<String,String>parasMap, Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(url, HttpMethods.POST, parasMap, headers, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, IHandler handler) throws HttpProcessException {
		send(client, url, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * get 方式请求资源或服务，自定义client对象，并指定返回结果的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, new Header[]{}, encoding, handler);
	}
	
	/**
	 * get 方式请求资源或服务，自定义client对象，并指定返回结果的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Header[] headers, IHandler handler) throws HttpProcessException {
		send(client, url, headers, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * get 方式请求资源或服务，自定义client对象，并指定返回结果的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, new HashMap<String, String>(), headers, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，并传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap, IHandler handler) throws HttpProcessException {
		send(client, url, parasMap, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, parasMap, new Header[]{}, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，设置内容类型，传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap, Header[] headers, IHandler handler) throws HttpProcessException {
		send(client, url, parasMap, headers, Charset.defaultCharset().name(), handler);
	}
	
	
	/**
	 * post方式请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap,Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.POST, parasMap, headers, Charset.defaultCharset().name(), handler);
	}

	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * post方式请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod,  IHandler handler) throws HttpProcessException {
		send(url, httpMethod, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, String encoding, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, new Header[]{},encoding, handler);
	}

	/**
	 * post方式请求资源或服务
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Header[] headers, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, headers, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, new HashMap<String, String>(), headers, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，传入请求参数
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Map<String,String>parasMap, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, parasMap, Charset.defaultCharset().name(), handler);
	}

	/**
	 * post方式请求资源或服务，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Map<String,String>parasMap, String encoding, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, parasMap, new Header[]{}, encoding, handler);
	}

	/**
	 * post方式请求资源或服务，设定内容类型，并传入请求参数
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers, IHandler handler) throws HttpProcessException {
		send(url, httpMethod, parasMap, headers, Charset.defaultCharset().name(), handler);
	}

	/**
	 * post方式请求资源或服务，设定内容类型，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(create(url), url, httpMethod, parasMap, headers, encoding, handler);
	}
	
	
	
	/**
	 * post方式请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, new Header[]{}, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Header[] headers, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, headers, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Header[] headers, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, new HashMap<String, String>(), headers, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，并传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, parasMap, Charset.defaultCharset().name(), handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，传入请求参数，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap, String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, parasMap, new Header[]{}, encoding, handler);
	}
	
	/**
	 * post方式请求资源或服务，自定义client对象，设置内容类型，传入请求参数
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap, Header[] headers, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, parasMap, headers, Charset.defaultCharset().name(), handler);
	}
	
	
	/**
	 * post方式请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param httpMethod	请求方法
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Map<String,String>parasMap,Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		
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
				logger.info("请求参数："+nvps.toString());
			}else{
				int idx = url.indexOf("?");
				logger.info("请求地址："+url.substring(0, (idx>0 ? idx-1:url.length()-1)));
				if(idx>0){
					logger.info("请求参数："+url.substring(idx+1));
				}
			}
			
			//执行请求
			execute(client, request, encoding, handler);
		} catch (UnsupportedEncodingException e) {
			throw new HttpProcessException(e);
		}
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
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void get(String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		get(create(url), url, headers, encoding, handler);
	}
	
	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void get(CloseableHttpAsyncClient client, String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.GET, headers, encoding, handler);
	}
	
	/**
	 * 以Post方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void post(String url, Map<String,String>parasMap,Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		post(create(url), url, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Post方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void post(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap,Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.POST, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Put方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void put(String url, Map<String,String>parasMap,Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		put(create(url), url, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Put方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void put(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap,Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.PUT, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Delete方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void delete(String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		delete(create(url), url, headers, encoding, handler);
	}
	
	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void delete(CloseableHttpAsyncClient client, String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.DELETE, headers, encoding, handler);
	}
	
	/**
	 * 以Patch方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param parasMap		请求参数
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void patch(String url, Map<String,String>parasMap, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		patch(create(url), url, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Patch方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void patch(CloseableHttpAsyncClient client, String url, Map<String,String>parasMap, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.PATCH, parasMap, headers, encoding, handler);
	}
	
	/**
	 * 以Head方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void head(String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		head(create(url), url, headers, encoding, handler);
	}
	
	/**
	 * 以Head方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void head(CloseableHttpAsyncClient client, String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.HEAD, headers, encoding, handler);
	}
	
	/**
	 * 以Options方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void options(String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		options(create(url), url, headers, encoding, handler);
	}
	
	/**
	 * 以Options方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void options(CloseableHttpAsyncClient client, String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.OPTIONS, headers, encoding, handler);
	}
	
	/**
	 * 以Trace方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void trace(String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		trace(create(url), url, headers, encoding, handler);
	}
	
	/**
	 * 以Trace方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void trace(CloseableHttpAsyncClient client, String url, Header[] headers,String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.TRACE, headers, encoding, handler);
	}

	
	/**
	 * 执行请求
	 * 
	 * @param client				client对象
	 * @param request			请求对象
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 */
	private static void execute(final CloseableHttpAsyncClient client, HttpRequestBase request, final String encoding, final IHandler handler){
		// Start the client
		client.start();
		//异步执行请求操作，通过回调，处理结果
		client.execute(request, new FutureCallback<HttpResponse>() {
			@Override
			public void failed(Exception e) {
				handler.failed(e);
				close(client);
			}
			
			@Override
			public void completed(HttpResponse resp) {
				String body="";
				try {
					HttpEntity entity = resp.getEntity();
					if (entity != null) {
						final InputStream instream = entity.getContent();
						try {
							final StringBuilder sb = new StringBuilder();
							final char[] tmp = new char[1024];
							final Reader reader = new InputStreamReader(instream,encoding);
							int l;
							while ((l = reader.read(tmp)) != -1) {
								sb.append(tmp, 0, l);
							}
							body = sb.toString();
						} finally {
							instream.close();
							EntityUtils.consume(entity);
						}
					}
				} catch (ParseException | IOException e) {
					logger.error(e);
				}
				handler.completed(body);
				close(client);
			}
			@Override
			public void cancelled() {
				handler.cancelled();
				close(client);
			}
		});
	}

	/**
	 * 关闭client对象
	 * 
	 * @param client
	 */
	private static void close(final CloseableHttpAsyncClient client) {
		try {
			client.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	//-----------华----丽----分----割----线--------------
	
	/**
	 * 根据请求方法名，获取request对象
	 * 
	 * @param url					资源地址
	 * @param method			请求方式名称：get、post、head、put、delete、patch、trace、options
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
	 * 回调处理接口
	 * 
	 * @author arron
	 * @date 2015年11月10日 上午10:05:40 
	 * @version 1.0
	 */
	public interface IHandler {
		
		/**
		 * 处理异常时，执行该方法
		 * @return
		 */
		Object failed(Exception e);
		
		/**
		 * 处理正常时，执行该方法
		 * @return
		 */
		Object completed(String respBody);
		
		/**
		 * 处理取消时，执行该方法
		 * @return
		 */
		Object cancelled();
	}
	
	
	public static void main(String[] args) throws HttpProcessException {
		String url="http://blog.csdn.net/xiaoxian8023";
		IHandler handler = new IHandler() {
			@Override
			public Object failed(Exception e) {
				System.out.println("失败了");
				return null;
			}
			
			@Override
			public Object completed(String respBody) {
				System.out.println("获取结果："+respBody.length());
				return null;
			}
			
			@Override
			public Object cancelled() {
				System.out.println("取消了");
				return null;
			}
		};
		HttpAsyncClientUtil.send(url, handler);
	}
}