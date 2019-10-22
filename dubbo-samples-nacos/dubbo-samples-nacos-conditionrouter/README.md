## Steps to run the samples

1. Start Nacos locally

  ```
  cd src/main/resources/docker
  docker-compose up
  ```
  
2. Start two dubbo provider `org.apache.dubbo.samples.configcenter.BasicProvider`, make sure one has the system property `-Ddubbo.port=20880` and the other with `-Ddubbo.port=20881`

3. Execute `org.apache.dubbo.samples.governance.util.NacosUtils` to publish routing rule.

4. Run Dubbo consumer demo `org.apache.dubbo.samples.configcenter.BasicConsumer`, verify that standard ouput has the following content on the consumer side:

   ```
   result: Hello world, response from provider: 192.168.99.1:20880
   result: Hello world again, response from provider: 192.168.99.1:20881
   ```