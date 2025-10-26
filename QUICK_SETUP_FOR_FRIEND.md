# 🎓 E-Learning System - Quick Start Guide

## ⚡ What's the Good News?

**NO DATABASE SETUP NEEDED!** 🎉

The database is **automatic** - just run the application and it creates everything you need.

---

## 📋 What You Need

Before you start, make sure you have:
- **Java 8 or newer** installed on your computer
- The project folder with all files

### Check if Java is installed:

**On Windows:**
```cmd
java -version
```

**On macOS/Linux:**
```bash
java -version
```

If Java isn't installed, download it from: https://adoptium.net

---

## ✨ How to Run (Super Easy!)

### **Option 1: Windows Users**
1. Find the file `run.bat` in the project folder
2. **Double-click it** to start the application
3. Done! The app opens and database is created automatically

### **Option 2: macOS/Linux Users**
1. Open Terminal and go to the project folder
2. Run this command:
   ```bash
   ./run.sh
   ```
3. Done! The app opens and database is created automatically

### **Option 3: Manual Run (Any OS)**
If the scripts don't work, open Terminal/Command Prompt in the project folder and run:

**Windows:**
```cmd
javac -cp "lib\sqlite-jdbc-3.44.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\rs2xml.jar" -d build/classes src/javasemesterproject/Main.java
java -cp "build\classes;lib\sqlite-jdbc-3.44.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\rs2xml.jar" javasemesterproject.Main
```

**macOS/Linux:**
```bash
javac -cp "lib/sqlite-jdbc-3.44.0.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar:lib/rs2xml.jar" -d build/classes src/javasemesterproject/Main.java
java -cp "build/classes:lib/sqlite-jdbc-3.44.0.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar:lib/rs2xml.jar" javasemesterproject.Main
```

---

## 🔐 Login Credentials

Use these to test the application:

### Admin Account:
- **Username:** `admin`
- **Password:** `admin`
- **Role:** Full administrative access

### Student Account:
- **Username:** `AALu`
- **Password:** `12312123123`
- **Role:** Student access

---

## 📂 Database Information

- **Type:** SQLite 3 (built-in, no server needed)
- **Location:** `ELearningSystem.db` (in project folder)
- **Automatic:** Database is created automatically when you first run the app
- **Size:** ~68 KB (very small, fits anywhere)

### Database Tables (Auto-Created):
✅ admin - Administrator accounts  
✅ student - Student accounts  
✅ teacher - Teacher accounts  
✅ subjects - Available subjects  
✅ courses - Course content  
✅ enrollments - Student enrollments  
✅ messages - User messaging system  
✅ message_users - Message tracking  

---

## 🐛 Troubleshooting

### "Java not found" Error
→ Install Java from https://adoptium.net

### App won't start
→ Make sure you're in the project folder when running the command

### Database errors
→ Delete `ELearningSystem.db` and run again (it will recreate automatically)

### Permission denied on macOS/Linux
→ Run: `chmod +x run.sh` then try again

---

## 📊 What's Inside?

This E-Learning System includes:

**Features:**
- 👤 User management (Admin, Teacher, Student roles)
- 📚 Course management system
- 📝 Enrollment tracking
- 💬 Built-in messaging system
- 🎓 Subject and course organization

**Technology:**
- Language: Java
- GUI: Swing/AWT
- Database: SQLite (embedded, no server)
- JARs: sqlite-jdbc, slf4j, rs2xml

---

## 🎯 First Time Using?

1. **Run the app** using `run.sh` or `run.bat`
2. **Login with:** admin / admin
3. **Explore** the admin dashboard
4. **Create** courses and subjects
5. **Invite** students to enroll

---

## 📞 Questions?

- Check the error messages in the console window
- Verify Java is properly installed
- Make sure all files are in the project folder
- Database is self-contained - if it breaks, just delete it and re-run

---

## ✅ Everything Should Work!

If you followed these steps, the application should run perfectly. The database is created automatically and will persist between sessions.

**Enjoy the E-Learning System! 🚀**
