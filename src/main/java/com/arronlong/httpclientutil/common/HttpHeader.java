package com.arronlong.httpclientutil.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * 创建HttpReqHead
 * 
 * @author arron
 * @date 2015年11月9日 上午10:37:23
 * @version 1.0
 */
public class HttpHeader {

	private HttpHeader() {};

	public static HttpHeader custom() {
		return new HttpHeader();
	}

	//记录head头信息
	private Map<String, Header> headerMaps = new HashMap<String, Header>();
	
	/**
	 * 指定客户端能够接收的内容类型
	 * 例如：Accept: text/plain, text/html
	 * 
	 * @param accept
	 */
	public HttpHeader other(String key, String value) {
		headerMaps.put(key, new BasicHeader(key, value));
		return this;
	}
	/**
	 * 指定客户端能够接收的内容类型
	 * 例如：Accept: text/plain, text/html
	 * 
	 * @param accept
	 */
	public HttpHeader accept(String accept) {
		headerMaps.put(HttpReqHead.ACCEPT,
				new BasicHeader(HttpReqHead.ACCEPT, accept));
		return this;
	}

	/**
	 * 浏览器可以接受的字符编码集
	 * 例如：Accept-Charset: iso-8859-5
	 * 
	 * @param acceptCharset
	 */
	public HttpHeader acceptCharset(String acceptCharset) {
		headerMaps.put(HttpReqHead.ACCEPT_CHARSET,
				new BasicHeader(HttpReqHead.ACCEPT_CHARSET, acceptCharset));
		return this;
	}

	/**
	 * 指定浏览器可以支持的web服务器返回内容压缩编码类型
	 * 例如：Accept-Encoding: compress, gzip
	 * 
	 * @param acceptEncoding
	 */
	public HttpHeader acceptEncoding(String acceptEncoding) {
		headerMaps.put(HttpReqHead.ACCEPT_ENCODING,
				new BasicHeader(HttpReqHead.ACCEPT_ENCODING, acceptEncoding));
		return this;
	}

	/**
	 * 浏览器可接受的语言
	 * 例如：Accept-Language: en,zh
	 * 
	 * @param acceptLanguage
	 */
	public HttpHeader acceptLanguage(String acceptLanguage) {
		headerMaps.put(HttpReqHead.ACCEPT_LANGUAGE,
				new BasicHeader(HttpReqHead.ACCEPT_LANGUAGE, acceptLanguage));
		return this;
	}

	/**
	 * 可以请求网页实体的一个或者多个子范围字段
	 * 例如：Accept-Ranges: bytes
	 * 
	 * @param acceptRanges
	 */
	public HttpHeader acceptRanges(String acceptRanges) {
		headerMaps.put(HttpReqHead.ACCEPT_RANGES,
				new BasicHeader(HttpReqHead.ACCEPT_RANGES, acceptRanges));
		return this;
	}

	/**
	 * HTTP授权的授权证书
	 * 例如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
	 * 
	 * @param authorization
	 */
	public HttpHeader authorization(String authorization) {
		headerMaps.put(HttpReqHead.AUTHORIZATION,
				new BasicHeader(HttpReqHead.AUTHORIZATION, authorization));
		return this;
	}

	/**
	 * 指定请求和响应遵循的缓存机制
	 * 例如：Cache-Control: no-cache
	 * 
	 * @param cacheControl
	 */
	public HttpHeader cacheControl(String cacheControl) {
		headerMaps.put(HttpReqHead.CACHE_CONTROL,
				new BasicHeader(HttpReqHead.CACHE_CONTROL, cacheControl));
		return this;
	}

	/**
	 * 表示是否需要持久连接（HTTP 1.1默认进行持久连接）
	 * 例如：Connection: close 短链接； Connection: keep-alive 长连接
	 * 
	 * @param connection
	 * @return
	 */
	public HttpHeader connection(String connection) {
		headerMaps.put(HttpReqHead.CONNECTION,
				new BasicHeader(HttpReqHead.CONNECTION, connection));
		return this;
	}
	
	/**
	 * HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器
	 * 例如：Cookie: $Version=1; Skin=new;
	 * 
	 * @param cookie
	 */
	public HttpHeader cookie(String cookie) {
		headerMaps.put(HttpReqHead.COOKIE,
				new BasicHeader(HttpReqHead.COOKIE, cookie));
		return this;
	}

	/**
	 * 请求内容长度
	 * 例如：Content-Length: 348
	 * 
	 * @param contentLength
	 */
	public HttpHeader contentLength(String contentLength) {
		headerMaps.put(HttpReqHead.CONTENT_LENGTH,
				new BasicHeader(HttpReqHead.CONTENT_LENGTH, contentLength));
		return this;
	}

