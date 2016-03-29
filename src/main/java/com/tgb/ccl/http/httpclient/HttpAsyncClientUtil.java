package com.tgb.ccl.http.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.tgb.ccl.http.common.HttpConfig;
import com.tgb.ccl.http.common.HttpMethods;
import com.tgb.ccl.http.common.Utils;
import com.tgb.ccl.http.exception.HttpProcessException;
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
	 * 请求资源或服务
	 * 
	 * @param config
	 * @return
	 * @throws HttpProcessException
	 */
	public static void send(HttpConfig config) throws HttpProcessException {
		execute(config);
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
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void get(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
	}
	
	/**
	 * 以Get方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void get(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.GET));
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
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void post(CloseableHttpAsyncClient client, String url, Map<String,Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.POST).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Post方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void post(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.POST));
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
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void put(CloseableHttpAsyncClient client, String url, Map<String,Object>parasMap,Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.PUT).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Put方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void put(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.PUT));
	}
	
	/**
	 * 以Delete方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void delete(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.DELETE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Delete方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void delete(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.DELETE));
	}
	
	/**
	 * 以Patch方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @return						返回处理结果
	 * @throws HttpProcessException 
	 */
	public static void patch(CloseableHttpAsyncClient client, String url, Map<String,Object>parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.PATCH).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Patch方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void patch(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.PATCH));
	}
	
	/**
	 * 以Head方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @throws HttpProcessException 
	 */
	public static void head(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.HEAD).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Head方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void head(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.HEAD));
	}
	
	
	/**
	 * 以Options方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @throws HttpProcessException 
	 */
	public static void options(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.OPTIONS).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Options方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void options(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.OPTIONS));
	}
	
	/**
	 * 以Trace方式，请求资源或服务
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param encoding		编码
	 * @param handler			回调处理对象
	 * @throws HttpProcessException 
	 */
	public static void trace(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
		send(HttpConfig.custom().method(HttpMethods.TRACE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
	}

	/**
	 * 以Trace方式，请求资源或服务
	 * 
	 * @param config		请求参数配置
	 * @throws HttpProcessException 
	 */
	public static void trace(HttpConfig config) throws HttpProcessException {
		send(config.method(HttpMethods.TRACE));
	}

	/**
	 * 下载图片
	 * 
	 * @param client				client对象
	 * @param url					资源地址
	 * @param headers			请求头信息
	 * @param context			http上下文，用于cookie操作
	 * @param out					输出流
	 * @throws HttpProcessException 
	 */
	public static void down(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
		execute(HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).out(out));
	}
	
	/**
	 * 下载图片
	 * 
	 * @param config		请求参数配置
	 * @param out					输出流
	 * @throws HttpProcessException 
	 */
	public static void down(HttpConfig config) throws HttpProcessException {
		execute(config.method(HttpMethods.GET));
	}
	
	/**
	 * 请求资源或服务
	 * 
	 * @param config				请求参数配置
	 * @param out					输出流对象
	 * @throws HttpProcessException 
	 */
	private static void execute(HttpConfig config) throws HttpProcessException {
		if(config.asynclient()==null){//检测是否设置了client
			config.asynclient(create(config.url()));
		}
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
				HttpEntity entity = Utils.map2List(nvps, config.map(), config.inenc());
				
				//设置参数到请求对象中
				((HttpEntityEnclosingRequestBase)request).setEntity(entity);
				
				logger.info("请求地址："+config.url());
				logger.info("请求参数："+nvps.toString());
			}else{
				int idx = config.url().indexOf("?");
				logger.info("请求地址："+config.url().substring(0, (idx>0 ? idx:config.url().length())));
				if(idx>0){
					logger.info("请求参数："+config.url().substring(idx+1));
				}
			}
			//执行请求
			final CloseableHttpAsyncClient client = config.asynclient();
			final String encoding=config.outenc();
			final IHandler handler=config.handler();
			final OutputStream out = config.out();
			
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
					try {
						if(out == null){
							handler.completed(fmt2String(resp, encoding));
						}else{
							handler.down(fmt2Stream(resp, out));
						}
					} catch (HttpProcessException e) {
						e.printStackTrace();
					}
					close(client);
				}
				
				@Override
				public void cancelled() {
					handler.cancelled();
					close(client);
				}
			});
			
		} catch (UnsupportedEncodingException e) {
			throw new HttpProcessException(e);
		}
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
		} catch (UnsupportedOperationException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
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
	private static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws HttpProcessException {
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
		 * 处理正常时，执行该方法
		 * @return
		 */
		Object down(OutputStream out);
		
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

			@Override
			public Object down(OutputStream out) {
				System.out.println("开始下载");
				return null;
			}
		};
		HttpAsyncClientUtil.send(HttpConfig.custom().url(url).handler(handler));
	}
}