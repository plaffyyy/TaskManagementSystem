FROM openjdk:23

WORKDIR /app
COPY  /target/system-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]