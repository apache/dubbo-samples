FROM openjdk:8-jdk
ADD ./target/dubbo-samples-xds-consumer-1.0-SNAPSHOT.jar dubbo-samples-xds-consumer-1.0-SNAPSHOT.jar
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=31000 /dubbo-samples-xds-consumer-1.0-SNAPSHOT.jar