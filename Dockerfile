FROM openjdk:17

ARG PROFILES
ARG ENV

ADD ./*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]