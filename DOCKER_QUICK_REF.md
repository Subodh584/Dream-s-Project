# 🚀 Docker Quick Reference

**Everything you need to know in one page!**

---

## 3-Step Quick Start

### **Step 1: Install Docker Desktop**
Download: https://www.docker.com/products/docker-desktop

### **Step 2: Extract Project**
Unzip this folder anywhere

### **Step 3: Run One of These**

**Windows:**
```
Double-click: start-docker.bat
```

**Mac/Linux:**
```
Open Terminal here and run:
chmod +x start-docker.sh
./start-docker.sh
```

**Or any system:**
```
Open Terminal/Command Prompt here and run:
docker-compose up
```

---

## ✅ You'll See This

```
✓ postgres_1  | database system is ready to accept connections
✓ app_1       | E-Learning System starting...
```

**When you see these: App is ready!** 🎉

---

## 🎓 Login Credentials

```
Admin:
  Username: admin
  Password: admin

Student:
  Username: AALu
  Password: 12312123123
```

---

## ⏹️ Stop Running

Press in terminal: **`Ctrl + C`**

---

## 🔄 Restart

Run the same command again - data is saved!

---

## 📚 Full Guide

Read: **`DOCKER_SETUP.md`** for complete documentation

---

## 🆘 Quick Fixes

| Problem | Solution |
|---------|----------|
| "Docker not found" | Install Docker Desktop, restart |
| "Port already in use" | Close other Docker/PostgreSQL |
| "Takes too long" | First run is slow, next runs are fast |
| "App crashes" | Read DOCKER_SETUP.md Troubleshooting |

---

## 🔨 Useful Commands

```bash
# See containers
docker ps

# See logs
docker-compose logs -f

# Restart
docker-compose restart

# Clean & restart
docker-compose down -v
docker-compose up
```

---

**More questions?** → Read: `DOCKER_SETUP.md` 📖

**Ready?** → Run: `docker-compose up` 🚀
