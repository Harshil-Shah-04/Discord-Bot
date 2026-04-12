# Use lightweight Java image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN ./gradlew build --no-daemon

# Run the bot
CMD ["java", "-jar", "build/libs/discord-bot-1.0-SNAPSHOT.jar"]