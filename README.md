Plain Twitter-some Microservice


**High level requirement:**  
There’ll be 5 users in the system with username as user1, user2... user5 with the
password as user1, user2... to user5 respectively.

On successful login, there’s be the following features can be accessed to a User: 
Authenticate user, Create Tweet, My Tweets.

Where,
1. Tweets: Feed of tweets from other 4 users
2. My Tweets: Feed of user’s own tweets
3. Create Tweet: Ability for logged in user to create a tweet restricted to 160
characters
   

**Steps followed to deploy in dockerized ec2 instance**

1. SSH into running ec2 box using Terminal app from local-box
2. Install Docker into ec2 using following steps,
   https://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html
3. Create a docker network layer for our application stack inside ec2 instance using command
   `$ docker network create vanilla-twitter`
4. Run mySql in docker container using below commands in ec2 instance, using docker network created,
   
   `$ docker pull mysql:latest`

   `$ docker run --name sql-vanilla-twitter --network vanilla-twitter \
        -e MYSQL_ROOT_PASSWORD=angel -e MYSQL_DATABASE=mysqlDB \
        -e MYSQL_USER=dhruva -e MYSQL_PASSWORD=root -d mysql:latest`
4. On local-box, in our spring boot based project's application yaml file, replace the jdbc connection url's hostname part 
   from localhost or 127.0.0.1 to the dockerized mySql container name
5. On local-box, create docker image out of our project code and push it to the dockerhub repository, using below commands
   `$ docker image build -t dhruvamist/vanilla-twitter-aws .`
   
   `$ docker push dhruvamist/vanilla-twitte-aws:latest`
6. SSH into running ec2 box using Terminal app from local-box
7. Run our spring boot based project in docker container using below commands in ec2 instance, using docker network created,
    `$ docker container run --network vanilla-twitter --name vanilla-twitter-app -p 8888:8888 -d dhruvamist/vanilla-twitter-aws:latest`
8. Check our project's logs after its docker container boots up & expect to see the spring boot tomcat running  
    `$ docker container logs -f <3_intiial_chars_container_id>`
9. By now, since our project is up & running while connected to mySql database, we should be able to access the exposed web service APIs using
   https://vanilla-twitter-app:8888/app-swagger-ui.html
10. Our OpenApi UI requires authenticate, using below endpoint, in order to begin requesting our vanilla-twitter application
    _/v1/login_
11. After getting authenticated, we can start posting message to our vanilla-twitter web service, using below endpoint 
    _/v1/tweets_

**Steps to deploy in dockerized local-box**

1. Run Docker desktop in local-box
2. Use Terminal app and traverse into project code folder
3. Run mySql in docker container using below commands,
   `$ docker pull mysql:latest`

   `$ docker run --name sql-vanilla-twitter -e MYSQL_ROOT_PASSWORD=angel \
        -e MYSQL_DATABASE=mysqlDB -e MYSQL_USER=dhruva -e MYSQL_PASSWORD=root \
        -d mysql:latest`

4. On local-box, in our spring boot based project's application yaml file, replace the jdbc connection url's hostname 
   part with localhost or 127.0.0.1

4. On local-box, create docker image out of our updated project code and push it to the dockerhub repository, using below commands
    `$ docker image build -t dhruvamist/vanilla-twitter-aws .`
   
    `$ docker push dhruvamist/vanilla-twitte-aws:latest`
5. User below command to deploy our project code within docker
    `$ docker-compose up --build -d`

**References**

1. Initial HLD : https://excalidraw.com/#json=5758701684129792,3AnWqe1ODlvxTtuvPMq6rw 
2. Our project source code: https://github.com/pdhruvamist/vanilla-twitter 
3. Our docker project image: https://hub.docker.com/r/dhruvamist/vanilla-twitter-aws
