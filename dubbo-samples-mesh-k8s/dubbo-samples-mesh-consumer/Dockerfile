FROM openjdk:8-jre
ADD ./target/dubbo-samples-mesh-consumer-1.0-SNAPSHOT.jar /dubbo-samples-mesh-consumer-1.0-SNAPSHOT.jar
EXPOSE 20880
EXPOSE 31000
EXPOSE 22222
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=31000 /dubbo-samples-mesh-consumer-1.0-SNAPSHOT.jar