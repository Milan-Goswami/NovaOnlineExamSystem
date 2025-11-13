package com.nova.dao; // DAO layer for teacher-specific database operations

import com.nova.model.Teacher; // Teacher model class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TeacherDAO is responsible for interacting with the `teacher` table.
 * Contains helper methods to log teachers in (more methods can be added later).
 */
public class TeacherDAO {

    /**
     * Attempts to authenticate a teacher using email and password.
     *
     * @param email    teacher's login email
     * @param password teacher's password (plain text for demo purposes)
     * @return Teacher object if credentials are valid; otherwise null
     */
    public Teacher loginTeacher(String email, String password) {
        final String sql = "SELECT id, name, email, password FROM teacher WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return null; // Connection error means we cannot proceed

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Convert the row into a Teacher object using a helper method
                    return mapRowToTeacher(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the problem for debugging
        }

        return null; // Invalid credentials or an error occurred
    }

    /**
     * Registers a brand-new teacher account.
     *
     * @param teacher model containing name, email, and password
     * @return true if the insert succeeded
     */
    public boolean registerTeacher(Teacher teacher) {
        final String sql = "INSERT INTO teacher(name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return false;

            ps.setString(1, teacher.getName());
            ps.setString(2, teacher.getEmail());
            ps.setString(3, teacher.getPassword());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Utility method to convert the current ResultSet row into a Teacher.
     * Keeping this private keeps mapping logic in one place.
     */
    private Teacher mapRowToTeacher(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id"));
        teacher.setName(rs.getString("name"));
        teacher.setEmail(rs.getString("email"));
        teacher.setPassword(rs.getString("password"));
        return teacher;
    }
}

