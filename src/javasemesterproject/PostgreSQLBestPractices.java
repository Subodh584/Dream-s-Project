package javasemesterproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PostgreSQL Best Practices for E-Learning System
 * 
 * This file demonstrates recommended approaches for:
 * - Using PreparedStatements instead of Statement (prevents SQL injection)
 * - Proper error handling
 * - Connection pooling
 */
public class PostgreSQLBestPractices {
    
    /**
     * UNSAFE: Using Statement (vulnerable to SQL injection)
     * DON'T DO THIS!
     */
    @Deprecated
    public static void unsafeLogin(String username, String password) {
        DBConnection db = new DBConnection();
        try {
            // ✗ VULNERABLE TO SQL INJECTION
            String query = "SELECT * FROM admin WHERE username = '" + username + 
                          "' AND password = '" + password + "'";
            ResultSet rs = db.s.executeQuery(query);
            
            if (rs.next()) {
                System.out.println("Login successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * SAFE: Using PreparedStatement (SQL injection proof)
     * RECOMMENDED APPROACH
     */
    public static void safeLogin(String username, String password) {
        DBConnection db = new DBConnection();
        try {
            // ✓ SAFE - Uses parameterized queries
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ Login successful");
                System.out.println("  Admin ID: " + rs.getInt("adminid"));
                System.out.println("  Name: " + rs.getString("fname"));
            } else {
                System.out.println("✗ Invalid credentials");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Database error: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe User Registration
     */
    public static void registerStudent(String fname, String lname, String email,
                                       String username, String password, 
                                       String gender) {
        DBConnection db = new DBConnection();
        try {
            String query = "INSERT INTO student (fname, lname, email_id, username, " +
                          "password, gender, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);  // TODO: Hash this with bcrypt!
            pstmt.setString(6, gender);
            pstmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✓ Student registered successfully");
            } else {
                System.out.println("✗ Registration failed");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error during registration: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe Message Sending
     */
    public static void sendMessage(int fromUserId, int toUserId, String messageText) {
        DBConnection db = new DBConnection();
        try {
            String query = "INSERT INTO messages (user_id, to_user_id, message, " +
                          "time_stamp, is_read) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setInt(1, fromUserId);
            pstmt.setInt(2, toUserId);
            pstmt.setString(3, messageText);
            pstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setBoolean(5, false);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✓ Message sent successfully");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error sending message: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe Course Enrollment
     */
    public static void enrollStudent(int studentId, int courseId) {
        DBConnection db = new DBConnection();
        try {
            String query = "INSERT INTO enrollments (stdid, course_id, enrollment_date) " +
                          "VALUES (?, ?, ?)";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✓ Student enrolled successfully");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("unique")) {
                System.out.println("⚠ Student already enrolled in this course");
            } else {
                System.err.println("✗ Error during enrollment: " + e.getMessage());
            }
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe Query with Filtering
     */
    public static void searchStudents(String nameFilter) {
        DBConnection db = new DBConnection();
        try {
            // Using ILIKE for case-insensitive search in PostgreSQL
            String query = "SELECT stdid, fname, lname, email_id FROM student " +
                          "WHERE fname ILIKE ? OR lname ILIKE ? LIMIT 20";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            String searchTerm = "%" + nameFilter + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("Search Results:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString("fname") + " " + 
                                 rs.getString("lname") + " (" + 
                                 rs.getString("email_id") + ")");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Search error: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe Update Operation
     */
    public static void updatePassword(int adminId, String newPassword) {
        DBConnection db = new DBConnection();
        try {
            String query = "UPDATE admin SET password = ? WHERE adminid = ?";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, newPassword);  // TODO: Hash this with bcrypt!
            pstmt.setInt(2, adminId);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✓ Password updated successfully");
            } else {
                System.out.println("✗ Admin not found");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error updating password: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * Example: Safe Delete Operation
     */
    public static void deleteStudent(int studentId) {
        DBConnection db = new DBConnection();
        try {
            // PostgreSQL will cascade delete enrollments and messages
            String query = "DELETE FROM student WHERE stdid = ?";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setInt(1, studentId);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✓ Student deleted successfully");
            } else {
                System.out.println("⚠ Student not found");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error deleting student: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * PostgreSQL Specific: Use RETURNING clause for efficiency
     */
    public static int addNewSubject(String subjectName, int adminId) {
        DBConnection db = new DBConnection();
        try {
            // RETURNING is a PostgreSQL feature - returns generated ID
            String query = "INSERT INTO subjects (name, adminid) VALUES (?, ?) RETURNING subject_id";
            
            PreparedStatement pstmt = db.c.prepareStatement(query);
            pstmt.setString(1, subjectName);
            pstmt.setInt(2, adminId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int subjectId = rs.getInt("subject_id");
                System.out.println("✓ Subject created with ID: " + subjectId);
                pstmt.close();
                return subjectId;
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error creating subject: " + e.getMessage());
        } finally {
            db.Close();
        }
        return -1;
    }
    
    /**
     * PostgreSQL Specific: Batch Insert for better performance
     */
    public static void batchInsertSubjects(String[] subjectNames, int adminId) {
        DBConnection db = new DBConnection();
        try {
            String query = "INSERT INTO subjects (name, adminid) VALUES (?, ?)";
            PreparedStatement pstmt = db.c.prepareStatement(query);
            
            for (String subjectName : subjectNames) {
                pstmt.setString(1, subjectName);
                pstmt.setInt(2, adminId);
                pstmt.addBatch();
            }
            
            int[] results = pstmt.executeBatch();
            System.out.println("✓ Inserted " + results.length + " subjects successfully");
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error in batch insert: " + e.getMessage());
        } finally {
            db.Close();
        }
    }
    
    /**
     * RECOMMENDATION: Implement Connection Pooling
     * For production use, consider using HikariCP or C3P0
     * 
     * Maven dependency for HikariCP:
     * <dependency>
     *     <groupId>com.zaxxer</groupId>
     *     <artifactId>HikariCP</artifactId>
     *     <version>5.1.0</version>
     * </dependency>
     */
    
    public static void main(String[] args) {
        System.out.println("PostgreSQL Best Practices Examples");
        System.out.println("====================================\n");
        
        // Example usage
        System.out.println("1. Safe Login Example:");
        safeLogin("admin", "admin");
        
        System.out.println("\n2. Search Example:");
        searchStudents("John");
    }
}
