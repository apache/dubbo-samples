# Dubbo-Samples-Stub
[Documentation of Local Stub](https://cn.dubbo.apache.org/en/docs/v2.7/user/examples/local-stub/)

* This sample is about the implementation of Local Stub.
When using RPC, the client usually only use the interface (which is implemented on provider side).
However, in certain circumstances, the client may also want to perform part of the logic.
For example: do ThreadLocal cache, verify parameters, return mock data when call fails, etc.

* To solve this problem, you can configure the stub using API provided by dubbo, and this sample
shows how. In this sample, a "DemoServiceStub" is defined in the module of "dubbo-samples-stub-interface",
alongside with the interface class of "DemoService". The way to use this stub on consumer side
is explicitly configuring the "stub" and "interfaceName" fields of @DubboReference annotation.

If you want to listen to the `onconnect` and `ondisconnect` events of the connection on the consumer side,
you can set them by the annotation `@DubboReference` attributes `onconnect` and `disconnect`,
where the value is the method name in the stub class `DemoServiceStub`.

When the consumer side sets the above listening method, the stub class `DemoServiceStub` will be export when the consumer side is started.
At this time, you need to specify the protocol used for stub class export on the consumer side, such as `dubbo.consumer.protocol=dubbo`,
because the stub class export stage requires the creation of the `Invoker` like the provider side, and this process needs to be based on a protocol.

After starting the consumer side, it is highly likely that the stub method set by `onconnect` cannot be executed.
This is because the process of creating a connection on the consumer side is asynchronous, through listening to the push of the registration center in the directory,
or the first active reading, and then the client connection is established.

If the local stub class `DemoServiceStub` is export after the client connection is established,
the invoker cannot be found when the connection event method is executed.

In this case, you can restart the service on the provider side, so that the connection on the consumer side will be reestablished,
and you can see that the method for monitoring the connection establishment in `DemoServiceStub` has been successfully executed.
