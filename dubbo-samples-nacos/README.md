## Steps to run the samples

1. Download nacos, go to https://github.com/alibaba/nacos/releases, and download the latest binary file.

2. Start Nacos locally

  ```
  cd bin
  ./startup.sh -m standalone
  ```
  
3. Run Dubbo provider demo `org.apache.dubbo.samples.ProviderBootstrap.main`

4. Run Dubbo consumer demo `org.apache.dubbo.samples.ConsumerBootstrap.main`, verify that standard ouput has the following content on the consumer side:

   ```
   result: hello, nacos
   ```