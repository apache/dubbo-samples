FROM openjdk:8-jre
ADD ./target/dubbo-samples-kubernetes-apiserver-1.0-SNAPSHOT.jar /dubbo-samples-kubernetes-apiserver-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-classpath", "/dubbo-samples-kubernetes-apiserver-1.0-SNAPSHOT.jar", "org.apache.dubbo.samples.ProviderBootstrap"]
