FROM java:8
MAINTAINER Mohd Javed Khan <jal90javed@gmai.com>
WORKDIR /
ADD target/workout-logger-0.0.1.jar workout-logger-0.0.1.jar
EXPOSE 8080
CMD java -jar workout-logger-0.0.1.jar
