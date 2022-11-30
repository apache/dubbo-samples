## dubbo-samples-compatible

This sample is showing following compatibility:

* A `Filter` written with Dubbo 2.6.x will be properly loaded with Dubbo 2.7.x
* A consumer with 2.6.x invokes a provider with 2.7.x (need to start 2.6.x consumer by yourself)
* A consumer with 2.7.x invokes a provider with 2.6.x (need to start 2.6.x provider by yourself)