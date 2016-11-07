# httpclientUtil （QQ交流群:<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=4fae0ff61968c0a25a08318ad42cfff7509542c26a1894706a9b7d1845b0bf68">548452686&nbsp;<img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="httpclientUtil交流" title="httpclientUtil交流"></a>）
该项目其实有3个工具类：
<ul>
  <li>1.基于HttpClient-4.4.1封装的一个工具类；</li>
  <li>2.基于HttpAsycClient-4.1封装的异步HttpClient工具类；</li>
  <li>3.javanet包下面是基于jdk自带的UrlConnection进行封装的。</li>
</ul>
前2个工具类支持插件式配置Header、插件式配置httpclient对象，这样就可以方便地自定义header信息、配置ssl、配置proxy等。
第三个虽然支持代理、ssl，但是并没有把代理ssl等进行抽象。

在test包里还有各种测试demo。
<img id="demo" src="http://mfxuan.free.800m.net/demo.png" alt=""/>

<div class="page_left">
<div class="columns_detailed">
<table>
<tr>
    <td rowspan="2">
    <a href="http://blog.csdn.net/column/details/httpclient-arron.html" target="_blank" >
    <img id="col_logo" src="http://box.kancloud.cn/cover_2016-01-11_5693502e2de2_800x1068.jpg?imageMogr2/thumbnail/346x462!/interlace/1/quality/100" alt="" class="column_logo" />
    </a>
    </td>
    <td>
      <ul style="padding:8px 0 0 0;">
    	<li>专栏创建者：<a href="http://blog.csdn.net/xiaoxian8023" class="user_name" target="_blank">xiaoxian8023</a></li>
    	<li>创建时间：2015-11-16</li>
    	<li>文章数：17篇</li>
    	<li>浏览量： 49364 次</li>
    	</ul>
    </td>
</tr>
<tr>
    <td>
        <ul><a id="col_tit" href="http://blog.csdn.net/column/details/httpclient-arron.html" class="title" target="_blank">轻松把玩HttpClient</a></li>
        <li id="col_desc">介绍如何使用HttpClient，通过一些简单示例，来帮助初学者快速入手。最后提供了一个非常强大的工具类，比现在网络上分享的都强大，支持插件式设置header、代理、ssl等配置信息。</li>
        <li><a href="http://blog.csdn.net/rss.html?type=column&column=httpclient-arron" class="fav"  target="_blank" >RSS订阅</a></li>
        </ul>
    </td>
</tr>
</table>
</div>

<h1 class="tit_1">最新更新文章</h1>

<div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="51606865" href="http://blog.csdn.net/xiaoxian8023/article/details/53065507" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(九)，添加多文件上传功能</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>在Git上有人给我提Issue，说怎么上传文件，其实我一开始就想上这个功能，不过这半年比较忙，所以一直耽搁了。这次正好没什么任务了，赶紧完成这个功能。毕竟作为一款工具类，有基本的请求和下载功能，就差上...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2016-11-07 14:12</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865" target="_blank" class="view">阅读(100)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>


<div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="51606865" href="http://blog.csdn.net/xiaoxian8023/article/details/53064210" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(八)，优化启用Http连接池策略</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>写了HttpClient工具类后，有人一直在问我怎么启用http连接池，其实我没考虑过这个问题难过。不过闲暇的时候，突然间想起了这个问题，就想把这个问题搞一搞。</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2016-10-07 13:26</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865" target="_blank" class="view">阅读(100)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    
<div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="51606865" href="http://blog.csdn.net/xiaoxian8023/article/details/51606865" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(七)，新增验证码识别功能</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>这个HttpClientUtil工具类分享在GitHub上已经半年多的时间了，并且得到了不小的关注，有25颗star，被fork了38次。有了大家的鼓励，工具类一直也在完善中。最近比较忙，两个多月前的修改在今天刚修改测试完成，今天再次分享给大家。&#160; &#160; &#160; &#160;验证码识别这项技术并不是本工具类的功能，而是通过一个开源的api来识别验证码的。这里做了一个简单的封装，主要是用来解决登陆时的验证码的问题。...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2016-06-07 23:05</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865" target="_blank" class="view">阅读(5186)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/51606865#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
<div class="blog_list">
     <h1> <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
          <a name="50768320" href="http://blog.csdn.net/xiaoxian8023/article/details/50768320" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(六)，封装输入参数，简化工具类</a>
          <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
     </h1>
        
        <p>在写这个工具类的时候发现传入的参数太多，以至于方法泛滥，只一个send方法就有30多个，所以对工具类进行了优化，把输入参数封装在一个对象里，这样以后再扩展输入参数，直接修改这个类就ok了。&#160; &#160; &#160; &#160;不多说了，先上代码：/** 
 * 请求配置类
 * 
 * @author arron
 * @date 2016年2月2日 下午3:14:32 
 * @version 1.0 
 */
