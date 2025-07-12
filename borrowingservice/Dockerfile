# Stage 1: Use Maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
# Left side is our current project folder where store all the code
# Right side is the folder in our docker server
COPY ./bookservice ./bookservice
COPY ./employeeservice ./employeeservice
COPY ./commonservice ./commonservice
COPY ./borrowingservice ./borrowingservice

# Package only build the project and not install Maven repository again
WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/bookservice
RUN mvn clean install -DskipTests

WORKDIR /app/employeeservice
RUN mvn clean install -DskipTests

WORKDIR /app/borrowingservice
RUN mvn clean package -DskipTests

# Stage 2: Use OpenJDK for running
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/borrowingservice/target/*.jar borrowingservice.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "borrowingservice.jar"]

