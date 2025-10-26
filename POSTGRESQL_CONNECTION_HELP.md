# ⚠️ PostgreSQL Connection Troubleshooting

## Current Status
✓ PostgreSQL JDBC driver installed (postgresql-42.7.1.jar)
✓ DBConnection.java updated to PostgreSQL
✓ All Java files compiled successfully
✗ Cannot connect to Supabase host (DNS resolution failed)

---

## What to Do Next

### Option 1: Verify Your Supabase Project (Recommended)

**Check if your Supabase project is active:**

1. Go to https://app.supabase.com
2. Login to your account
3. Look for project: **db.acmvmyzmrrjvadhqhiyw**
4. If project exists, get the correct credentials:
   - Go to **Settings** → **Database**
   - Copy the connection string
   - Look for host: Should look like `db.xxxxx.supabase.co`
   - Username should be `postgres`
   - Password is what you set during project creation

**If you cannot find it:**
- The project may have been deleted
- Create a new Supabase project
- Share the new connection details

---

### Option 2: Use Local PostgreSQL (Temporary)

If Supabase is unavailable, you can test locally:

**Step 1: Check if PostgreSQL is installed**
```bash
which psql
```

**Step 2: If not installed, install via Homebrew:**
```bash
brew install postgresql@15
```

**Step 3: Start PostgreSQL:**
```bash
brew services start postgresql@15
```

**Step 4: Create database and user:**
```bash
createdb ELearningSystem
psql -U postgres -d ELearningSystem < ELearningSystem_PostgreSQL.sql
```

**Step 5: Update DBConnection.java**
Change this line in DBConnection.java:
```java
c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ELearningSystem","postgres","password");
```
Replace `password` with your PostgreSQL password (or leave empty if none)

**Step 6: Recompile:**
```bash
cd ~/Desktop/DREAM\'s\ Project/E-Learning-System-Java-GUI-Application
javac -cp "lib/PostgreSQLDriver/*:lib/rs2xml.jar" -d build/classes $(find src -name "*.java")
```

**Step 7: Test:**
```bash
java -cp "build/classes:lib/PostgreSQLDriver/*:lib/rs2xml.jar" javasemesterproject.TestPostgresConnection
```

---

### Option 3: Revert to MySQL (Last Resort)

If you want to go back to MySQL temporarily:

**Step 1: Revert DBConnection.java:**
```java
Class.forName("com.mysql.jdbc.Driver");
c = DriverManager.getConnection("jdbc:mysql:///ELearningSystem","root","");
```

**Step 2: Update MySQL Connection String:**
For MySQL 8.0+, add `allowPublicKeyRetrieval`:
```java
c = DriverManager.getConnection("jdbc:mysql:///ELearningSystem?allowPublicKeyRetrieval=true&useSSL=false","root","");
```

**Step 3: Recompile:**
```bash
javac -cp "lib/MySQLDriver/*:lib/rs2xml.jar" -d build/classes $(find src -name "*.java")
```

---

## 🎯 Next Steps

**Choose one of the three options above:**

1. **Best Solution:** Fix Supabase (most reliable, cloud-based)
2. **Good Alternative:** Use local PostgreSQL (reliable, free)
3. **Quick Fix:** Revert to MySQL with updated driver (not recommended long-term)

---

## 📋 Quick Decision Guide

```
Do you have an active Supabase project?
├─ YES → Use Option 1 (verify credentials)
├─ NO, but want to use Supabase → Create new project, then Option 1
├─ NO, prefer local database → Use Option 2 (PostgreSQL locally)
└─ Just want it to work now → Use Option 3 (MySQL)
```

---

## 📞 Need Help?

Run this to diagnose:
```bash
# Test DNS resolution
nslookup db.acmvmyzmrrjvadhqhiyw.supabase.co

# Test connection to local PostgreSQL
psql -U postgres -d ELearningSystem -c "SELECT version();"

# Check MySQL status
mysql -u root -e "SELECT VERSION();"
```

Share the output if you need help troubleshooting.
