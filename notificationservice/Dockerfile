# Stage 1: Use Maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
# Left side is our current project folder where store all the code
# Right side is the folder in our docker server
COPY ./notificationservice ./notificationservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

# Package only build the project and not install Maven repository again
WORKDIR /app/notificationservice
RUN mvn clean package -DskipTests

# Stage 2: Use OpenJDK for running
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/notificationservice/target/*.jar notificationservice.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "notificationservice.jar"]

