# Multi-stage build for optimal image size

# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for layer caching)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached layer if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/target/egti-*.jar app.jar

# Create uploads directory for profile images
RUN mkdir -p /app/uploads/profile-image

# Expose port (Render will inject PORT via env var)
EXPOSE 8080

# Run the application
# Use shell form to allow environment variable expansion
ENTRYPOINT java -jar app.jar
