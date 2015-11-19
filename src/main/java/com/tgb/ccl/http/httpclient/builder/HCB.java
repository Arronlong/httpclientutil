package com.tgb.ccl.http.httpclient.builder;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

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
	
	private boolean isSetPool=false;//记录是否设置了连接池
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
}