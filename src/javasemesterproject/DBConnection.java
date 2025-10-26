package javasemesterproject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class DBConnection {
    public Connection c;
    public Statement s;
    public DBConnection(){
        try{
            //Register JDBC Driver with Class's Static method
            Class.forName("org.postgresql.Driver");
            
            // Read database configuration from environment variables (for Docker)
            // or use defaults (for local development)
            String dbHost = System.getenv("DATABASE_HOST") != null ? 
                System.getenv("DATABASE_HOST") : "localhost";
            String dbPort = System.getenv("DATABASE_PORT") != null ? 
                System.getenv("DATABASE_PORT") : "5432";
            String dbName = System.getenv("DATABASE_NAME") != null ? 
                System.getenv("DATABASE_NAME") : "ELearningSystem";
            String dbUser = System.getenv("DATABASE_USER") != null ? 
                System.getenv("DATABASE_USER") : "user62";
            String dbPass = System.getenv("DATABASE_PASSWORD") != null ? 
                System.getenv("DATABASE_PASSWORD") : "";
            
            // Build connection string
            String connectionURL = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            
            // Log connection details (for debugging)
            System.out.println("Connecting to: " + dbHost + ":" + dbPort + "/" + dbName);
            
            // Connect to database
            c = DriverManager.getConnection(connectionURL, dbUser, dbPass);
            s = c.createStatement();
            System.out.println("✓ Connected to PostgreSQL Database Successfully!");
        }
        catch(ClassNotFoundException | SQLException e){
            System.err.println("✗ Database Connection Error: " + e.getMessage());
            e.printStackTrace();
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
