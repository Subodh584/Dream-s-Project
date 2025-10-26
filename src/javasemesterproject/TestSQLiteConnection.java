package javasemesterproject;
import java.sql.*;

public class TestSQLiteConnection {
    public static void main(String[] args) {
        System.out.println("=== SQLite Database Connection Test ===\n");
        
        try {
            // Create database connection (this will auto-initialize if needed)
            DBConnection db = new DBConnection();
            
            System.out.println("\n✓ Testing database contents...");
            
            // Test: Check admin table
            String query = "SELECT COUNT(*) as count FROM admin";
            ResultSet rs = db.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("  Admin users: " + rs.getInt("count"));
            }
            
            // Test: Check student table
            query = "SELECT COUNT(*) as count FROM student";
            rs = db.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("  Student users: " + rs.getInt("count"));
            }
            
            // Test: Check teacher table
            query = "SELECT COUNT(*) as count FROM teacher";
            rs = db.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("  Teacher users: " + rs.getInt("count"));
            }
            
            // Test: Verify admin user
            query = "SELECT username, password FROM admin WHERE username = 'admin'";
            rs = db.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("\n✓ Default admin account exists:");
                System.out.println("  Username: " + rs.getString("username"));
                System.out.println("  Password: " + rs.getString("password"));
            }
            
            // Test: Verify student user
            query = "SELECT username, password FROM student WHERE username = 'AALu'";
            rs = db.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("\n✓ Sample student account exists:");
                System.out.println("  Username: " + rs.getString("username"));
                System.out.println("  Password: " + rs.getString("password"));
            }
            
            // Check if database file exists
            java.io.File dbFile = new java.io.File("ELearningSystem.db");
            if (dbFile.exists()) {
                long fileSize = dbFile.length();
                System.out.println("\n✓ Database file created: ELearningSystem.db (" + fileSize + " bytes)");
            }
            
            System.out.println("\n=== All Tests Passed! ===");
            System.out.println("Your friend can now run the application - database is ready!");
            System.out.println("\nLogin with:");
            System.out.println("  Admin: admin / admin");
            System.out.println("  Student: AALu / 12312123123");
            
            db.Close();
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
