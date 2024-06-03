# Build the React app
FROM node:21 as build

WORKDIR /app

COPY ./frontend/package.json ./frontend/package-lock.json ./
RUN npm install

COPY ./frontend ./
RUN npm run build

# Build the Spring Boot application
FROM maven:3.8.1-jdk-11 as maven_build

WORKDIR /app

COPY ./backend/pom.xml ./backend/pom.xml
COPY ./backend/src ./backend/src

RUN mvn clean package

# Combine frontend and backend into a single image
FROM tomcat:9.0-jdk11

# Copy the React build files to Tomcat's webapps directory
COPY --from=build /app/build /usr/local/tomcat/webapps/ROOT

# Copy the Spring Boot jar to Tomcat's webapps directory
COPY --from=maven_build /app/target/backend-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/backend-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["catalina.sh", "run"]
