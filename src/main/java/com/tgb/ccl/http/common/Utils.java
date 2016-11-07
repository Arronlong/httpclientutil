package com.tgb.ccl.http.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

/** 
 * 工具类
 * 
 * 		用于设定参数特定类型
 *		启用bebug模式，打印消息
 * 
 * @author arron
 * @date 2015年11月10日 下午12:49:26 
 * @version 1.0 
 */
public class Utils {
	
	//传入参数特定类型
	public static final String ENTITY_STRING="$ENTITY_STRING$";
	public static final String ENTITY_FILE="$ENTITY_FILEE$";
	public static final String ENTITY_BYTES="$ENTITY_BYTES$";
	public static final String ENTITY_INPUTSTREAM="$ENTITY_INPUTSTREAM$";
	public static final String ENTITY_SERIALIZABLE="$ENTITY_SERIALIZABLE$";
	public static final String ENTITY_MULTIPART="$ENTITY_MULTIPART$";
	private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE, ENTITY_MULTIPART);
	
	/**
	 * 是否开启debug，
	 */
	private static boolean debug = false;
	private static final Logger logger = Logger.getLogger("HttpClient(异步)工具类");

	/**
	 * 检测url是否含有参数，如果有，则把参数加到参数列表中
	 * 
	 * @param url					资源地址
	 * @param nvps				参数列表
	 * @return	返回去掉参数的url
	 * @throws UnsupportedEncodingException 
	 */
	public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding) throws UnsupportedEncodingException {
		// 检测url中是否存在参数
		if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			Map<String, Object> map = buildParas(url.substring(url
					.indexOf("?") + 1));
			map2HttpEntity(nvps, map, encoding);
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	/**
	 * 参数转换，将map中的参数，转到参数列表中
	 * 
	 * @param nvps				参数列表
	 * @param map				参数列表（map）
	 * @throws UnsupportedEncodingException 
	 */
	public static HttpEntity map2HttpEntity(List<NameValuePair> nvps, Map<String, Object> map, String encoding) throws UnsupportedEncodingException {
		HttpEntity entity = null;
		if(map!=null && map.size()>0){
			boolean isSpecial = false;
			// 拼接参数
			for (Entry<String, Object> entry : map.entrySet()) {
				if(SPECIAL_ENTITIY.contains(entry.getKey())){//判断是否在之中
					isSpecial = true;
					if(ENTITY_STRING.equals(entry.getKey())){//string
						entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
						break;
					}else if(ENTITY_BYTES.equals(entry.getKey())){//file
						entity = new ByteArrayEntity((byte[])entry.getValue());
						break;
					}else if(ENTITY_FILE.equals(entry.getKey())){//file
						if(File.class.isAssignableFrom(entry.getValue().getClass())){
							entity = new FileEntity((File)entry.getValue(), ContentType.APPLICATION_OCTET_STREAM);
						}else if(entry.getValue().getClass()==String.class){
							entity = new FileEntity(new File((String) entry.getValue()), ContentType.create("text/plain", "UTF-8"));
						}
						break;
					}else if(ENTITY_INPUTSTREAM.equals(entry.getKey())){//inputstream
//						entity = new InputStreamEntity();
						break;
					}else if(ENTITY_SERIALIZABLE.equals(entry.getKey())){//serializeable
//						entity = new SerializableEntity()
						break;
					}else if(ENTITY_MULTIPART.equals(entry.getKey())){//MultipartEntityBuilder
						File[] files  = null;
						if(File.class.isAssignableFrom(entry.getValue().getClass().getComponentType())){
							files=(File[])entry.getValue();
						}else if(entry.getValue().getClass().getComponentType()==String.class){
							String[] names = (String[]) entry.getValue();
							files = new File[names.length];
							for (int i = 0; i < names.length; i++) {
								files[i] = new File(names[i]);
							}
						}
						MultipartEntityBuilder builder = MultipartEntityBuilder.create();
						builder.setCharset(Charset.forName(encoding));// 设置请求的编码格式
						builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);// 设置浏览器兼容模式
						int count = 0;
						for (File file : files) {
//							//把文件转换成流对象FileBody
//							FileBody fileBody = new FileBody(file);
//							builder.addPart(String.valueOf(map.get(ENTITY_MULTIPART+".name")) + count++, fileBody);
							builder.addBinaryBody(String.valueOf(map.get(ENTITY_MULTIPART+".name")) + count++,file);
						}
						boolean forceRemoveContentTypeCharset = (Boolean)map.get(ENTITY_MULTIPART+".rmCharset");
						Map<String, Object> m = new HashMap<String, Object>();
						m.putAll(map);
						m.remove(ENTITY_MULTIPART);
						m.remove(ENTITY_MULTIPART+".name");
						m.remove(ENTITY_MULTIPART+".rmCharset");
						Iterator<Entry<String, Object>> iterator = m.entrySet().iterator();
						// 发送的数据
				        while (iterator.hasNext()) {
							Entry<String, Object> e = iterator.next();
				            builder.addTextBody(e.getKey(), String.valueOf(e.getValue()), ContentType.create("text/plain", encoding));
				        }
						entity = builder.build();// 生成 HTTP POST 实体
						
						//强制去除contentType中的编码设置，否则，在某些情况下会导致上传失败
						if(forceRemoveContentTypeCharset){
							removeContentTypeChraset(encoding, entity);
						}
						break;
					}else {
						nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
					}
				}else{
					nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			if(!isSpecial) {
				entity = new UrlEncodedFormEntity(nvps, encoding);
			}
		}
		return entity;
	}

	/**
	 * @param encoding
	 * @param entity
	 */
	private static void removeContentTypeChraset(String encoding, HttpEntity entity) {
		try {
			Class<?> clazz = entity.getClass();
			Field field = clazz.getDeclaredField("contentType");
			field.setAccessible(true); //将字段的访问权限设为true：即去除private修饰符的影响
			if(Modifier.isFinal(field.getModifiers())){
				Field modifiersField = Field.class.getDeclaredField("modifiers"); //去除final修饰符的影响，将字段设为可修改的  
				modifiersField.setAccessible(true);  
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);  
			}
			BasicHeader o = (BasicHeader) field.get(entity);
			field.set(entity, new BasicHeader(HTTP.CONTENT_TYPE, o.getValue().replace("; charset="+encoding,"")));
		} catch (NoSuchFieldException e) {
			Utils.exception(e);
		} catch (SecurityException e) {
			Utils.exception(e);
		} catch (IllegalArgumentException e) {
			Utils.exception(e);
		} catch (IllegalAccessException e) {
			Utils.exception(e);
		}
	}
	
	
	/**
	 * 生成参数
	 * 参数格式“k1=v1&k2=v2”
	 * 
	 * @param paras				参数列表
	 * @return						返回参数列表（map）
	 */
	public static Map<String,Object> buildParas(String paras){
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
	public static Map<String,Object> buildParas(String[][] paras){
		// 创建参数队列    
		Map<String,Object> map = new HashMap<String, Object>();
		for (String[] para: paras) {
			map.put(para[0], para[1]);
		}
		return map;
	}
	
	/**
	 * 打印消息
	 * 
	 * @param msg
	 */
	public static void info(String msg){
		if(debug){
			logger.info(msg);
		}
	}
	
	/**
	 * 打印消息和异常堆栈
	 * 
	 * @param msg
	 * @param t
	 */
	public static void infoException(String msg, Throwable t){
		if(debug){
			logger.info(msg, t);
		}
	}
	
	/**
	 * 打印错误消息
	 * 
	 * @param msg
	 */
	public static void error(String msg){
		logger.error(msg);
	}
	
	/**
	 * 打印错误消息和异常堆栈
	 * 
	 * @param msg
	 * @param t
	 */
	public static void errorException(String msg, Throwable t){
		logger.error(msg, t);
	}
	
	/**
	 * 打印异常堆栈
	 * 
	 * @param t
	 */
	public static void exception(Throwable t){
		logger.error(t);
	}

	/**
	 * 开启打印日志
	 */
	public static void debug() {
		debug(true);
	}
	/**
	 * 开启或关闭打印日志
	 */
	public static void debug(boolean debug) {
		Utils.debug = debug;
	}
}
