# -------- Build Stage --------
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY . .

# Fix permission for gradlew
RUN chmod +x ./gradlew

# Build fat jar
RUN ./gradlew shadowJar --no-daemon

# -------- Runtime Stage --------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy only the built jar
COPY --from=builder /app/build/libs/*-all.jar app.jar

# Run the bot
CMD ["java", "-jar", "app.jar"]