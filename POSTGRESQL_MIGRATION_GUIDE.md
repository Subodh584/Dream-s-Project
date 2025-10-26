# PostgreSQL/Supabase Migration Guide

## 📋 Overview
This guide helps you migrate the E-Learning System from MySQL (WAMP) to PostgreSQL (Supabase).

---

## 🔧 Step 1: Update Maven/Gradle Dependencies

### For Maven (add to pom.xml):
```xml
<!-- PostgreSQL JDBC Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>
```

### For NetBeans Build:
1. Right-click on the project → Properties
2. Navigate to **Libraries**
3. Remove **MySQL Driver** library
4. Add **PostgreSQL Driver** JAR file:
   - Download: https://jdbc.postgresql.org/download/
   - Or use Maven Central: postgresql-42.7.1.jar

---

## 📁 Step 2: Updated Database Connection

The `DBConnection.java` file has been updated to:
```java
// Old (MySQL):
Class.forName("com.mysql.jdbc.Driver");
DriverManager.getConnection("jdbc:mysql:///ELearningSystem","root","");

// New (PostgreSQL):
Class.forName("org.postgresql.Driver");
DriverManager.getConnection(
    "jdbc:postgresql://db.acmvmyzmrrjvadhqhiyw.supabase.co:5432/postgres",
    "postgres",
    "Aaluparatha"
);
```

**Connection Details:**
- Host: `db.acmvmyzmrrjvadhqhiyw.supabase.co`
- Port: `5432`
- Database: `postgres`
- Username: `postgres`
- Password: `Aaluparatha`

---

## 🗄️ Step 3: Create Database Schema on Supabase

