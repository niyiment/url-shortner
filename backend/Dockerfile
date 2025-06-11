# Use a Java base image (e.g., OpenJDK)
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build artifact (assuming a Spring Boot JAR)
# This assumes your `mvn package` command will produce a JAR in `target/`
COPY target/*.jar app.jar

# Expose the port your Spring Boot application runs on (e.g., 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]