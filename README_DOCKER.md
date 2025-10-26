# 🐳 E-Learning System - Docker Edition

**Zero Setup Required! Just Download, Run, and Use!**

---

## 📸 What You Get

A complete, ready-to-run E-Learning System with:
- ✅ Java GUI Application (Swing/AWT)
- ✅ PostgreSQL Database (auto-created)
- ✅ Sample data (admin, students, teachers)
- ✅ All features working out-of-the-box

---

## ⚡ Quick Start (< 10 minutes)

### **For Windows Users:**

1. **Install Docker Desktop**
   - Download: https://www.docker.com/products/docker-desktop
   - Run installer
   - Restart computer

2. **Extract this project folder** anywhere (Desktop, Documents, etc.)

3. **Double-click `start-docker.bat`**

4. **Wait for the message:** "E-Learning System starting..."

5. **Done!** Application is ready 🎉

### **For Mac/Linux Users:**

1. **Install Docker Desktop**
   - Download: https://www.docker.com/products/docker-desktop
   - Install normally
   - Start Docker Desktop

2. **Extract this project folder**

3. **Open Terminal in this folder**

4. **Run:**
   ```bash
   chmod +x start-docker.sh
   ./start-docker.sh
   ```

5. **Wait for the message:** "E-Learning System starting..."

6. **Done!** Application is ready 🎉

---

## 🎓 Default Login Credentials

### **Admin Account**
```
Username: admin
Password: admin
```

### **Student Account**
```
Username: AALu
Password: 12312123123
```

### **Teacher Account**
You can create a new teacher account through the admin panel!

---

## 📚 Full Documentation

For detailed information, read: **`DOCKER_SETUP.md`**

Topics covered:
- ✅ What is Docker?
- ✅ Troubleshooting
- ✅ Advanced configuration
- ✅ Useful commands
- ✅ Security considerations

---

## 🚀 What's Inside

```
Docker Container 1: PostgreSQL Database
├── Automatic initialization on first run
├── Database: ELearningSystem
├── Tables: Admin, Student, Teacher, Courses, Subjects, etc.
└── Persists data across restarts

Docker Container 2: Java Application
├── Compiled Java application
├── All libraries and dependencies
├── Auto-connects to database
└── Ready to use
```

---

## ⏹️ Stopping the Application

**In the terminal where it's running:**
- Press: **`Ctrl + C`** (or `Cmd + C` on Mac)

**To stop everything:**
```bash
docker-compose down
```

---

## 🔄 Running Again

Just run the same command again:
```bash
./start-docker.sh          # Mac/Linux
# or
start-docker.bat           # Windows
# or
docker-compose up          # Any terminal
```

Data is automatically saved!

---

## 🐛 Troubleshooting

### **First Time?**
The first run takes 5-10 minutes (downloading and building).
Subsequent runs are much faster (~30 seconds).

### **"Docker not found"?**
1. Make sure Docker Desktop is installed
2. Restart your computer
3. Try again

### **"Port already in use"?**
1. Close other Docker applications
2. Or close PostgreSQL if installed separately
3. Try again

### **"Can't find file"?**
1. Make sure you're in the project folder
2. On Mac/Linux: `chmod +x start-docker.sh`

### **Still stuck?**
Read: **`DOCKER_SETUP.md`** - Full troubleshooting section inside

---

## 📋 System Requirements

**Minimum:**
- 4GB RAM
- 2GB disk space (for Docker images)
- Internet connection (for first download)

**Recommended:**
- 8GB RAM
- 5GB disk space
- Windows 10+, Mac 10.13+, or Linux

---

## 🔧 Manual Commands (Advanced)

```bash
# Start the system
docker-compose up

# Stop everything
docker-compose down

# See running containers
docker ps

# View logs
docker-compose logs -f

# Restart just the app
docker-compose restart app

# Remove database and start fresh
docker-compose down -v
```

---

## 📁 Project Structure

```
E-Learning-System/
├── start-docker.sh              ← Click to start (Mac/Linux)
├── start-docker.bat             ← Click to start (Windows)
├── DOCKER_SETUP.md              ← Full documentation
├── docker-compose.yml           ← Docker configuration
├── Dockerfile                   ← How to build app
├── init-db/
│   └── 01-init.sql             ← Database setup
├── src/                         ← Java source code
├── build/                       ← Compiled code
└── lib/                         ← Libraries
```

---

## 🎯 Features Included

✅ **Admin Panel**
- Manage subjects and courses
- View students and teachers
- Account management

✅ **Teacher Features**
- Create and manage courses
- View enrolled students
- Send messages

✅ **Student Features**
- Enroll in courses
- View course content
- Send and receive messages
- Profile management

✅ **Database**
- Automatic backup with every change
- Secure password storage
- Full data persistence

---

## 💡 Tips

1. **Keep terminal open** while using app - it shows helpful info
2. **Use admin account first** to set up subjects/courses
3. **Create teacher accounts** through admin panel
4. **Student login works** immediately with provided credentials
5. **Data persists** between restarts - close and reopen anytime

---

## 📞 Common Questions

**Q: Do I need to know programming?**
A: No! Just install Docker Desktop and click the start button.

**Q: Is my data safe?**
A: Yes! All data is stored in Docker volumes and persists between restarts.

**Q: Can I share the project?**
A: Yes! Anyone with Docker Desktop can run it the same way.

**Q: How do I add more students?**
A: Use Admin account → Admin Management → Add new users

**Q: Can I change passwords?**
A: Yes! Each user can change their own password in account settings.

**Q: What if I need to reset?**
A: Run: `docker-compose down -v` to clear everything, then `docker-compose up` to start fresh.

---

## 🔒 Security Note

**For Development Only:**
- Default passwords are simple (this is intentional for demo)
- Don't use in production without changing credentials
- Don't expose to internet without security hardening
- See DOCKER_SETUP.md for production recommendations

---

## 📊 Next Steps After Installation

1. ✅ Start the application
2. ✅ Login with admin/admin
3. ✅ Create some courses and subjects
4. ✅ Add students through signup
5. ✅ Explore all features!

---

## 🎉 You're All Set!

Everything is configured and ready to go.

**Just:**
1. Download Docker Desktop
2. Run the start script
3. Use the application!

**It's that simple!** 🚀

---

## 📧 Support Resources

- **Docker Help:** https://docs.docker.com/
- **Java Swing Guide:** https://docs.oracle.com/javase/tutorial/uiswing/
- **PostgreSQL Info:** https://www.postgresql.org/docs/

---

**Version:** 1.0  
**Last Updated:** October 27, 2025  
**Status:** Ready for Production ✅

Enjoy your E-Learning System! 📚🎓
