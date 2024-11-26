# Use the official Maven image to build the app
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app/backend
COPY pom.xml .

# Copy the source code and build the application
COPY . /app/backend
RUN mvn clean package -DskipTests

# Use the OpenJDK runtime for running the application
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/backend/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.devtools.restart.enabled=true", "-jar", "app.jar"]
