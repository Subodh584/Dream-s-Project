@echo off
REM E-Learning System Startup Script for Windows
REM This script runs the application with SQLite database

REM Get the directory where this script is located
setlocal enabledelayedexpansion
cd /d "%~dp0"

REM Build classpath with all required JARs
set CLASSPATH=build\classes;lib\sqlite-jdbc-3.44.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\rs2xml.jar

REM Display startup message
echo.
echo ====================================
echo Starting E-Learning System...
echo Database: ELearningSystem.db
echo (auto-created if needed)
echo ====================================
echo.

REM Run the application
java -cp "%CLASSPATH%" javasemesterproject.Main

REM Pause to see any error messages
pause
