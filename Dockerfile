FROM java:8-jdk-alpine

COPY ./target/HeyCarService-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch HeyCarService-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","HeyCarService-0.0.1-SNAPSHOT.jar"]