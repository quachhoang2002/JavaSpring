# Stage 1: Build the application
FROM openjdk:20-ea-11-jdk-slim as build
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven


# Copy only pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Create the final Docker image with the built JAR file
FROM openjdk:20-jdk
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
