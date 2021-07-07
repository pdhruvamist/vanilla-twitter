FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} vanilla-twitter.jar

ENTRYPOINT ["java","-jar","/vanilla-twitter.jar"]
