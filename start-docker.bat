@echo off
REM E-Learning System Docker Startup Script for Windows
REM This script makes it easy to start the application

echo ==========================================
echo    E-Learning System - Docker Launcher
echo ==========================================
echo.

REM Check if Docker is installed
where docker >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker is not installed!
    echo    Please download Docker Desktop from: https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)

echo ✓ Docker detected successfully
echo.

REM Check if docker-compose is available
where docker-compose >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker Compose is not available!
    echo    It should come with Docker Desktop. Please reinstall Docker.
    pause
    exit /b 1
)

echo ✓ Docker Compose detected successfully
echo.

REM Get the directory where this script is located
set SCRIPT_DIR=%~dp0

echo Starting E-Learning System...
echo.
echo 📍 Location: %SCRIPT_DIR%
echo.
echo Please wait... this may take a few minutes on first run
echo.

REM Change to project directory
cd /d "%SCRIPT_DIR%"

REM Start Docker Compose
docker-compose up --build

pause
