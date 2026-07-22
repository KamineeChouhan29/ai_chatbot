# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy pom and source code
COPY pom.xml .
COPY src ./src
# Build the jar
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/AIBackend-0.0.1-SNAPSHOT.jar app.jar
# Force the port to 8080
ENV PORT=8080
EXPOSE 8080
# Run the application with memory limits
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]