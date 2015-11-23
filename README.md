# httpclientUtil
该项目其实有3个工具类：
  1.基于HttpClient-4.4.1封装的一个工具类；
  2.基于HttpAsyncClient-4.1封装的异步HttpClient工具类；
  3.javanet包下面是基于jdk自带的UrlConnection进行封装的。

前2个工具类支持插件式配置Header、插件式配置httpclient对象，这样就可以方便地自定义header信息、配置ssl、配置proxy等。
第三个虽然支持代理、ssl，但是并没有把代理ssl等进行抽象。

在test包里还有各种测试demo。
