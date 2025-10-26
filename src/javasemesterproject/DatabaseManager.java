package javasemesterproject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton Database Manager
 * Ensures only ONE database connection is used throughout the application
 * This prevents SQLite "database is locked" errors
 */
public class DatabaseManager {
    private static DatabaseManager instance = null;
    public Connection c;
    public Statement s;
    private static final Object lock = new Object();
    
    private DatabaseManager(){
        try{
            // SQLite JDBC Driver - no setup needed!
            Class.forName("org.sqlite.JDBC");
            
            // Create/Connect to local SQLite database file
            String dbPath = "ELearningSystem.db";
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            
            // Enable journal mode for better concurrency
            s = c.createStatement();
            s.execute("PRAGMA journal_mode=WAL");
            s.execute("PRAGMA busy_timeout=5000");  // 5 second timeout
            s.execute("PRAGMA synchronous=NORMAL");
            
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
    
    /**
     * Get singleton instance of DatabaseManager
     */
    public static DatabaseManager getInstance(){
        if(instance == null){
            synchronized(lock){
                if(instance == null){
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // Check if tables exist
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "admin", null);
            
            boolean tablesExist = tables.next();
            
            if (!tablesExist) {
                System.out.println("Initializing database schema...");
            }
            
            // Always execute schema (creates tables if not exist, and inserts sample data)
            executeSQLSchema();
            
            if (!tablesExist) {
                System.out.println("✓ Database schema initialized successfully!");
            } else {
                System.out.println("✓ Database schema verified!");
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
            "VALUES('Dream', 'Pachori', 'Dream@admin.com', 'admin', 'admin', 'Male')",
            
            // Insert new admin account (if not exists)
            "INSERT OR IGNORE INTO admin (fname, lname, email_id, username, password, gender) " +
            "VALUES('Dream', 'Pachori', 'DreamPachori@admin.com', 'DreamPachori', 'NameeDaaku', 'Male')",
            
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
    
    /**
     * Get a connection (returns the singleton instance's connection)
     */
    public Connection getConnection(){
        return c;
    }
    
    /**
     * Get a statement (returns the singleton instance's statement)
     */
    public Statement getStatement(){
        return s;
    }
    
    /**
     * Execute query with automatic retry on busy timeout
     */
    public ResultSet executeQuery(String query) throws SQLException {
        int retries = 0;
        while(retries < 3){
            try {
                return s.executeQuery(query);
            } catch(SQLException e){
                if(e.getMessage().contains("database is locked")){
                    retries++;
                    try {
                        Thread.sleep(500);  // Wait 500ms before retry
                    } catch(InterruptedException ex){
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new SQLException("Database is locked after 3 retries");
    }
    
    /**
     * Execute update with automatic retry on busy timeout
     */
    public int executeUpdate(String query) throws SQLException {
        int retries = 0;
        while(retries < 3){
            try {
                return s.executeUpdate(query);
            } catch(SQLException e){
                if(e.getMessage().contains("database is locked")){
                    retries++;
                    try {
                        Thread.sleep(500);  // Wait 500ms before retry
                    } catch(InterruptedException ex){
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new SQLException("Database is locked after 3 retries");
    }
    
    public void Close(){
        try {
            if(c != null && !c.isClosed()){
                c.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