pub...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2016-02-29 21:55</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/50768320" target="_blank" class="view">阅读(1836)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/50768320#comments" target="_blank" class="comment">评论(5)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="50474987" href="http://blog.csdn.net/xiaoxian8023/article/details/50474987" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(五)，携带Cookie的请求</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>最近更新了一下HttpClientUtil工具类代码，主要是添加了一个参数HttpContext，这个是用来干嘛的呢？其实是用来保存和传递Cookie所需要的。因为我们有很多时候都需要登录，然后才能请求一些想要的数据。而在这以前使用HttpClientUtil工具类，还不能办到。现在更新了以后，终于可以了。&#160; &#160; &#160; &#160;先说一下思路：本次的demo，就是获取csdn中的c币，要想获取c币，必须先登...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2016-01-07 11:45</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/50474987" target="_blank" class="view">阅读(3491)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/50474987#comments" target="_blank" class="comment">评论(2)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49949813" href="http://blog.csdn.net/xiaoxian8023/article/details/49949813" target="_blank">轻松把玩HttpAsyncClient之模拟post请求示例</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>如果看到过我前些天写过的《轻松把玩HttpClient之模拟post请求示例》这篇文章，你再看本文就是小菜一碟了，如果你顺便懂一些NIO，基本上是毫无压力了。因为HttpAsyncClient相对于HttpClient，就多了一个NIO，这也是为什么支持异步的原因。不过我有一个疑问，虽说NIO是同步非阻塞IO，但是HttpAsyncClient提供了回调的机制，这点儿跟netty很像，所以可以模拟...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-23 09:13</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49949813" target="_blank" class="view">阅读(2360)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49949813#comments" target="_blank" class="comment">评论(5)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49910885" href="http://blog.csdn.net/xiaoxian8023/article/details/49910885" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(四)，单线程调用及多线程批量调用测试</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>本文主要来分享一下该工具类的测试结果。工具类的整体源码不再单独分享，源码基本上都已经在文章中了。开始我们的测试。单线程调用测试：	public static void testOne() throws HttpProcessException{
		
		System.out.println(&quot;--------简单方式调用（默认post）--------&quot;);
		String url = &quot;ht...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-19 11:21</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49910885" target="_blank" class="view">阅读(2998)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49910885#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49910127" href="http://blog.csdn.net/xiaoxian8023/article/details/49910127" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(三)，插件式配置Header</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>上篇文章介绍了插件式配置HttpClient，本文将介绍插件式配置Header。为什么要配置header在前面已经提到了，还里再简单说一下，要使用HttpClient模拟请求，去访问各种接口或者网站资源，都有可能有各种限制，比如说java客户端模拟访问csdn博客，就必须设置User-Agent，否则就报错了。还有各种其他情况，必须的设置一些特定的Header，才能请求成功，或者才能不出问题。好了...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-19 11:20</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49910127" target="_blank" class="view">阅读(1547)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49910127#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49909359" href="http://blog.csdn.net/xiaoxian8023/article/details/49909359" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(二)，插件式配置HttpClient对象</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>上一篇文章中，简单分享一下封装HttpClient工具类的思路及部分代码，本文将分享如何实现插件式配置HttpClient对象。如果你看过我前面的几篇关于HttpClient的文章或者官网示例，应该都知道HttpClient对象在创建时，都可以设置各种参数，但是却没有简单的进行封装，比如对我来说比较重要的3个：代理、ssl（包含绕过证书验证和自定义证书验证）、超时。还需要自己写。所以这里我就简单封...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-19 11:20</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49909359" target="_blank" class="view">阅读(1487)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49909359#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49883113" href="http://blog.csdn.net/xiaoxian8023/article/details/49883113" target="_blank">轻松把玩HttpClient之封装HttpClient工具类(一)（现有网上分享中的最强大的工具类）</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>搜了一下网络上别人封装的HttpClient，大部分特别简单，有一些看起来比较高级，但是用起来都不怎么好用。调用关系不清楚，结构有点混乱。所以也就萌生了自己封装HttpClient工具类的想法。要做就做最好的，本工具类支持插件式配置Header、插件式配置httpclient对象，这样就可以方便地自定义header信息、配置ssl、配置proxy等。是不是觉得说的有点悬乎了，那就先看看调用吧：...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-19 11:20</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49883113" target="_blank" class="view">阅读(2061)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49883113#comments" target="_blank" class="comment">评论(8)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49867257" href="http://blog.csdn.net/xiaoxian8023/article/details/49867257" target="_blank">轻松把玩HttpClient之设置代理，可以访问FaceBook</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>前面的文章介绍了一些HttpClient的简单示例，本文继续采用示例的方式来演示HttpClient的功能。


在项目中我们可能会遇到这样的情况：为了保证系统的安全性，只允许使用特定的代理才可以访问，而与这些系统使用HttpClient进行交互时，只能为其配置代理。


