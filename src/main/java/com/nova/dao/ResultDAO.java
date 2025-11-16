// package com.nova.dao; // DAO package

// import com.nova.model.Result; // Model for result rows

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * ResultDAO stores and retrieves exam results.
//  */
// public class ResultDAO {

//     /**
//      * Saves a result record for a student.
//      * @param r the result to save
//      * @return true if one row was inserted
//      */
//     public boolean saveResult(Result r) {
//         final String sql = "INSERT INTO result(student_id, score, exam_date) VALUES (?, ?, NOW())";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

//             if (ps == null) return false;
//             ps.setInt(1, r.getStudentId());
//             ps.setInt(2, r.getScore());
//             int rows = ps.executeUpdate();
//             return rows == 1;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     /**
//      * Returns all results for a given student ID.
//      * @param studentId foreign key to student.id
//      * @return list of Result objects (possibly empty)
//      */
//     public List<Result> getResultsByStudent(int studentId) {
//         final String sql = "SELECT id, student_id, score, exam_date FROM result WHERE student_id = ? ORDER BY id DESC";
//         List<Result> results = new ArrayList<>();
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

//             if (ps == null) return results;
//             ps.setInt(1, studentId);
//             try (ResultSet rs = ps.executeQuery()) {
//                 while (rs.next()) {
//                     results.add(mapRowToResult(rs));
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return results;
//     }

//     /**
//      * Returns the top results ordered by score descending.
//      * @param limit maximum number of rows to return
//      * @return list of Result objects up to the given limit
//      */
//     public List<Result> getTopResults(int limit) {
//         final String sql = "SELECT id, student_id, score FROM result ORDER BY score DESC, id ASC LIMIT ?";
//         List<Result> results = new ArrayList<>();
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

//             if (ps == null) return results;
//             ps.setInt(1, Math.max(0, limit)); // Defensive: avoid negative limits
//             try (ResultSet rs = ps.executeQuery()) {
//                 while (rs.next()) {
//                     results.add(mapRowToResult(rs));
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return results;
//     }

//     // Helper to map current row to Result object
//     private Result mapRowToResult(ResultSet rs) throws SQLException {
//         Result r = new Result();
//         r.setId(rs.getInt("id"));
//         r.setStudentId(rs.getInt("student_id"));
//         r.setScore(rs.getInt("score"));
//         return r;
//     }
// }





package com.nova.dao; // DAO package

import com.nova.model.Result; // Model for result rows

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ResultDAO stores and retrieves exam results.
 */
public class ResultDAO {

    /**
     * Saves a result record for a student.
     * @param r the result to save
     * @return true if one row was inserted
     */
    public boolean saveResult(Result r) {
        final String sql = "INSERT INTO result(student_id, score, exam_date) VALUES (?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return false;
            ps.setInt(1, r.getStudentId());
            ps.setInt(2, r.getScore());
            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns all results for a given student ID.
     * @param studentId foreign key to student.id
     * @return list of Result objects (possibly empty)
     */
    public List<Result> getResultsByStudent(int studentId) {
        final String sql = "SELECT id, student_id, score, exam_date FROM result WHERE student_id = ? ORDER BY id DESC";
        List<Result> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return results;
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRowToResult(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Returns the top results ordered by score descending.
     * @param limit maximum number of rows to return
     * @return list of Result objects up to the given limit
     */
    public List<Result> getTopResults(int limit) {
        final String sql = "SELECT id, student_id, score, exam_date FROM result ORDER BY score DESC, id ASC LIMIT ?";
        List<Result> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) return results;
            ps.setInt(1, Math.max(0, limit)); // Defensive: avoid negative limits
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRowToResult(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Helper to map current row to Result object
    private Result mapRowToResult(ResultSet rs) throws SQLException {
        Result r = new Result();
        r.setId(rs.getInt("id"));
        r.setStudentId(rs.getInt("student_id"));
        r.setScore(rs.getInt("score"));
        r.setExamDate(rs.getTimestamp("exam_date"));
        return r;
    }
}