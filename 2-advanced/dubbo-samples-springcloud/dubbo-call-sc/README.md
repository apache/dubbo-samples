Dubbo 作为消费者自动发现并消费原生 Spring Cloud 服务，示例以 Consul 为注册中心。

## Spring Cloud 服务端

## Dubbo 客户端
* Spring MVC方式开发服务端
* 以标准Dubbo方式开发客户端
    * 首先，定义标准JAX-RS注解的接口（与Provider端相对应）
    * Registry指定
    * 同时消费，要使用Multiple Registry策略