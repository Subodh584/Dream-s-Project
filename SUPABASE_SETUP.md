# 🚀 Supabase Setup Guide

## Quick Setup (2 minutes)

### Step 1: Open Supabase SQL Editor
1. Go to https://app.supabase.com
2. Login with your account
3. Select your project (db.acmvmyzmrrjvadhqhiyw)
4. Click **"SQL Editor"** on the left sidebar

### Step 2: Create the Database Schema
1. Click **"New Query"**
2. Paste the entire content from `ELearningSystem_PostgreSQL.sql`
3. Click **"Run"** button
4. Wait for success message ✓

### Step 3: Verify Tables Created
Run this query to check:
```sql
SELECT tablename FROM pg_tables WHERE schemaname='public';
```

You should see these 8 tables:
- admin
- student
- teacher
- subject
- course
- course_enrollment
- messages
- notifications

### Step 4: Test the Connection
1. In VS Code terminal, run:
```bash
cd ~/Desktop/DREAM\'s\ Project/E-Learning-System-Java-GUI-Application
java -cp "build/classes:lib/PostgreSQLDriver/*:lib/rs2xml.jar" javasemesterproject.TestPostgresConnection
```

2. Look for: **✓ ALL TESTS PASSED**

If you see errors, check:
- ✓ Database tables exist
- ✓ You used correct credentials
- ✓ Database is public (check Supabase settings)

### Step 5: Run the Application
```bash
java -cp "build/classes:lib/PostgreSQLDriver/*:lib/rs2xml.jar" javasemesterproject.Main
```

Login with: **admin / admin**

---

## 🎯 What Changed

| Item | Before | After |
|------|--------|-------|
| Database | MySQL (local) | PostgreSQL (Supabase cloud) |
| Driver | mysql-connector-java-5.1.23 | postgresql-42.7.1 |
| Connection URL | jdbc:mysql:///ELearningSystem | jdbc:postgresql://db.acmvmyzmrrjvadhqhiyw.supabase.co:5432/postgres |
| Credentials | root / (empty) | postgres / Aaluparatha |
| Location | Your computer | Supabase cloud |

---

## ⚠️ Common Issues

### "Connection refused"
- Check internet connection
- Verify Supabase project is active
- Check credentials in DBConnection.java

### "Table does not exist"
- Run the SQL schema from `ELearningSystem_PostgreSQL.sql`
- Make sure query completed successfully

### "Authentication failed"
- Verify username is "postgres"
- Verify password is "Aaluparatha"
- Check no typos in connection string

### "Driver not found"
- Verify postgresql-42.7.1.jar exists in lib/PostgreSQLDriver/
- Compilation should have worked (check build/classes/)

---

## ✓ You're Done!

Your E-Learning System now uses:
- ✅ PostgreSQL (modern, reliable)
- ✅ Supabase cloud (no local setup needed)
- ✅ Works from anywhere with internet
- ✅ Automatic backups and monitoring

**Next: Open your application and test login with admin/admin**
