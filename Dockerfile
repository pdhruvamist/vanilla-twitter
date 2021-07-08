FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

# boot up vanilla-twitter application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vanilla-twitter.jar

EXPOSE 3306 8888
ENTRYPOINT ["java","-jar","/vanilla-twitter.jar"]
