package com.nova.dao; // DAO package for data access classes

import com.nova.model.Student; // Import the Student model

import java.sql.Connection; // JDBC connection
import java.sql.PreparedStatement; // Precompiled SQL statements to prevent SQL injection
import java.sql.ResultSet; // Holds data returned from SELECT queries
import java.sql.SQLException; // Represents SQL errors

/**
 * StudentDAO handles database operations related to students.
 * Uses PreparedStatement and the shared DBConnection utility.
 */
public class StudentDAO {

    /**
     * Registers a new student by inserting a row into the student table.
     * @param s the student to register
     * @return true if one row was inserted; false otherwise
     */
    public boolean registerStudent(Student s) {
        final String sql = "INSERT INTO student(name, email, password) VALUES (?, ?, ?)"; // SQL with placeholders
        try (Connection conn = DBConnection.getConnection(); // Acquire a DB connection
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) { // Prepare the statement

            if (ps == null) return false; // If connection failed, return false

            // Bind parameters to the SQL placeholders in order
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());

            // Execute the insert and check affected rows
            int rows = ps.executeUpdate();
            return rows == 1; // true if exactly one row inserted
        } catch (SQLException e) { // Handle SQL errors
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    /**
     * Attempts to log in a student by matching email and password.
     * @param email student's email
     * @param password student's password
     * @return a Student object if credentials are valid; otherwise null
     */
    public Student login(String email, String password) {
        final String sql = "SELECT id, name, email, password FROM student WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return null; // Connection failed

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) { // Execute query and obtain results
                if (rs.next()) { // If a row exists, map it to a Student object
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPassword(rs.getString("password"));
                    return s; // Successful login
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No match or error
    }

    /**
     * Retrieves a student by their numeric ID.
     * @param id primary key value in student table
     * @return Student if found; otherwise null
     */
    public Student getStudentById(int id) {
        final String sql = "SELECT id, name, email, password FROM student WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return null;
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPassword(rs.getString("password"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found
    }
}


