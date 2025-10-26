package javasemesterproject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PostgreSQL/Supabase Connection Test Utility
 * Use this to verify your database connection is working properly
 */
public class TestPostgresConnection {
    
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  E-Learning System - PostgreSQL Connection Test");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        DBConnection db = null;
        try {
            // Test 1: Connection
            System.out.println("TEST 1: Attempting to connect to Supabase PostgreSQL...");
            db = new DBConnection();
            System.out.println("✓ SUCCESS: Connected to database!\n");
            
            // Test 2: Query Admin Table
            System.out.println("TEST 2: Querying admin table...");
            ResultSet adminRS = db.s.executeQuery("SELECT * FROM admin LIMIT 5");
            int adminCount = 0;
            while (adminRS.next()) {
                adminCount++;
                System.out.println("  - Admin ID: " + adminRS.getInt("adminid") + 
                                 ", Username: " + adminRS.getString("username") +
                                 ", Name: " + adminRS.getString("fname"));
            }
            System.out.println("✓ SUCCESS: Found " + adminCount + " admin(s)\n");
            
            // Test 3: Query Student Table
            System.out.println("TEST 3: Querying student table...");
            ResultSet studentRS = db.s.executeQuery("SELECT COUNT(*) as total FROM student");
            if (studentRS.next()) {
                int studentCount = studentRS.getInt("total");
                System.out.println("✓ SUCCESS: Total students: " + studentCount + "\n");
            }
            
            // Test 4: Query Teacher Table
            System.out.println("TEST 4: Querying teacher table...");
            ResultSet teacherRS = db.s.executeQuery("SELECT COUNT(*) as total FROM teacher");
            if (teacherRS.next()) {
                int teacherCount = teacherRS.getInt("total");
                System.out.println("✓ SUCCESS: Total teachers: " + teacherCount + "\n");
            }
            
            // Test 5: Query Subjects Table
            System.out.println("TEST 5: Querying subjects table...");
            ResultSet subjectRS = db.s.executeQuery("SELECT COUNT(*) as total FROM subjects");
            if (subjectRS.next()) {
                int subjectCount = subjectRS.getInt("total");
                System.out.println("✓ SUCCESS: Total subjects: " + subjectCount + "\n");
            }
            
            // Test 6: Query Courses Table
            System.out.println("TEST 6: Querying courses table...");
            ResultSet courseRS = db.s.executeQuery("SELECT COUNT(*) as total FROM courses");
            if (courseRS.next()) {
                int courseCount = courseRS.getInt("total");
                System.out.println("✓ SUCCESS: Total courses: " + courseCount + "\n");
            }
            
            // Test 7: Query Enrollments
            System.out.println("TEST 7: Querying enrollments table...");
            ResultSet enrollRS = db.s.executeQuery("SELECT COUNT(*) as total FROM enrollments");
            if (enrollRS.next()) {
                int enrollCount = enrollRS.getInt("total");
                System.out.println("✓ SUCCESS: Total enrollments: " + enrollCount + "\n");
            }
            
            // Test 8: Query Messages
            System.out.println("TEST 8: Querying messages table...");
            ResultSet msgRS = db.s.executeQuery("SELECT COUNT(*) as total FROM messages");
            if (msgRS.next()) {
                int msgCount = msgRS.getInt("total");
                System.out.println("✓ SUCCESS: Total messages: " + msgCount + "\n");
            }
            
            // Test 9: Database Version
            System.out.println("TEST 9: Checking PostgreSQL version...");
            ResultSet versionRS = db.s.executeQuery("SELECT version()");
            if (versionRS.next()) {
                String version = versionRS.getString(1);
                System.out.println("✓ PostgreSQL Version: " + version.split(",")[0] + "\n");
            }
            
            // Success Summary
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("  ✓ ALL TESTS PASSED!");
            System.out.println("  ✓ Your connection to Supabase PostgreSQL is working!");
            System.out.println("═══════════════════════════════════════════════════════");
            
        } catch (SQLException e) {
            System.err.println("\n✗ ERROR: " + e.getMessage());
            System.err.println("\nPossible causes:");
            System.err.println("1. PostgreSQL JDBC driver not in classpath");
            System.err.println("2. Supabase connection URL is incorrect");
            System.err.println("3. Database credentials (username/password) are wrong");
            System.err.println("4. Database tables haven't been created yet");
            System.err.println("5. Network connectivity issue to Supabase");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.Close();
                System.out.println("\nDatabase connection closed.");
            }
        }
    }
}