这里我们使用gogent代理访问脸书来模拟这种情况。现在在浏览器上访问是可以访问的：...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-16 17:27</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49867257" target="_blank" class="view">阅读(1624)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49867257#comments" target="_blank" class="comment">评论(1)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49866397" href="http://blog.csdn.net/xiaoxian8023/article/details/49866397" target="_blank">轻松把玩HttpClient之配置ssl，采用设置信任自签名证书实现https</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>在上篇文章《HttpClient配置ssl实现https简单示例——绕过证书验证》中简单分享了一下如何绕过证书验证。如果你想用httpclient访问一个网站，但是对方的证书没有通过ca认证或者其他问题导致证书不被信任，比如12306的证书就是这样的。所以对于这样的情况，你只能是选择绕过证书验证的方案了。
但是，如果是自己用jdk或者其他工具生成的证书，还是希望用其他方式认证自签名的证书，这篇文...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-16 16:00</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49866397" target="_blank" class="view">阅读(1113)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49866397#comments" target="_blank" class="comment">评论(1)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49865335" href="http://blog.csdn.net/xiaoxian8023/article/details/49865335" target="_blank">轻松把玩HttpClient之配置ssl，采用绕过证书验证实现https</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>上篇文章说道httpclient不能直接访问https的资源，这次就来模拟一下环境，然后配置https测试一下。在前面的文章中，分享了一篇自己生成并在tomcat中配置ssl的文章《Tomcat配置SSL》，大家可以据此来在本地配置https。我已经配置好了，效果是这样滴：


可以看到已经信任该证书（显示浅绿色小锁），浏览器可以正常访问。现在我们用代码测试一下：

	public st...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-16 15:11</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49865335" target="_blank" class="view">阅读(1870)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49865335#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49863967" href="http://blog.csdn.net/xiaoxian8023/article/details/49863967" target="_blank">轻松把玩HttpClient之模拟post请求示例</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>HttpClient 是 Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包，并且它支持 HTTP 协议最新的版本和建议。


许多需要后台模拟请求的系统或者框架都用的是httpclient。所以作为一个java开发人员，有必要学一学。本文提供了一个简单的demo，供初学者参考。


使用HttpClie...</p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-16 13:23</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49863967" target="_blank" class="view">阅读(1588)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49863967#comments" target="_blank" class="comment">评论(0)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="49785417" href="http://blog.csdn.net/xiaoxian8023/article/details/49785417" target="_blank">简单的利用UrlConnection，后台模拟http请求</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>这两天在整理看httpclient，然后想自己用UrlConnection后台模拟实现Http请求，于是一个简单的小例子就新鲜出炉了（支持代理哦）：
public class SimpleHttpTest {

	public static String send(String urlStr, Map map,String encoding){
		String body=&quot;&quot;;
		Strin...</p>
        <p>
        </p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-11-11 19:13</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49785417" target="_blank" class="view">阅读(1613)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/49785417#comments" target="_blank" class="comment">评论(1)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="43345717" href="http://blog.csdn.net/xiaoxian8023/article/details/43345717" target="_blank">httpclient3.x中使用HTTPS的方法</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>HttpClient请求https的实例：
import javax.net.ssl.SSLContext;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security....</p>
        <p>
        </p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-01-31 21:08</span>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/43345717" target="_blank" class="view">阅读(1391)</a>&nbsp;&nbsp;
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/43345717#comments" target="_blank" class="comment">评论(5)</a>
            </span>
        </div>
    </div>
    <div class="blog_list">
        <h1>
                    <a href="http://blog.csdn.net/code/column.html" class="category">[编程语言]</a>
            <a name="43345113" href="http://blog.csdn.net/xiaoxian8023/article/details/43345113" target="_blank">HttpClient3.x之Get请求和Post请求示例</a>

                    <img src="http://static.blog.csdn.net/images/icon-zhuanjia.gif" class="blog-icons" alt="专家" title="专家">
        </h1>
        
        <p>HttpClient的支持在HTTP/1.1规范中定义的所有的HTTP方法：GET, HEAD, POST, PUT, DELETE, TRACE 和 OPTIONS。每有一个方法都有一个对应的类：HttpGet，HttpHead，HttpPost，HttpPut，HttpDelete，HttpTrace和HttpOptions。所有的这些类均实现了HttpUriRequest接口，故可以作为ex...</p>
        <p>
        </p>
        <div class="about_info">
            <span class="fl">
                <a href="http://blog.csdn.net/xiaoxian8023" target="_blank" class="user_name">xiaoxian8023</a>
                <span class="time">2015-01-31 20:41</span>
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/43345113" target="_blank" class="view">阅读(894)</a>
                <a href="http://blog.csdn.net/xiaoxian8023/article/details/43345113#comments" target="_blank" class="comment">评论(6)</a>
            </span>
        </div>
    </div>
</div>

