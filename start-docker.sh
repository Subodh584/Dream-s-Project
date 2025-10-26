#!/bin/bash

# E-Learning System Docker Startup Script
# This script makes it even easier to start the application

set -e  # Exit on error

echo "=========================================="
echo "   E-Learning System - Docker Launcher"
echo "=========================================="
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed!"
    echo "   Please download Docker Desktop from: https://www.docker.com/products/docker-desktop"
    exit 1
fi

# Check if Docker daemon is running
if ! docker info &> /dev/null; then
    echo "❌ Docker is not running!"
    echo "   Please start Docker Desktop and try again"
    exit 1
fi

echo "✓ Docker detected successfully"
echo ""

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed!"
    echo "   It should come with Docker Desktop. Please reinstall Docker."
    exit 1
fi

echo "✓ Docker Compose detected successfully"
echo ""

# Get the script directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Change to project directory
cd "$SCRIPT_DIR"

echo "Starting E-Learning System..."
echo ""
echo "📍 Location: $SCRIPT_DIR"
echo ""

# Check if containers exist already
if docker-compose ps 2>/dev/null | grep -q "elearning"; then
    echo "Found existing containers. Restarting..."
    docker-compose restart
else
    echo "Starting fresh..."
    docker-compose up --build
fi
