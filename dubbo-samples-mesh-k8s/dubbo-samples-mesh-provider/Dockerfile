FROM openjdk:8-jre
ADD ./target/dubbo-samples-mesh-provider-1.0-SNAPSHOT.jar /dubbo-samples-mesh-provider-1.0-SNAPSHOT.jar
EXPOSE 50052
EXPOSE 31000
EXPOSE 22222
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=31000 /dubbo-samples-mesh-provider-1.0-SNAPSHOT.jar
