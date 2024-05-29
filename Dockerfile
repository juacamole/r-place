# Build-Stage für das Frontend
FROM node:18 AS frontend-build
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm install -g npm@latest
RUN npm cache clean --force
RUN npm install
COPY frontend/ .
RUN npm run build

# Build-Stage für das Backend
FROM openjdk:17-slim AS backend-build
WORKDIR /app
COPY backend/target/app.jar .
COPY --from=frontend-build /frontend/dist /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
