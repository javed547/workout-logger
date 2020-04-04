FROM maven:3.5.2-jdk-8-alpine
MAINTAINER Mohd Javed Khan <jal90javed@gmai.com>
WORKDIR /
ADD src src
ADD pom.xml pom.xml
RUN mvn clean install
ADD target/workout-logger-0.0.1.jar workout-logger-0.0.1.jar
EXPOSE 8080
CMD java -jar workout-logger-0.0.1.jar
