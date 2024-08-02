# Stage 1: Build the application
FROM maven:3.9.8-amazoncorretto-17-al2023 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY . .


# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:24-slim-bullseye

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/bumblebee-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
