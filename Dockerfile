FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

RUN apt-get update -y && apt-get install -y \
    alien \
    wget
    
# mysql setup
RUN wget -nv http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm && \
    alien -iv mysql-community-release-el6-5.noarch.rpm && \
    apt-get install -y mysql-server mysql-client
COPY mysql-config/vanilla-twitter-db.sql /opt/vanilla-twitter-db.sql
# COPY mysql-config/mysqlInitConfig.sh /etc/service/mysql/run
RUN chmod +x /etc/service/mysql/run

# boot up vanilla-twitter application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vanilla-twitter.jar

ENTRYPOINT ["java","-jar","/vanilla-twitter.jar"]
