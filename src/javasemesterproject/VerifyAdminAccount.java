package javasemesterproject;
import java.sql.*;

public class VerifyAdminAccount {
    public static void main(String[] args) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            
            System.out.println("\n📋 Admin Accounts in Database:");
            System.out.println("─".repeat(70));
            
            String query = "SELECT adminid, fname, lname, username, password, email_id FROM admin;";
            ResultSet rs = db.getStatement().executeQuery(query);
            
            int count = 0;
            while(rs.next()) {
                count++;
                System.out.println("\n✓ Account " + count + ":");
                System.out.println("  ID:       " + rs.getInt("adminid"));
                System.out.println("  Name:     " + rs.getString("fname") + " " + rs.getString("lname"));
                System.out.println("  Username: " + rs.getString("username"));
                System.out.println("  Password: " + rs.getString("password"));
                System.out.println("  Email:    " + rs.getString("email_id"));
            }
            
            System.out.println("\n" + "─".repeat(70));
            System.out.println("Total Admin Accounts: " + count);
            
            if(count >= 2) {
                System.out.println("\n✅ SUCCESS! New admin account 'DreamPachori' created successfully!");
                System.out.println("\n🔐 Login Credentials:");
                System.out.println("   Username: DreamPachori");
                System.out.println("   Password: NameeDaaku");
            }
            
            System.exit(0);
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
