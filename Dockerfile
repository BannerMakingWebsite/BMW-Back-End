FROM openjdk:11-jre
VOLUME /tmp
ARG JAR_FILE=build/libs/BMW-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]