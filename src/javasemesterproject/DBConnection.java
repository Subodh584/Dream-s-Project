package javasemesterproject;
import java.sql.*;
        
/**
 * Legacy DBConnection class - now delegates to DatabaseManager singleton
 * This maintains backward compatibility with existing code
 */
public class DBConnection {
    public Connection c;
    public Statement s;
    
    public DBConnection(){
        // Get the singleton database manager instance
        DatabaseManager dm = DatabaseManager.getInstance();
        this.c = dm.getConnection();
        this.s = dm.getStatement();
    }
    
    public void Close(){
        // Don't close the singleton connection
        // It stays open for the entire application lifetime
    }
}
