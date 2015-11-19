package com.tgb.ccl.http.javanet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.tgb.ccl.http.exception.HttpProcessException;

/**
 * 工具类【使用 HTTP 或 HTTPS 协议发送请求】
 * 
 * @author Arron
 * @version 1.0
 */
public abstract class SendDataTool {

    /**
     * logger
     */
    protected static Logger logger = Logger.getLogger(SendDataTool.class);

    /**
     * 根据URL取得一个连接
     * 
     * @param urlStr
     * @return
     * @throws HttpProcessException
     */
    protected URLConnection getRequestByUrl( String urlStr) throws HttpProcessException {
        URL url = null;
        URLConnection conn = null;
        try {
        	Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8087));
            url = new URL(urlStr);
            conn = url.openConnection(proxy);
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (MalformedURLException e) {
            throw new HttpProcessException(e);
        } catch (IOException e) {
            throw new HttpProcessException(e);
        }

        return conn;
    }

    /**
     * 与服务器建立连接（HTTP、或HTTPS）
     */
    protected abstract URLConnection createRequest(String strUrl, String strMethod) throws HttpProcessException;
    
    /**
     * 发送请求
     * @param 报文头【Content-type】
     * @throws HttpProcessException 
     */
    public String send(String date, String url, String contentType)  throws HttpProcessException {
    	return send(date, url, contentType, Charset.defaultCharset().name());
    }

    /**
     * 
     * 发送请求
     * @param 报文头【Content-type】
     * @throws HttpProcessException 
     */
    public String send(String date, String url, String contentType, String encoding)  throws HttpProcessException {

        // 1、重新对请求报文进行 GBK 编码
        byte[] postData;
        try {
            postData = date.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new HttpProcessException(e);
        }

        // 2、发送 HTTPS 请求
        OutputStream reqStream = null;
        InputStream resStream = null;
        URLConnection request = null;
        String respText = null;
        try {
            logger.info("交易请求地址：" + url);
            logger.info("参数：" + date);
            
            //A、与服务器建立 HTTPS 连接
            request = createRequest(url, "POST");
            
            
            //B、指定报文头【Content-type】
            if (contentType == null || contentType.length() == 0) {
                request.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            } else {
                request.setRequestProperty("Content-type", contentType);
            }
//            request.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //   指定报文头【Content-length】 与 【Keep-alive】
            request.setRequestProperty("Content-length", String.valueOf(postData.length));
            request.setRequestProperty("Keep-alive", "false");
            
            //C、发送报文至服务器
            reqStream = request.getOutputStream();
            reqStream.write(postData);
            reqStream.close();
            
            //D、接收服务器返回结果
            ByteArrayOutputStream ms = null;
            resStream = request.getInputStream();
            ms = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int count;
            while ((count = resStream.read(buf, 0, buf.length)) > 0) {
                ms.write(buf, 0, count);
            }
            resStream.close();
            respText = new String(ms.toByteArray(), encoding);
            logger.info("交易响应结果：");
            logger.info(respText);
        } catch (UnknownHostException e) {
            throw new HttpProcessException( "服务器不可达【" + e.getMessage() + "】", e);
        } catch (IOException e) {
            throw new HttpProcessException( e.getMessage(), e);
        } finally {
            SendDataTool.close(reqStream);
            SendDataTool.close(resStream);
        }

        return respText;
    }

    public static void close(InputStream c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception ex) {
        }
    }

    public static void close(OutputStream c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception ex) {
        }
    }
}
