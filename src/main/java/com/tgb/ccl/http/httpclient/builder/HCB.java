package com.tgb.ccl.http.httpclient.builder;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import com.tgb.ccl.http.common.SSLs;
import com.tgb.ccl.http.exception.HttpProcessException;

/**
 * httpclient创建者
 * 
 * @author arron
 * @date 2015年11月9日 下午5:45:47 
 * @version 1.0
 */
public class  HCB extends HttpClientBuilder{
	
	public boolean isSetPool=false;//记录是否设置了连接池
	private boolean isNewSSL=false;//记录是否设置了更新了ssl
	
	//用于配置ssl
	private SSLs ssls = SSLs.getInstance();
	
	private HCB(){}
	public static HCB custom(){
		return new HCB();
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeout		超市时间，单位-毫秒
	 * @return
	 */
	public HCB timeout(int timeout){
		 // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
		return (HCB) this.setDefaultRequestConfig(config);
	}
	
	/**
	 * 设置ssl安全链接
	 * 
	 * @return
	 * @throws HttpProcessException
	 */
	public HCB ssl() throws HttpProcessException {
		if(isSetPool){//如果已经设置过线程池，那肯定也就是https链接了
			if(isNewSSL){
				throw new HttpProcessException("请先设置ssl，后设置pool");
			}
			return this;
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", ssls.getSSLCONNSF()).build();
		//设置连接池大小
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		return (HCB) this.setConnectionManager(connManager);
	}
	

	/**
	 * 设置自定义sslcontext
	 * 
	 * @param keyStorePath		密钥库路径
	 * @return
	 * @throws HttpProcessException
	 */
	public HCB ssl(String keyStorePath) throws HttpProcessException{
		return ssl(keyStorePath,"nopassword");
	}
	/**
	 * 设置自定义sslcontext
	 * 
	 * @param keyStorePath		密钥库路径
	 * @param keyStorepass		密钥库密码
	 * @return
	 * @throws HttpProcessException
	 */
	public HCB ssl(String keyStorePath, String keyStorepass) throws HttpProcessException{
		this.ssls = SSLs.custom().customSSL(keyStorePath, keyStorepass);
		this.isNewSSL=true;
		return ssl();
	}
	
	
	/**
	 * 设置连接池（默认开启https）
	 * 
	 * @param maxTotal					最大连接数
	 * @param defaultMaxPerRoute	每个路由默认连接数
	 * @return
	 * @throws HttpProcessException
	 */
	public HCB pool(int maxTotal, int defaultMaxPerRoute) throws HttpProcessException{
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", ssls.getSSLCONNSF()).build();
		//设置连接池大小
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connManager.setMaxTotal(maxTotal);
		connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		isSetPool=true;
		return (HCB) this.setConnectionManager(connManager);
	}
	
	/**
	 * 设置代理
	 * 
	 * @param hostOrIP		代理host或者ip
	 * @param port			代理端口
	 * @return
	 */
	public HCB proxy(String hostOrIP, int port){
		// 依次是代理地址，代理端口号，协议类型  
		HttpHost proxy = new HttpHost(hostOrIP, port, "http");  
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		return (HCB) this.setRoutePlanner(routePlanner);
	}
	
	/**
	 * 重试（如果请求是幂等的，就再次尝试）
	 * 
	 * @param tryTimes		重试次数
	 * @return
	 */
	public HCB retry(final int tryTimes){
		return retry(tryTimes, false);
	}
	
	/**
	 * 重试（如果请求是幂等的，就再次尝试）
	 * 
	 * @param tryTimes						重试次数
	 * @param retryWhenInterruptedIO		连接拒绝时，是否重试
	 * @return
	 */
	public HCB retry(final int tryTimes, final boolean retryWhenInterruptedIO){
		// 请求重试处理
	    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
	        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
	            if (executionCount >= tryTimes) {// 如果已经重试了n次，就放弃
	                return false;
	            }
	            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
	                return true;
	            }
	            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
	                return false;
	            }
	            if (exception instanceof InterruptedIOException) {// 超时
	                //return false;
	                return retryWhenInterruptedIO;
	            }
	            if (exception instanceof UnknownHostException) {// 目标服务器不可达
	                return true;
	            }
	            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
	            	return false;
	            }
	            if (exception instanceof SSLException) {// SSL握手异常
	                return false;
	            }

	            HttpClientContext clientContext = HttpClientContext .adapt(context);
	            HttpRequest request = clientContext.getRequest();
	            // 如果请求是幂等的，就再次尝试
	            if (!(request instanceof HttpEntityEnclosingRequest)) {
	                return true;
	            }
	            return false;
	        }
	    };
	    this.setRetryHandler(httpRequestRetryHandler);
	    return this;
	}
	
}