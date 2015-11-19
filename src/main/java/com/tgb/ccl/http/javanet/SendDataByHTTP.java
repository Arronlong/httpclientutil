package com.tgb.ccl.http.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.tgb.ccl.http.exception.HttpProcessException;

/**
 * 工具类【使用 HTTP 协议发送请求】
 * 
 * @author Arron
 * @version 1.0
 */
public class SendDataByHTTP extends SendDataTool {

    @Override
    protected URLConnection createRequest( String urlStr, String strMethod) throws HttpProcessException {

        // 根据URL取得一个连接
        URLConnection conn = getRequestByUrl(urlStr);
        // 判断该连接是否 HTTP 连接
        if(conn instanceof HttpsURLConnection || !(conn instanceof HttpURLConnection)) {
            throw new HttpProcessException("该URL无法建立HTTP连接："+urlStr);
        }
        // 设置HTTP相关配置：提交方式
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod(strMethod);
        } catch (IOException e) {
            throw new HttpProcessException(e.getMessage(), e);
        }

        return conn;
    }
}
