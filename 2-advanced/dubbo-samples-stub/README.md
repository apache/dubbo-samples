# Dubbo-Samples-Stub
[本地存根相关文档](https://dubbo.apache.org/zh-cn/docs3-v2/java-sdk/advanced-features-and-usage/service/local-stub/)
* 该案例是关于实现本地存根的。远程服务后，客户端通常只剩下接口，而实现全在服务器端，但提供方有些时候想在客户端也执行部分逻辑。
* 该案例的实现方式就是在interface包中定义DemoServiceStub，之后关键是在consumer端的service类声明的@DubboReference注解中设置stub属性为定义的stub类，设置interfaceName属性为interface的name。

