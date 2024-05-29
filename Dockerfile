FROM eclipse-temurin:21

WORKDIR /app

LABEL authors="juacamole"

COPY backend/target app.jar

EXPOSE 8080

FROM eclipse-temurin:21

WORKDIR /app

COPY --from=0 /app/app.jar /app/

ENTRYPOINT ["java", "-jar", "app.jar"]