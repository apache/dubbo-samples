# Dubbo-Samples-Stub
[本地存根相关文档](https://dubbo.apache.org/zh-cn/docs3-v2/java-sdk/advanced-features-and-usage/service/local-stub/)
* 该案例是关于实现本地存根的。远程服务后，客户端通常只剩下接口，而实现全在服务器端，但提供方有些时候想在客户端也执行部分逻辑。
* 该案例的实现方式就是在interface包中定义DemoServiceStub，之后关键是在consumer端的service类声明的@DubboReference注解中设置stub属性为定义的stub类，设置interfaceName属性为interface的name。

如果在consumer端想要监听连接的`建立`与`断开`事件，可以通过注解 `@DubboReference` 中的属性 `onconnect` 和 `disconnect` 来设置，其中的值
是存根类 `DemoServiceStub` 中的方法名。

当consumer端设置了如上监听方法，在consumer端启动时是会把存根类 `DemoServiceStub` 进行服务暴露的，此时需要在consumer端指定服务暴露使用的协议，
比如 `dubbo.consumer.protocol=dubbo`，因为在暴露存根类服务阶段是需要像provider一样创建`Invoker`对象，在这个过程中需要基于一个 protocol 进行。

在启动consumer端后大概率无法执行到 `onconnect` 设置的存根方法，这是因为consumer端创建连接的过程是异步的，通过directory中监听注册中心的推送，
或首次的主动读取，然后才建立的客户端连接。

如果本地存根类 `DemoServiceStub` 的暴露过程是在客户端连接建立之后，则执行连接事件方法时是找不到执行的 invoker 的。此时可以通过重启provider端
的服务，这样consumer端的连接会重新建立，这样就能看到 `DemoServiceStub` 中监听连接建立的方法被成功执行了。