	/**
	 * 请求的与实体对应的MIME信息
	 * 例如：Content-Type: application/x-www-form-urlencoded
	 * 
	 * @param contentType
	 */
	public HttpHeader contentType(String contentType) {
		headerMaps.put(HttpReqHead.CONTENT_TYPE,
				new BasicHeader(HttpReqHead.CONTENT_TYPE, contentType));
		return this;
	}

	/**
	 * 请求发送的日期和时间
	 * 例如：Date: Tue, 15 Nov 2010 08:12:31 GMT
	 * 
	 * @param date
	 * @return
	 */
	public HttpHeader date(String date) {
		headerMaps.put(HttpReqHead.DATE,
				new BasicHeader(HttpReqHead.DATE, date));
		return this;
	}
	
	/**
	 * 请求的特定的服务器行为
	 * 例如：Expect: 100-continue
	 * 
	 * @param expect
	 */
	public HttpHeader expect(String expect) {
		headerMaps.put(HttpReqHead.EXPECT,
				new BasicHeader(HttpReqHead.EXPECT, expect));
		return this;
	}
	
	/**
	 * 发出请求的用户的Email
	 * 例如：From: user@email.com
	 * 
	 * @param from
	 */
	public HttpHeader from(String from) {
		headerMaps.put(HttpReqHead.FROM,
				new BasicHeader(HttpReqHead.FROM, from));
		return this;
	}
	
	/**
	 * 指定请求的服务器的域名和端口号
	 * 例如：Host: blog.csdn.net
	 * 
	 * @param host
	 * @return
	 */
	public HttpHeader host(String host) {
		headerMaps.put(HttpReqHead.HOST,
				new BasicHeader(HttpReqHead.HOST, host));
		return this;
	}
	
	/**
	 * 只有请求内容与实体相匹配才有效
	 * 例如：If-Match: “737060cd8c284d8af7ad3082f209582d”
	 * 
	 * @param ifMatch
	 * @return
	 */
	public HttpHeader ifMatch(String ifMatch) {
		headerMaps.put(HttpReqHead.IF_MATCH,
				new BasicHeader(HttpReqHead.IF_MATCH, ifMatch));
		return this;
	}
	
	/**
	 * 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
	 * 例如：If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
	 * 
	 * @param ifModifiedSince
	 * @return
	 */
	public HttpHeader ifModifiedSince(String ifModifiedSince) {
		headerMaps.put(HttpReqHead.IF_MODIFIED_SINCE,
				new BasicHeader(HttpReqHead.IF_MODIFIED_SINCE, ifModifiedSince));
		return this;
	}
	
	/**
	 * 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
	 * 例如：If-None-Match: “737060cd8c284d8af7ad3082f209582d”
	 * 
	 * @param ifNoneMatch
	 * @return
	 */
	public HttpHeader ifNoneMatch(String ifNoneMatch) {
		headerMaps.put(HttpReqHead.IF_NONE_MATCH,
				new BasicHeader(HttpReqHead.IF_NONE_MATCH, ifNoneMatch));
		return this;
	}
	
	/**
	 * 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag
	 * 例如：If-Range: “737060cd8c284d8af7ad3082f209582d”
	 * 
	 * @param ifRange
	 * @return
	 */
	public HttpHeader ifRange(String ifRange) {
		headerMaps.put(HttpReqHead.IF_RANGE,
				new BasicHeader(HttpReqHead.IF_RANGE, ifRange));
		return this;
	}
	
	/**
	 * 只在实体在指定时间之后未被修改才请求成功
	 * 例如：If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT
	 * 
	 * @param ifUnmodifiedSince
	 * @return
	 */
	public HttpHeader ifUnmodifiedSince(String ifUnmodifiedSince) {
		headerMaps.put(HttpReqHead.IF_UNMODIFIED_SINCE,
				new BasicHeader(HttpReqHead.IF_UNMODIFIED_SINCE, ifUnmodifiedSince));
		return this;
	}
	
	/**
	 * 限制信息通过代理和网关传送的时间
	 * 例如：Max-Forwards: 10
	 * 
	 * @param maxForwards
	 * @return
	 */
	public HttpHeader maxForwards(String maxForwards) {
		headerMaps.put(HttpReqHead.MAX_FORWARDS,
				new BasicHeader(HttpReqHead.MAX_FORWARDS, maxForwards));
		return this;
	}
	
	/**
	 * 用来包含实现特定的指令
	 * 例如：Pragma: no-cache
	 * 
	 * @param pragma
	 * @return
	 */
	public HttpHeader pragma(String pragma) {
		headerMaps.put(HttpReqHead.PRAGMA,
				new BasicHeader(HttpReqHead.PRAGMA, pragma));
		return this;
	}
	
