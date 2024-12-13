# Step 1: Use a base image with JDK installed
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file into the container
COPY target/OnlineBookstore-0.0.3.jar app.jar

# Step 4: Expose the application port
EXPOSE 8080

# Step 5: Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
