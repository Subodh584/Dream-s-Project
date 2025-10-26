#!/bin/bash
# E-Learning System Startup Script for Linux/macOS
# This script runs the application with SQLite database

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Change to project directory
cd "$SCRIPT_DIR"

# Build classpath with all required JARs
CLASSPATH="build/classes:lib/sqlite-jdbc-3.44.0.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar:lib/rs2xml.jar"

# Run the application
echo "Starting E-Learning System..."
echo "Database: ELearningSystem.db (auto-created if needed)"
echo ""

java -cp "$CLASSPATH" javasemesterproject.Main

# Exit with the same code as the Java program
exit $?
