# httpclientutil （QQ群[548452686](http://shang.qq.com/wpa/qunwpa?idkey=4fae0ff61968c0a25a08318ad42cfff7509542c26a1894706a9b7d1845b0bf68 "httpclientUtil交流") ![image](http://pub.idqqimg.com/wpa/images/group.png)）

该项目基于HttpClient-4.4.1封装的一个工具类，支持插件式配置Header、插件式配置httpclient对象，这样就可以方便地自定义header信息、配置ssl、配置proxy等。

## Maven坐标：
```
<!-- https://mvnrepository.com/artifact/com.arronlong/httpclientutil -->
<dependency>
    <groupId>com.arronlong</groupId>
    <artifactId>httpclientutil</artifactId>
    <version>1.0.4</version>
</dependency>
```

## 简单Demo
在test包里还有各种测试[demo](http://mfxuan.free.800m.net/demo.png)，各测试类的源码在src/test/java/com/httpclient/test包路径下。
```
public static void main(String[] args) throws HttpProcessException, FileNotFoundException {
	String url = "https://github.com/Arronlong/httpclientutil";
	
	//最简单的使用：
	String html = HttpClientUtil.get(HttpConfig.custom().url(url));
	System.out.println(html);
	
	//---------------------------------
	//			【详细说明】
	//--------------------------------
	
	//插件式配置Header（各种header信息、自定义header）
	Header[] headers = HttpHeader.custom()
						 		 .userAgent("javacl")
								 .other("customer", "自定义")
								 .build();
	
	//插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
	HCB hcb = HCB.custom()
				 .timeout(1000) //超时
				 .pool(100, 10) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
				 .sslpv(SSLProtocolVersion.TLSv1_2) 	//设置ssl版本号，默认SSLv3，也可以调用sslpv("TLSv1.2")
				 .ssl()  	  	//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
				 .retry(5)		//重试5次
				 ;
	
	HttpClient client = hcb.build();
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("key1", "value1");
	map.put("key2", "value2");
	
	//插件式配置请求参数（网址、请求参数、编码、client）
	HttpConfig config = HttpConfig.custom()
	                              .headers(headers)	//设置headers，不需要时则无需设置
	                              .url(url)	          //设置请求的url
	                              .map(map)	          //设置请求参数，没有则无需设置
	                              .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
	                              .client(client)    //如果只是简单使用，无需设置，会自动获取默认的一个client对象
	                              //.inenc("utf-8")  //设置请求编码，如果请求返回一直，不需要再单独设置
	                              //.inenc("utf-8")	//设置返回编码，如果请求返回一直，不需要再单独设置
	                              //.json("json字符串")                          //json方式请求的话，就不用设置map方法，当然二者可以共用。
	                              //.context(HttpCookies.custom().getContext()) //设置cookie，用于完成携带cookie的操作
	                              //.out(new FileOutputStream("保存地址"))       //下载的话，设置这个方法,否则不要设置
	                              //.files(new String[]{"d:/1.txt","d:/2.txt"}) //上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
	                              ;
	
	
	//使用方式：
	String result1 = HttpClientUtil.get(config);     //get请求
	String result2 = HttpClientUtil.post(config);    //post请求
	System.out.println(result1);
	System.out.println(result2);
	
	//HttpClientUtil.down(config);                   //下载，需要调用config.out(fileOutputStream对象)
	//HttpClientUtil.upload(config);                 //上传，需要调用config.files(文件路径数组)
	
	//如果指向看是否访问正常
	//String result3 = HttpClientUtil.head(config); // 返回Http协议号+状态码
	//int statusCode = HttpClientUtil.status(config);//返回状态码
	
	//[新增方法]sendAndGetResp，可以返回原生的HttpResponse对象，
	//同时返回常用的几类对象：result、header、StatusLine、StatusCode
	HttpResult respResult = HttpClientUtil.sendAndGetResp(config);
	System.out.println("返回结果：\n"+respResult.getResult());
	System.out.println("返回resp-header："+respResult.getRespHeaders());//可以遍历
	System.out.println("返回具体resp-header："+respResult.getHeaders("Date"));
	System.out.println("返回StatusLine对象："+respResult.getStatusLine());
	System.out.println("返回StatusCode："+respResult.getStatusCode());
	System.out.println("返回HttpResponse对象）（可自行处理）："+respResult.getResp());
}
```

![image](http://box.kancloud.cn/cover_2016-01-11_5693502e2de2_800x1068.jpg?imageMogr2/thumbnail/346x462!/interlace/1/quality/100)| 专栏创建者：[xiaoxian8023](http://blog.csdn.net/xiaoxian8023)<br/>创建时间：2015-11-16<br/>文章数：17篇<br/>[RSS订阅](http://blog.csdn.net/xiaoxian8023/rss/list)<br/> <br/>[轻松把玩HttpClient](http://blog.csdn.net/column/details/httpclient-arron.html)<br/>介绍如何使用HttpClient，通过一些简单示例，来帮助初学者快速入手。<br/>同时提供了一个非常强大的工具类，比现在网络上分享的都强大：<br/>支持插件式设置header、代理、ssl等配置信息，支持携带Cookie的操作，支持http的各种方法，支持上传、下载等功能。
---|---


---

# 最新更新文章
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(九)，添加多文件上传功能](http://blog.csdn.net/xiaoxian8023/article/details/53065507)
```
在Git上有人给我提Issue，说怎么上传文件，其实我一开始就想上这个功能，不过这半年比较忙，所以一直耽搁了。
这次正好没什么任务了，赶紧完成这个功能。毕竟作为一款工具类，有基本的请求和下载功能，就差上...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(八)，优化启用Http连接池策略](http://blog.csdn.net/xiaoxian8023/article/details/53064210)
```
写了HttpClient工具类后，有人一直在问我怎么启用http连接池，其实我没考虑过这个问题难过。
不过闲暇的时候，突然间想起了这个问题，就想把这个问题搞一搞。
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(七)，新增验证码识别功能](http://blog.csdn.net/xiaoxian8023/article/details/51606865)
```
这个HttpClientUtil工具类分享在GitHub上已经半年多的时间了，并且得到了不小的关注，有25颗star，被fork了38次。
有了大家的鼓励，工具类一直也在完善中。最近比较忙，两个多月前的修改在今天刚修改测试完成，今天再次分享给大家。
验证码识别这项技术并不是本工具类的功能，而是通过一个开源的api来识别验证码的。
这里做了一个简单的封装，主要是用来解决登陆时的验证码的问题。...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(六)，封装输入参数，简化工具类](http://blog.csdn.net/xiaoxian8023/article/details/50768320)
```
在写这个工具类的时候发现传入的参数太多，以至于方法泛滥，只一个send方法就有30多个，
所以对工具类进行了优化，把输入参数封装在一个对象里，这样以后再扩展输入参数，直接修改这个类就ok了。
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(五)，携带Cookie的请求](http://blog.csdn.net/xiaoxian8023/article/details/50474987)
```
最近更新了一下HttpClientUtil工具类代码，主要是添加了一个参数HttpContext，这个是用来干嘛的呢？
其实是用来保存和传递Cookie所需要的。因为我们有很多时候都需要登录，然后才能请求一些想要的数据。
而在这以前使用HttpClientUtil工具类，还不能办到。现在更新了以后，终于可以了。
先说一下思路：本次的demo，就是获取csdn中的c币，要想获取c币，必须先登...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpAsyncClient之模拟post请求示例](http://blog.csdn.net/xiaoxian8023/article/details/49949813)
```
如果看到过我前些天写过的《轻松把玩HttpClient之模拟post请求示例》这篇文章，你再看本文就是小菜一碟了，如果你顺便懂一些NIO，基本上是毫无压力了。
因为HttpAsyncClient相对于HttpClient，就多了一个NIO，这也是为什么支持异步的原因。
不过我有一个疑问，虽说NIO是同步非阻塞IO，但是HttpAsyncClient提供了回调的机制，
这点儿跟netty很像，所以可以模拟...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(四)，单线程调用及多线程批量调用测试](http://blog.csdn.net/xiaoxian8023/article/details/49910885)
```
本文主要来分享一下该工具类的测试结果。工具类的整体源码不再单独分享，源码基本上都已经在文章中了。
开始我们的测试。单线程调用测试：
    public static void testOne() throws HttpProcessException{
		
		System.out.println("--------简单方式调用（默认post）--------");
		String url = "http://...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(三)，插件式配置Header](http://blog.csdn.net/xiaoxian8023/article/details/49910127)
```
上篇文章介绍了插件式配置HttpClient，本文将介绍插件式配置Header。为什么要配置header在前面已经提到了，还里再简单说一下，
要使用HttpClient模拟请求，去访问各种接口或者网站资源，都有可能有各种限制，
比如说java客户端模拟访问csdn博客，就必须设置User-Agent，否则就报错了。
还有各种其他情况，必须的设置一些特定的Header，才能请求成功，或者才能不出问题。好了...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(二)，插件式配置HttpClient对象](http://blog.csdn.net/xiaoxian8023/article/details/49909359)
```
上一篇文章中，简单分享一下封装HttpClient工具类的思路及部分代码，本文将分享如何实现插件式配置HttpClient对象。
如果你看过我前面的几篇关于HttpClient的文章或者官网示例，应该都知道HttpClient对象在创建时，都可以设置各种参数，
但是却没有简单的进行封装，比如对我来说比较重要的3个：
代理、ssl（包含绕过证书验证和自定义证书验证）、超时。还需要自己写。
所以这里我就简单封...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之封装HttpClient工具类(一)（现有网上分享中的最强大的工具类）](http://blog.csdn.net/xiaoxian8023/article/details/49883113)
```
搜了一下网络上别人封装的HttpClient，大部分特别简单，有一些看起来比较高级，但是用起来都不怎么好用。
调用关系不清楚，结构有点混乱。所以也就萌生了自己封装HttpClient工具类的想法。
要做就做最好的，本工具类支持插件式配置Header、插件式配置httpclient对象，
这样就可以方便地自定义header信息、配置ssl、配置proxy等。是不是觉得说的有点悬乎了，那就先看看调用吧：...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之设置代理，可以访问FaceBook](http://blog.csdn.net/xiaoxian8023/article/details/49867257)
```
前面的文章介绍了一些HttpClient的简单示例，本文继续采用示例的方式来演示HttpClient的功能。
在项目中我们可能会遇到这样的情况：为了保证系统的安全性，只允许使用特定的代理才可以访问，
而与这些系统使用HttpClient进行交互时，只能为其配置代理。
这里我们使用gogent代理访问脸书来模拟这种情况。现在在浏览器上访问是可以访问的：...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之配置ssl，采用设置信任自签名证书实现https](http://blog.csdn.net/xiaoxian8023/article/details/49866397)
```
在上篇文章《HttpClient配置ssl实现https简单示例——绕过证书验证》中简单分享了一下如何绕过证书验证。
如果你想用httpclient访问一个网站，但是对方的证书没有通过ca认证或者其他问题导致证书不被信任，
比如12306的证书就是这样的。所以对于这样的情况，你只能是选择绕过证书验证的方案了。
但是，如果是自己用jdk或者其他工具生成的证书，还是希望用其他方式认证自签名的证书，这篇文...
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之配置ssl，采用绕过证书验证实现https](http://blog.csdn.net/xiaoxian8023/article/details/49865335)
```
上篇文章说道httpclient不能直接访问https的资源，这次就来模拟一下环境，然后配置https测试一下。
在前面的文章中，分享了一篇自己生成并在tomcat中配置ssl的文章《Tomcat配置SSL》，大家可以据此来在本地配置https。
我已经配置好了，效果是这样滴：
可以看到已经信任该证书（显示浅绿色小锁），浏览器可以正常访问。现在我们用代码测试一下：
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [轻松把玩HttpClient之模拟post请求示例](http://blog.csdn.net/xiaoxian8023/article/details/49863967)
```
HttpClient 是 Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包，并且它支持 HTTP 协议最新的版本和建议。
许多需要后台模拟请求的系统或者框架都用的是httpclient。所以作为一个java开发人员，有必要学一学。
本文提供了一个简单的demo，供初学者参考。
```
## ![image](http://static.blog.csdn.net/images/icon-zhuanjia.gif "专家") [简单的利用UrlConnection，后台模拟http请求](http://blog.csdn.net/xiaoxian8023/article/details/49785417)
```
这两天在整理看httpclient，然后想自己用UrlConnection后台模拟实现Http请求，于是一个简单的小例子就新鲜出炉了（支持代理哦）：
  public class SimpleHttpTest {

	public static String send(String urlStr, Map map,String encoding){
		String body="";
		Strin...
```
