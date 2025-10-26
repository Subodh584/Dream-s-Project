package javasemesterproject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class DBConnection {
    public Connection c;
    public Statement s;
    public DBConnection(){
        try{
            // SQLite JDBC Driver - no setup needed!
            Class.forName("org.sqlite.JDBC");
            
            // Create/Connect to local SQLite database file
            // Database file will be created automatically in project folder
            String dbPath = "ELearningSystem.db";
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            s = c.createStatement();
            
            System.out.println("✓ Connected to SQLite Database Successfully!");
            System.out.println("  Database: " + dbPath);
            
            // Initialize database schema if it doesn't exist
            initializeDatabase();
        }
        catch(ClassNotFoundException | SQLException e){
            System.err.println("✗ Database Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeDatabase() {
        try {
            // Check if tables exist
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "admin", null);
            
            if (!tables.next()) {
                System.out.println("Initializing database schema...");
                executeSQLSchema();
                System.out.println("✓ Database schema initialized successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    private void executeSQLSchema() throws SQLException {
        String[] sqlStatements = {
            // Admin Table
            "CREATE TABLE IF NOT EXISTS admin(" +
            "adminid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fname VARCHAR(150) NOT NULL," +
            "lname VARCHAR(150)," +
            "email_id VARCHAR(250)," +
            "username VARCHAR(20) NOT NULL UNIQUE," +
            "password VARCHAR(255)," +
            "gender VARCHAR(10)," +
            "picture BLOB," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Student Table
            "CREATE TABLE IF NOT EXISTS student(" +
            "stdid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fname VARCHAR(150) NOT NULL," +
            "lname VARCHAR(150)," +
            "email_id VARCHAR(250)," +
            "username VARCHAR(20) NOT NULL UNIQUE," +
            "password VARCHAR(255)," +
            "gender VARCHAR(10)," +
            "registration_date DATE DEFAULT CURRENT_DATE," +
            "last_login TIMESTAMP," +
            "picture BLOB," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Teacher Table
            "CREATE TABLE IF NOT EXISTS teacher(" +
            "teacherid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fname VARCHAR(150) NOT NULL," +
            "lname VARCHAR(150)," +
            "email_id VARCHAR(250)," +
            "username VARCHAR(20) NOT NULL UNIQUE," +
            "password VARCHAR(255)," +
            "gender VARCHAR(10)," +
            "registration_date DATE DEFAULT CURRENT_DATE," +
            "last_login TIMESTAMP," +
            "picture BLOB," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Subjects Table
            "CREATE TABLE IF NOT EXISTS subjects(" +
            "subject_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(150) NOT NULL UNIQUE," +
            "adminid INTEGER NOT NULL," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (adminid) REFERENCES admin(adminid) ON DELETE CASCADE)",
            
            // Courses Table
            "CREATE TABLE IF NOT EXISTS courses(" +
            "course_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(250) NOT NULL UNIQUE," +
            "description VARCHAR(1000) NOT NULL," +
            "content VARCHAR(3000) NOT NULL," +
            "teacherid INTEGER NOT NULL," +
            "subject_id INTEGER NOT NULL," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (teacherid) REFERENCES teacher(teacherid) ON DELETE CASCADE," +
            "FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE)",
            
            // Enrollments Table
            "CREATE TABLE IF NOT EXISTS enrollments(" +
            "enrollment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "enrollment_date DATE DEFAULT CURRENT_DATE," +
            "course_id INTEGER NOT NULL," +
            "stdid INTEGER NOT NULL," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE," +
            "FOREIGN KEY (stdid) REFERENCES student(stdid) ON DELETE CASCADE," +
            "UNIQUE(course_id, stdid))",
            
            // Messages Table
            "CREATE TABLE IF NOT EXISTS messages(" +
            "message_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "message VARCHAR(2000) NOT NULL," +
            "time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "user_id INTEGER NOT NULL," +
            "to_user_id INTEGER NOT NULL," +
            "message_type VARCHAR(20) DEFAULT 'text'," +
            "is_read INTEGER DEFAULT 0," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // MessageUsers Table
            "CREATE TABLE IF NOT EXISTS message_users(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "message_id INTEGER NOT NULL UNIQUE," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (message_id) REFERENCES messages(message_id) ON DELETE CASCADE)",
            
            // Insert default admin (if not exists)
            "INSERT OR IGNORE INTO admin (fname, lname, email_id, username, password, gender) " +
            "VALUES('Suwaid', 'Aslam', 'Suwaid@admin.com', 'admin', 'admin', 'Male')",
            
            // Insert sample student (if not exists)
            "INSERT OR IGNORE INTO student (fname, lname, email_id, username, password, gender, registration_date) " +
            "VALUES('Subodh', 'Student', 'subodh@student.com', 'AALu', '12312123123', 'Male', DATE('now'))"
        };
        
        for (String sql : sqlStatements) {
            try {
                s.execute(sql);
            } catch (SQLException e) {
                // Table might already exist, continue
                if (!e.getMessage().contains("already exists")) {
                    System.err.println("SQL Error: " + e.getMessage());
                }
            }
        }
    }
    
    public void Close(){
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
