package com.arronlong.httpclientutil.test;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地测试方法
 *
 * @author sharker
 * @date 2019/7/18 14:53
 */
public class LocalHttpClientTest {

    public static void main(String[] args) throws HttpProcessException {
        final Object token = "b982e7b31a7b783007c057848009a932";
        String json = "{\"token\":\""+token+"\"}";
        Map<String, Object> map = new HashMap<String, Object>(){{
            put("token", token);
            put("s", "asdfasdf");
        }};
        Header[] headers = new BasicHeader[]{
                new BasicHeader("token", token.toString())
        };
        HttpConfig httpConfig = HttpConfig.custom().json(json).url("http://localhost:8094/chat-messages/test").map(map).headers(headers);
        String result = HttpClientUtil.post(httpConfig);
        System.out.println("result: " + result);
    }

}
