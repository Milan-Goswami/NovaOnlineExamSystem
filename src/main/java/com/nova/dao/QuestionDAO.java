package com.nova.dao; // DAO package

import com.nova.model.Question; // Model mapping for questions

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // List implementation
import java.util.List; // Interface for returning multiple rows

/**
 * QuestionDAO provides CRUD-like operations for question records.
 */
public class QuestionDAO {

    /**
     * Retrieves all questions from the database.
     * @return list of Question objects (empty list if none or on error)
     */
    public List<Question> getAllQuestions() {
        final String sql = "SELECT id, question_text, option_a, option_b, option_c, option_d, correct_option FROM question";
        List<Question> list = new ArrayList<>(); // Prepare container for results

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return list; // Return empty list if connection failed
            try (ResultSet rs = ps.executeQuery()) { // Run the SELECT
                while (rs.next()) { // Iterate over each row
                    list.add(mapRowToQuestion(rs)); // Map current row to Question and add to list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Inserts a new question into the database.
     * @param q the Question to add
     * @return true if exactly one row was inserted
     */
    public boolean addQuestion(Question q) {
        final String sql = "INSERT INTO question(question_text, option_a, option_b, option_c, option_d, correct_option) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return false;

            // Bind each column value in order
            ps.setString(1, q.getQuestionText());
            ps.setString(2, q.getOptionA());
            ps.setString(3, q.getOptionB());
            ps.setString(4, q.getOptionC());
            ps.setString(5, q.getOptionD());
            ps.setString(6, q.getCorrectOption());

            int rows = ps.executeUpdate(); // Execute INSERT
            return rows == 1; // Expect exactly one row affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Returns total count of questions in the question table
     * @return total number of questions
     */
    public int getTotalQuestionsCount() {
        final String sql = "SELECT COUNT(*) as total FROM question";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return 0;
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * Retrieves a single question by its ID.
     * @param id question primary key
     * @return Question if found; null otherwise
     */
    public Question getQuestionById(int id) {
        final String sql = "SELECT id, question_text, option_a, option_b, option_c, option_d, correct_option FROM question WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return null;
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // If a row is found, map it
                    return mapRowToQuestion(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found
    }

    /**
     * Updates an existing question using the values from the provided Question object.
     * @param q question containing the new values (must include the ID)
     * @return true if exactly one row was updated
     */
    public boolean updateQuestion(Question q) {
        final String sql = "UPDATE question SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_option = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return false;

            ps.setString(1, q.getQuestionText());
            ps.setString(2, q.getOptionA());
            ps.setString(3, q.getOptionB());
            ps.setString(4, q.getOptionC());
            ps.setString(5, q.getOptionD());
            ps.setString(6, q.getCorrectOption());
            ps.setInt(7, q.getId());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a question from the database by its ID.
     * @param id primary key to remove
     * @return true if exactly one row was deleted
     */
    public boolean deleteQuestion(int id) {
        final String sql = "DELETE FROM question WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return false;

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to convert a ResultSet row into a Question object
    private Question mapRowToQuestion(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getInt("id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        return q;
    }
}


