FROM java:8-jdk-alpine
COPY ./target/MusicDatabase-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch MusicDatabase-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","MusicDatabase-0.0.1-SNAPSHOT.jar"]