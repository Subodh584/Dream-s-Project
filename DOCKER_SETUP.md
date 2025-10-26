# 🐳 Docker Setup Guide - E-Learning System

## For Non-Technical Users

Welcome! This guide explains how to run the E-Learning System using Docker. **No coding knowledge required!**

---

## 📋 What is Docker?

Think of Docker as a "sealed box" that contains:
- ✅ The Java application
- ✅ PostgreSQL database
- ✅ Everything needed to run
- ✅ No setup needed!

Once you download Docker Desktop, you just press one button and everything works.

---

## 🎯 Quick Start (3 Steps)

### **Step 1: Download Docker Desktop** (5 minutes)

1. Go to: **https://www.docker.com/products/docker-desktop**
2. Click "Download for [your system]" (Windows/Mac/Linux)
3. Install it (just like any other app)
4. Restart your computer

**That's it!** Docker Desktop is now ready.

---

### **Step 2: Download the Project**

1. Download this project folder
2. Extract it anywhere (Desktop, Downloads, etc.)

---

### **Step 3: Run One Command** (1 minute)

**On Windows:**
1. Open Command Prompt (type `cmd` in search box)
2. Navigate to project folder: `cd path/to/project`
3. Run: `docker-compose up`

**On Mac/Linux:**
1. Open Terminal
2. Navigate to project folder: `cd path/to/project`
3. Run: `docker-compose up`

---

## ✅ How to Know It's Working

**Look for these messages in the terminal:**

```
✓ postgres_1  | database system is ready to accept connections
✓ app_1       | E-Learning System starting...
```

**When you see these messages:** The app is ready! 🎉

---

## 🖥️ How to Use the Application

### **Logging In**

**Admin Account:**
- Username: `admin`
- Password: `admin`

**Student Account:**
- Username: `AALu`
- Password: `12312123123`

### **Stopping the Application**

In the terminal, press: **`Ctrl + C`**

(On Windows: `Ctrl + C`, On Mac: `Cmd + C`)

### **Starting Again**

Run the same command:
```bash
docker-compose up
```

---

## 📊 What's Happening Behind the Scenes?

```
1. Docker reads docker-compose.yml
   ↓
2. Creates PostgreSQL container (database)
   ↓
3. Initializes database with tables and sample data
   ↓
4. Creates Java app container
   ↓
5. App connects to database
   ↓
6. App is ready to use!
```

---

## 🆘 Troubleshooting

### **"docker: command not found"**
- You need to install Docker Desktop
- After installing, restart your terminal and try again

### **"Port 5432 is already in use"**
- Close other PostgreSQL instances or Docker containers
- Or use different port in docker-compose.yml

### **"Cannot connect to database"**
- Wait 10 seconds and check terminal for error messages
- Look for "database system is ready to accept connections"

### **"Application crashes on startup"**
- Check if you're using Java 11 or higher (usually already installed)
- Read the error messages in terminal

### **Want to see logs?**
- Terminal shows all information automatically
- Look for ✓ or ✗ signs to see what's working

---

## 🎓 Useful Commands

### **See running containers:**
```bash
docker ps
```

### **Stop everything:**
```bash
docker-compose down
```

### **Remove database and start fresh:**
```bash
docker-compose down -v
```
Then run `docker-compose up` again

### **See logs from container:**
```bash
docker-compose logs -f
```

### **Restart only the app (not database):**
```bash
docker-compose restart app
```

---

## 📁 Project Structure for Docker

```
E-Learning-System/
├── Dockerfile                 (How to build the app)
├── docker-compose.yml         (How to run everything)
├── .dockerignore              (What to skip)
├── init-db/
│   └── 01-init.sql           (Database setup)
├── src/                        (Java source code)
├── build/                      (Compiled files)
└── lib/                        (Libraries needed)
```

---

## 🚀 Advanced: Customization

### **Change Database Password**

Edit `docker-compose.yml`:

```yaml
postgres:
  environment:
    POSTGRES_PASSWORD: your-new-password  # Change this
```

Also update in app section:
```yaml
app:
  environment:
    DATABASE_PASSWORD: your-new-password  # Same password
```

### **Change Port Numbers**

Edit `docker-compose.yml`:

```yaml
postgres:
  ports:
    - "5432:5432"  # Left = external, Right = internal
    # Change 5432 to another port like 5433
```

### **Increase Memory**

Edit `docker-compose.yml`:

```yaml
app:
  environment:
    JAVA_OPTS: "-Xmx1024m -Xms512m"  # Increase these numbers
```

---

## 💡 Tips & Tricks

✅ **Tip 1:** Keep terminal visible while using the app - it shows helpful messages

✅ **Tip 2:** First run takes longer (5-10 minutes) - it's downloading and building

✅ **Tip 3:** Second and later runs are faster (30 seconds)

✅ **Tip 4:** Database data persists - close app and reopen, your data is still there

✅ **Tip 5:** To completely reset everything: `docker-compose down -v` then `docker-compose up`

---

## 🔒 Security Note

These credentials are for development only:
- Don't use in production
- Don't expose to the internet
- For production, use strong passwords and SSL

---

## ❓ Still Have Questions?

### **Check these first:**
1. Read the terminal messages - they're very helpful!
2. Make sure Docker Desktop is running
3. Make sure you have internet (first run needs to download images)
4. Try stopping and starting again

### **Common Issues:**
- Port conflict? → Stop other Docker apps or databases
- Out of memory? → Close other apps
- Can't compile? → Update Docker Desktop to latest version

---

## 📞 Support

If you encounter issues:

1. **Take a screenshot** of the error message
2. **Note the command** you ran
3. **Provide terminal output** (copy the text)
4. **Describe what you were doing** when it happened

---

## ✨ Summary

| Step | What to Do | Time |
|------|-----------|------|
| 1 | Download Docker Desktop | 5 min |
| 2 | Download project folder | 2 min |
| 3 | Run `docker-compose up` | 1 min |
| **Total** | **You're done!** | **~8 min** |

**Result:** Complete E-Learning System running locally with database! 🎉

---

## 🎬 Next Steps

1. ✅ Get Docker Desktop running
2. ✅ Run `docker-compose up`
3. ✅ Login with admin/admin
4. ✅ Start exploring!

**Enjoy!** 🚀
