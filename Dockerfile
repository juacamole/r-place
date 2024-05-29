FROM openjdk:11-jre-slim AS build

WORKDIR /app

LABEL authors="juacamole"

COPY backend/target/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