	/**
	 * 连接到代理的授权证书
	 * 例如：Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
	 * 
	 * @param proxyAuthorization
	 */
	public HttpHeader proxyAuthorization(String proxyAuthorization) {
		headerMaps.put(HttpReqHead.PROXY_AUTHORIZATION,
				new BasicHeader(HttpReqHead.PROXY_AUTHORIZATION, proxyAuthorization));
		return this;
	}
	
	/**
	 * 只请求实体的一部分，指定范围
	 * 例如：Range: bytes=500-999
	 * 
	 * @param range
	 */
	public HttpHeader range(String range) {
		headerMaps.put(HttpReqHead.RANGE,
				new BasicHeader(HttpReqHead.RANGE, range));
		return this;
	}

	/**
	 * 先前网页的地址，当前请求网页紧随其后,即来路
	 * 例如：Referer: http://www.zcmhi.com/archives/71.html
	 * 
	 * @param referer
	 */
	public HttpHeader referer(String referer) {
		headerMaps.put(HttpReqHead.REFERER,
				new BasicHeader(HttpReqHead.REFERER, referer));
		return this;
	}
	
	/**
	 * 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息
	 * 例如：TE: trailers,deflate;q=0.5
	 * 
	 * @param te
	 */
	public HttpHeader te(String te) {
		headerMaps.put(HttpReqHead.TE,
				new BasicHeader(HttpReqHead.TE, te));
		return this;
	}
	
	/**
	 * 向服务器指定某种传输协议以便服务器进行转换（如果支持）
	 * 例如：Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
	 * 
	 * @param upgrade
	 */
	public HttpHeader upgrade(String upgrade) {
		headerMaps.put(HttpReqHead.UPGRADE,
				new BasicHeader(HttpReqHead.UPGRADE, upgrade));
		return this;
	}
	
	/**
	 * User-Agent的内容包含发出请求的用户信息
	 * 
	 * @param userAgent
	 * @return
	 */
	public HttpHeader userAgent(String userAgent) {
		headerMaps.put(HttpReqHead.USER_AGENT,
				new BasicHeader(HttpReqHead.USER_AGENT, userAgent));
		return this;
	}

	/**
	 * 关于消息实体的警告信息
	 * 例如：Warn: 199 Miscellaneous warning
	 * 
	 * @param warning
	 * @return
	 */
	public HttpHeader warning(String warning) {
		headerMaps.put(HttpReqHead.WARNING,
				new BasicHeader(HttpReqHead.WARNING, warning));
		return this;
	}
	
	/**
	 * 通知中间网关或代理服务器地址，通信协议
	 * 例如：Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
	 * 
	 * @param via
	 * @return
	 */
	public HttpHeader via(String via) {
		headerMaps.put(HttpReqHead.VIA,
				new BasicHeader(HttpReqHead.VIA, via));
		return this;
	}

	/**
	 * 设置此HTTP连接的持续时间（超时时间）
	 * 例如：Keep-Alive: 300
	 * 
	 * @param keepAlive
	 * @return
	 */
	public HttpHeader keepAlive(String keepAlive) {
		headerMaps.put(HttpReqHead.KEEP_ALIVE,
				new BasicHeader(HttpReqHead.KEEP_ALIVE, keepAlive));
		return this;
	}

	public String accept() {
		return get(HttpReqHead.ACCEPT);
	}

	public String acceptCharset() {
		return get(HttpReqHead.ACCEPT_CHARSET);
	}

	public String acceptEncoding() {
		return get(HttpReqHead.ACCEPT_ENCODING);
	}

	public String acceptLanguage() {
		return get(HttpReqHead.ACCEPT_LANGUAGE);
	}

	public String acceptRanges() {
		return get(HttpReqHead.ACCEPT_RANGES);
	}

	public String authorization() {
		return get(HttpReqHead.AUTHORIZATION);
	}

	public String cacheControl() {
		return get(HttpReqHead.CACHE_CONTROL);
	}

	public String connection() {
		return get(HttpReqHead.CONNECTION);
	}
	
	public String cookie() {
		return get(HttpReqHead.COOKIE);
	}

	public String contentLength() {
		return get(HttpReqHead.CONTENT_LENGTH);
	}

	public String contentType() {
		return get(HttpReqHead.CONTENT_TYPE);
	}
	
	public String date() {
		return get(HttpReqHead.DATE);
	}

	public String expect() {
		return get(HttpReqHead.EXPECT);
	}

	public String from() {
		return get(HttpReqHead.FROM);
	}
	
