FROM openjdk:17-slim

WORKDIR /app

LABEL authors="juacamole"

COPY backend/target/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]