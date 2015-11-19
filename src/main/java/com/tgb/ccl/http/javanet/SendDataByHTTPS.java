package com.tgb.ccl.http.javanet;
import java.io.IOException;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.tgb.ccl.http.common.SSLs;
import com.tgb.ccl.http.exception.HttpProcessException;

/**
 * 工具类【使用 HTTPS 协议发送请求】
 * 
 * @author Arron 
 * @version 1.0
 */
public class SendDataByHTTPS extends SendDataTool {

    @Override
    protected URLConnection createRequest(String urlStr, String strMethod) throws HttpProcessException {

        // 根据URL取得一个连接
        URLConnection conn = getRequestByUrl(urlStr);
        // 判断该连接是否 HTTPS 连接
        if(!(conn instanceof HttpsURLConnection)) {
            throw new HttpProcessException("该URL无法建立HTTPS连接："+urlStr);
        }
        // 设置HTTPS相关配置
        try {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setRequestMethod(strMethod);
            httpsConn.setSSLSocketFactory(SSLs.getInstance().getSSLSF());
            httpsConn.setHostnameVerifier(SSLs.getVerifier());
        } catch (IOException e) {
            throw new HttpProcessException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HttpProcessException("与服务器建立 HTTPS 连接时发生未知错误", e);
        }

        return conn;
    }

}