	public String host() {
		return get(HttpReqHead.HOST);
	}
	
	public String ifMatch() {
		return get(HttpReqHead.IF_MATCH);
	}
	
	public String ifModifiedSince() {
		return get(HttpReqHead.IF_MODIFIED_SINCE);
	}
	
	public String ifNoneMatch() {
		return get(HttpReqHead.IF_NONE_MATCH);
	}
	
	public String ifRange() {
		return get(HttpReqHead.IF_RANGE);
	}
	
	public String ifUnmodifiedSince() {
		return get(HttpReqHead.IF_UNMODIFIED_SINCE);
	}
	
	public String maxForwards() {
		return get(HttpReqHead.MAX_FORWARDS);
	}
	
	public String pragma() {
		return get(HttpReqHead.PRAGMA);
	}

	public String proxyAuthorization() {
		return get(HttpReqHead.PROXY_AUTHORIZATION);
	}

	public String referer() {
		return get(HttpReqHead.REFERER);
	}
	
	public String te() {
		return get(HttpReqHead.TE);
	}
	
	public String upgrade() {
		return get(HttpReqHead.UPGRADE);
	}
	
	public String userAgent() {
		return get(HttpReqHead.USER_AGENT);
	}
	
	public String via() {
		return get(HttpReqHead.VIA);
	}
	
	public String warning() {
		return get(HttpReqHead.WARNING);
	}

	public String keepAlive() {
		return get(HttpReqHead.KEEP_ALIVE);
	}
	
	
	/**
	 * 获取head信息
	 * 
	 * @return
	 */
	private String get(String headName) {
		if (headerMaps.containsKey(headName)) {
			return headerMaps.get(headName).getValue();
		}
		return null;
	}

	/**
	 * 返回header头信息
	 * 
	 * @return
	 */
	public Header[] build() {
		Header[] headers = new Header[headerMaps.size()];
		int i = 0;
		for (Header header : headerMaps.values()) {
			headers[i] = header;
			i++;
		}
		headerMaps.clear();
		headerMaps = null;
		return headers;
	}

	/**
	 * Http头信息
	 * 
	 * @author arron
	 * @date 2015年11月9日 上午11:29:04
	 * @version 1.0
	 */
	private static class HttpReqHead {
		public static final String ACCEPT = "Accept";
		public static final String ACCEPT_CHARSET = "Accept-Charset";
		public static final String ACCEPT_ENCODING = "Accept-Encoding";
		public static final String ACCEPT_LANGUAGE = "Accept-Language";
		public static final String ACCEPT_RANGES = "Accept-Ranges";
		public static final String AUTHORIZATION = "Authorization";
		public static final String CACHE_CONTROL = "Cache-Control";
		public static final String CONNECTION = "Connection";
		public static final String COOKIE = "Cookie";
		public static final String CONTENT_LENGTH = "Content-Length";
		public static final String CONTENT_TYPE = "Content-Type";
		public static final String DATE= "Date";
		public static final String EXPECT = "Expect";
		public static final String FROM = "From";
		public static final String HOST = "Host";
		public static final String IF_MATCH = "If-Match ";
		public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
		public static final String IF_NONE_MATCH = "If-None-Match";
		public static final String IF_RANGE = "If-Range";
		public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
		public static final String KEEP_ALIVE = "Keep-Alive";
		public static final String MAX_FORWARDS = "Max-Forwards";
		public static final String PRAGMA = "Pragma";
		public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
		public static final String RANGE = "Range";
		public static final String REFERER = "Referer";
		public static final String TE = "TE";
		public static final String UPGRADE = "Upgrade";
		public static final String USER_AGENT = "User-Agent";
		public static final String VIA = "Via";
		public static final String WARNING = "Warning";
	}
	
	/**
	 * 常用头信息配置
	 * 
	 * @author arron
	 * @date 2015年11月18日 下午5:30:00 
	 * @version 1.0
	 */
	public static class Headers{
		public static final String APP_FORM_URLENCODED="application/x-www-form-urlencoded";
		public static final String TEXT_PLAIN="text/plain";
		public static final String TEXT_HTML="text/html";
		public static final String TEXT_XML="text/xml";
		public static final String TEXT_JSON="text/json";
		public static final String CONTENT_CHARSET_ISO_8859_1 = Consts.ISO_8859_1.name();
		public static final String CONTENT_CHARSET_UTF8 = Consts.UTF_8.name();
		public static final String DEF_PROTOCOL_CHARSET = Consts.ASCII.name();
		public static final String CONN_CLOSE = "close";
		public static final String KEEP_ALIVE = "keep-alive";
		public static final String EXPECT_CONTINUE = "100-continue";
	}
}
