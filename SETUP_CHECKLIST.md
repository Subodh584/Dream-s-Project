# PostgreSQL Migration Checklist ✓

## 📋 Quick Start Checklist

- [ ] **Step 1: Download PostgreSQL JDBC Driver**
  - Download from: https://jdbc.postgresql.org/download/
  - Version needed: 42.7.1 or later
  - Save to: `/Users/user62/Desktop/DREAM's Project/E-Learning-System-Java-GUI-Application/lib/`
  - Filename: `postgresql-42.7.1.jar`

- [ ] **Step 2: Update NetBeans Project (if using library references)**
  - Open project in NetBeans
  - Right-click project → Properties
  - Go to Libraries tab
  - Remove MySQL Driver library
  - Add PostgreSQL JDBC JAR file

- [ ] **Step 3: Create Database on Supabase**
  - Visit: https://app.supabase.com
  - Login to your Supabase project
  - Open SQL Editor
  - Create new query
  - Copy & paste entire content from `ELearningSystem_PostgreSQL.sql`
  - Execute the query
  - Verify all tables are created

- [ ] **Step 4: Verify Connection**
  - Build the project: `Clean and Build` in NetBeans
  - Run test: `TestPostgresConnection.java`
  - Should see "ALL TESTS PASSED"

- [ ] **Step 5: Test Application**
  - Run: `LoadingScreen.java` or `Main.java`
  - Try login with: admin / admin
  - If it works, migration is successful! 🎉

- [ ] **Step 6: Test Core Features**
  - [ ] Login as Admin
  - [ ] Add a new subject
  - [ ] Login as Teacher
  - [ ] Add a new course
  - [ ] Login as Student
  - [ ] Enroll in a course
  - [ ] Send a message
  - [ ] Withdraw from course

---

## 🔗 Connection Details (Reference)

**Host:** `db.acmvmyzmrrjvadhqhiyw.supabase.co`
**Port:** `5432`
**Database:** `postgres`
**Username:** `postgres`
**Password:** `Aaluparatha`
**JDBC URL:** `jdbc:postgresql://db.acmvmyzmrrjvadhqhiyw.supabase.co:5432/postgres`

---

## 📁 Files Modified/Created

### Modified:
- ✏️ `src/javasemesterproject/DBConnection.java` - Updated connection settings

### Created:
- 📄 `ELearningSystem_PostgreSQL.sql` - PostgreSQL schema (improved)
- 📄 `POSTGRESQL_MIGRATION_GUIDE.md` - Detailed migration guide
- 📄 `SETUP_CHECKLIST.md` - This file
- 📄 `src/javasemesterproject/TestPostgresConnection.java` - Connection test utility

### Unchanged (but may need code updates):
- All GUI and business logic files work with PostgreSQL
- Minor adjustments might be needed for specific queries using MySQL syntax

---

## ⚠️ Important Notes

1. **Column Names:** PostgreSQL is case-sensitive. Make sure all your SQL queries use lowercase table/column names:
   ```java
   // ✗ WRONG (for PostgreSQL):
   "SELECT * FROM Admin WHERE Adminid = 1"
   
   // ✓ CORRECT:
   "SELECT * FROM admin WHERE adminid = 1"
   ```

2. **Picture Upload:** Uses BYTEA instead of MEDIUMBLOB. No code changes needed, works the same.

3. **Security:** Current passwords are stored in PLAIN TEXT. Consider implementing bcrypt hashing (see guide for details).

4. **Performance:** PostgreSQL typically performs better than MySQL for this type of application.

---

## 🆘 Troubleshooting

| Problem | Solution |
|---------|----------|
| "Driver not found" | Add postgresql JAR to classpath |
| "Connection refused" | Check Supabase project is running, verify credentials |
| "Table not found" | Run SQL schema file on Supabase |
| "Wrong password" | Verify credentials in DBConnection.java |
| "Network error" | Check internet connection, firewall settings |

---

## 📞 Need Help?

1. Run `TestPostgresConnection.java` to diagnose connection issues
2. Check the detailed `POSTGRESQL_MIGRATION_GUIDE.md`
3. Visit Supabase Dashboard to verify database state
4. Check PostgreSQL driver version compatibility

---

## ✅ Success Indicators

After migration, you should see:
- ✓ No MySQL connection errors
- ✓ All tables visible in Supabase SQL Editor
- ✓ Login works with admin/admin
- ✓ Can perform CRUD operations (Create, Read, Update, Delete)
- ✓ Messages system works
- ✓ Enrollment system works

---

**Status:** Ready for Migration 🚀
**Last Updated:** October 27, 2025
