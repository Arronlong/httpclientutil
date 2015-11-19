package com.tgb.ccl.http.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/** 
 * 
 * @author arron
 * @date 2015年11月10日 下午12:49:26 
 * @version 1.0 
 */
public class Utils {

	/**
	 * 检测url是否含有参数，如果有，则把参数加到参数列表中
	 * 
	 * @param url					资源地址
	 * @param nvps				参数列表
	 * @return	返回去掉参数的url
	 */
	public static String checkHasParas(String url, List<NameValuePair> nvps) {
		// 检测url中是否存在参数
		if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			Map<String, String> map = buildParas(url.substring(url
					.indexOf("?") + 1));
			map2List(nvps, map);
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	/**
	 * 参数转换，将map中的参数，转到参数列表中
	 * 
	 * @param nvps				参数列表
	 * @param map				参数列表（map）
	 */
	public static void map2List(List<NameValuePair> nvps, Map<String, String> map) {
		if(map==null) return;
		// 拼接参数
		for (Entry<String, String> entry : map.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
	}
	
	
	/**
	 * 生成参数
	 * 参数格式“k1=v1&k2=v2”
	 * 
	 * @param paras				参数列表
	 * @return						返回参数列表（map）
	 */
	public static Map<String,String> buildParas(String paras){
		String[] p = paras.split("&");
		String[][] ps = new String[p.length][2];
		int pos = 0;
		for (int i = 0; i < p.length; i++) {
			pos = p[i].indexOf("=");
			ps[i][0]=p[i].substring(0,pos);
			ps[i][1]=p[i].substring(pos+1);
			pos = 0;
		}
		return buildParas(ps);
	}
	
	/**
	 * 生成参数
	 * 参数类型：{{"k1","v1"},{"k2","v2"}}
	 * 
	 * @param paras 				参数列表
	 * @return						返回参数列表（map）
	 */
	public static Map<String,String> buildParas(String[][] paras){
		// 创建参数队列    
		Map<String,String> map = new HashMap<String, String>();
		for (String[] para: paras) {
			map.put(para[0], para[1]);
		}
		return map;
	}
	
}
