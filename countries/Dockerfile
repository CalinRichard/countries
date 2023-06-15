# Use an appropriate base image for your application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Copy the liquibase file to the container
COPY src/main/resources/db/changelog/*.xml /liquibase/changelog/

# Resolve dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-slim-buster

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built from your application
COPY --from=build /app/target/*.jar app.jar

# Expose the port your application will listen on
EXPOSE 8888

# Run the application when the container starts
CMD ["java", "-jar", "app.jar"]