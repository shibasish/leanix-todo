FROM openjdk:8-jre
WORKDIR /
ADD target/ToDo-1.0-SNAPSHOT.jar app.jar
COPY to-do.yml to-do.yml
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "server", "to-do.yml"]