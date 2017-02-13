package com.httpclient.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.httpclient.HttpClientUtil;
import com.httpclient.common.HttpConfig;
import com.httpclient.common.HttpHeader;
import com.httpclient.exception.HttpProcessException;

/** 
 * 测试携带cookie的操作
 * 
 * @author arron
 * @date 2016年1月7日 上午10:09:53 
 * @version 1.0 
 */
public class TestCookie {

	public static void main(String[] args) throws HttpProcessException {
		//登录地址
		String loginUrl = "https://passport.csdn.net/account/login";
		//C币查询
		String scoreUrl = "http://my.csdn.net/my/score";
		
		//定义cookie存储
		HttpClientContext context = new HttpClientContext();
		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);
		HttpConfig config =HttpConfig.custom().url(loginUrl).context(context);
		//获取参数
		String loginform = HttpClientUtil.get(config);//可以用.send(config)代替，但是推荐使用明确的get方法
		//System.out.println(loginform);
		System.out.println("获取登录所需参数");
		String lt = regex("\"lt\" value=\"([^\"]*)\"", loginform)[0];
		String execution = regex("\"execution\" value=\"([^\"]*)\"", loginform)[0];
		String _eventId = regex("\"_eventId\" value=\"([^\"]*)\"", loginform)[0];
		
		//组装参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", "用户名");
		map.put("password", "密码");
		map.put("lt", lt);
		map.put("execution", execution);
		map.put("_eventId", _eventId);

		//发送登录请求
		String result = HttpClientUtil.post(config.map(map));//可以用.send(config.method(HttpMethods.POST).map(map))代替，但是推荐使用明确的post方法
		//System.out.println(result);
		if(result.contains("帐号登录")){//如果有帐号登录，则说明未登录成功
			String errmsg = regex("\"error-message\">([^<]*)<", result)[0];
			System.err.println("登录失败："+errmsg);
			return;
		}
		System.out.println("----登录成功----");
		
//		//打印参数，可以看到cookie里已经有值了。
//		cookieStore = context.getCookieStore();
//		for (Cookie cookie : cookieStore.getCookies()) {
//			System.out.println(cookie.getName()+"--"+cookie.getValue());
//		}
		
		//访问积分管理页面
		Header[] headers = HttpHeader.custom().userAgent("User-Agent: Mozilla/5.0").build();
		result = HttpClientUtil.post(config.url(scoreUrl).headers(headers));//可以用.send(config.url(scoreUrl).headers(headers))代替，但是推荐使用明确的post方法
		//获取C币
		String score = regex("\"last-img\"><span>([^<]*)<", result)[0];
		System.out.println("您当前有C币："+score);
		
	}
	

	/**
	 * 通过正则表达式获取内容
	 * 
	 * @param regex		正则表达式
	 * @param from		原字符串
	 * @return
	 */
	public static String[] regex(String regex, String from){
		Pattern pattern = Pattern.compile(regex); 
		Matcher matcher = pattern.matcher(from);
		List<String> results = new ArrayList<String>();
		while(matcher.find()){
			for (int i = 0; i < matcher.groupCount(); i++) {
				results.add(matcher.group(i+1));
			}
		}
		return results.toArray(new String[]{});
	}
}
