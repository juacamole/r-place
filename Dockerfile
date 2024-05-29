# Frontend Build
FROM node:21 AS frontend-build

WORKDIR /frontend

COPY frontend/package*.json ./

RUN npm install

COPY frontend/ .

RUN npm run build

# Backend Build
FROM openjdk:17-slim AS backend-build

WORKDIR /app

COPY backend/target/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]