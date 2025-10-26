# Multi-stage build for E-Learning System
# Stage 1: Build the application
FROM openjdk:17-slim AS builder

WORKDIR /build

# Copy project files
COPY . .

# Compile Java files directly using javac (find is already available)
RUN mkdir -p build/classes && \
    javac -cp "lib/PostgreSQLDriver/*:lib/rs2xml.jar" \
          -d build/classes \
          $(find src -name "*.java") 2>&1 | grep -i error || true

# Stage 2: Runtime image
FROM openjdk:17-slim

WORKDIR /app

# Install bash and other utilities
RUN apt-get update && apt-get install -y bash curl && rm -rf /var/lib/apt/lists/*

# Copy compiled classes and libraries from builder
COPY --from=builder /build/build/classes ./classes/
COPY --from=builder /build/lib ./lib/

# Expose port for the application (if needed for future web features)
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV DATABASE_HOST=postgres
ENV DATABASE_PORT=5432
ENV DATABASE_NAME=ELearningSystem
ENV DATABASE_USER=elearning_user
ENV DATABASE_PASSWORD=elearning_password

# Create startup script
RUN echo '#!/bin/bash\n\
echo "E-Learning System starting..."\n\
java $JAVA_OPTS -cp "classes:lib/*" javasemesterproject.Main\n\
' > /app/start.sh && chmod +x /app/start.sh

# Default command
CMD ["/app/start.sh"]