### Option A: Using Supabase Web UI
1. Go to [Supabase Dashboard](https://app.supabase.com)
2. Login with your credentials
3. Select your project
4. Open **SQL Editor**
5. Create a new query
6. Copy the entire content from `ELearningSystem_PostgreSQL.sql`
7. Paste and execute

### Option B: Using Command Line
```bash
# Install PostgreSQL client (if not already installed)
brew install postgresql  # macOS
# or
sudo apt-get install postgresql-client  # Linux

# Connect and run the SQL file
psql -h db.acmvmyzmrrjvadhqhiyw.supabase.co -U postgres -d postgres -f ELearningSystem_PostgreSQL.sql

# When prompted, enter password: Aaluparatha
```

---

## 📊 Step 4: SQL Schema Changes (MySQL → PostgreSQL)

### Key Differences Handled:

| Aspect | MySQL | PostgreSQL |
|--------|-------|-----------|
| **Auto Increment** | `AUTO_INCREMENT` | `SERIAL` |
| **Blob Type** | `MEDIUMBLOB` | `BYTEA` |
| **Case Sensitivity** | Insensitive (by default) | Sensitive (use lowercase) |
| **ON CONFLICT** | Not supported | ✓ Supported |
| **Constraints** | `ON DELETE CASCADE` | ✓ Supported |

### Schema Improvements in PostgreSQL Version:

1. **MessageUsers Table - FIXED**
   - Added `PRIMARY KEY` (was missing)
   - Added `id SERIAL PRIMARY KEY`
   - Maintained foreign key to Messages

2. **Added Indexes** for better performance:
   ```sql
   CREATE INDEX idx_subjects_adminid ON subjects(adminid);
   CREATE INDEX idx_enrollments_stdid ON enrollments(stdid);
   CREATE INDEX idx_messages_timestamp ON messages(time_stamp DESC);
   ```

3. **Added Timestamps** to all tables:
   ```sql
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   ```

4. **Enhanced Messages Table:**
   - Added `message_type` column
   - Added `is_read` boolean flag
   - Better indexing for queries

5. **Created Helpful Views:**
   - `user_inbox` - Recipient's messages
   - `user_sentbox` - Sender's messages

---

## 🔐 Security Recommendations

⚠️ **IMPORTANT**: The current password storage is insecure!

### Current Issue:
- Passwords stored in PLAIN TEXT
- Visible to database administrators

### Recommended Fix:
1. Use bcrypt or PBKDF2 for password hashing
2. Update all Java code to hash passwords before storing

**Example (Java with bcrypt):**
```java
import org.mindrot.jbcrypt.BCrypt;

// Hashing a password
String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

// Verifying a password
boolean isCorrect = BCrypt.checkpw(plainPassword, hashedPassword);
```

Add to dependencies:
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

---

## ✅ Step 5: Update Java Code (if needed)

### 1. Check Column Name Case
PostgreSQL is case-sensitive. Update queries if needed:

```java
// MySQL (works either way):
statement.executeQuery("SELECT * FROM Admin WHERE username='admin'");

// PostgreSQL (use lowercase):
statement.executeQuery("SELECT * FROM admin WHERE username='admin'");
```

### 2. BYTEA Handling (Picture Uploads)
```java
// PostgreSQL uses BYTEA for binary data
PreparedStatement ps = connection.prepareStatement(
    "UPDATE admin SET picture = ? WHERE adminid = ?"
);
ps.setBytes(1, imageBytes);
ps.setInt(2, adminId);
ps.executeUpdate();
```

### 3. Timestamp Handling
```java
// PostgreSQL handles timestamps well with java.sql.Timestamp
Timestamp timestamp = new Timestamp(System.currentTimeMillis());
ps.setTimestamp(1, timestamp);
```

---

## 🧪 Step 6: Testing the Connection

### Test Connection Code:
Create a simple test file: `TestConnection.java`

```java
import javasemesterproject.DBConnection;
import java.sql.ResultSet;

public class TestConnection {
    public static void main(String[] args) {
        try {
            DBConnection db = new DBConnection();
            
            // Test query
            ResultSet rs = db.s.executeQuery("SELECT * FROM admin LIMIT 1");
            
            if (rs.next()) {
                System.out.println("✓ Connection successful!");
                System.out.println("Admin: " + rs.getString("fname"));
            } else {
                System.out.println("⚠ No admin records found");
            }
            
            db.Close();
        } catch (Exception e) {
            System.err.println("✗ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

Run this to verify the connection works.

---

## 📝 Step 7: Update Project.properties (Optional)

If using NetBeans library references:
1. Open `nbproject/project.properties`
2. Find the MySQL library reference
3. Replace with PostgreSQL driver path

```properties
# Old:
javac.classpath=\
    ${libs.MySQLDriver.classpath}:\
    ${file.reference.rs2xml.jar}

# New:
javac.classpath=\
    /path/to/postgresql-42.7.1.jar:\
    ${file.reference.rs2xml.jar}
```

---

## 🔄 Step 8: Rebuild and Test

1. **Clean the project:**
   ```bash
   cd /Users/user62/Desktop/DREAM\'s\ Project/E-Learning-System-Java-GUI-Application
   rm -rf build dist
   ```

2. **Rebuild in NetBeans:**
   - Right-click project → Clean
   - Right-click project → Build

3. **Run the application:**
   - Run → Run Project (or F6)

4. **Test login:**
   - Username: `admin`
   - Password: `admin`

---

## 🆘 Troubleshooting

### Error: "Class not found: org.postgresql.Driver"
**Solution:** Add PostgreSQL JDBC JAR to classpath

### Error: "Connection refused"
**Solution:** 
- Verify Supabase project is running
- Check firewall settings
- Confirm URL and credentials are correct

### Error: "Table does not exist"
**Solution:** Run the SQL schema file in Supabase SQL Editor

### Error: "Column name is ambiguous"
**Solution:** Use fully qualified column names in queries

### Encoding Issues
**Solution:** PostgreSQL uses UTF-8 by default (better than MySQL's latin1)

---

## 📚 Useful PostgreSQL Queries

```sql
-- List all tables
\dt

-- Describe table structure
\d admin;

-- List all users
SELECT usename FROM pg_user;

-- Check database size
SELECT pg_database.datname, pg_size_pretty(pg_database.pg_database_size(pg_database.datname)) 
FROM pg_database;

-- Kill idle connections
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'postgres' AND pid <> pg_backend_pid();
```

---

## 🎯 Next Steps

1. ✅ Download PostgreSQL JDBC driver
2. ✅ Update DBConnection.java (already done)
3. ✅ Execute ELearningSystem_PostgreSQL.sql on Supabase
4. ✅ Rebuild project with PostgreSQL driver in classpath
5. ✅ Test connection
6. ✅ Test login functionality
7. ✅ Test all CRUD operations
8. 📋 Consider implementing password hashing (security improvement)

---

## 📞 Support

For issues with:
- **Supabase**: https://app.supabase.com/support
- **PostgreSQL**: https://www.postgresql.org/support/
- **JDBC Driver**: https://jdbc.postgresql.org/documentation/

---

**Last Updated:** October 27, 2025
**Status:** ✓ Ready for Migration
