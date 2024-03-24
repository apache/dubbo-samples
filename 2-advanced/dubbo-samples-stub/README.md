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
