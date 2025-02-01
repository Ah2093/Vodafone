 # Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app


# Create the directory for file uploads
RUN mkdir -p /app/files && chmod 755 /app/files

# Copy the JAR file from the build stage
COPY --from=build /app/target/vodafone-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8082

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]