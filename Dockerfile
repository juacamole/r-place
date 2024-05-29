# Build-Stage für das Frontend
FROM node:18 AS frontend-build
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm install -g npm@latest
RUN npm install
COPY frontend/ .
RUN npm install @types/babel__core @types/babel__generator @types/babel__template @types/babel__traverse @types/estree @types/json-schema @types/node @types/prop-types @types/react @types/react-dom @types/semver
RUN npm run build

# Build-Stage für das Backend
FROM eclipse-temurin:21 AS backend-build
WORKDIR /app
COPY backend/target/app.jar .
COPY --from=frontend-build /frontend/dist /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
