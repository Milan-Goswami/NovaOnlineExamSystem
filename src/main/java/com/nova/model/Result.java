package com.nova.model;

/**
 * Result model captures the outcome of a student's exam attempt.
 */
public class Result {

    private int id; // Unique identifier for the result record
    private int studentId; // Foreign key to Student.id
    private int score; // Numeric score for the attempt

    /** No-argument constructor. */
    public Result() {
    }

    /** All-arguments constructor. */
    public Result(int id, int studentId, int score) {
        this.id = id;
        this.studentId = studentId;
        this.score = score;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", score=" + score +
                '}';
    }
}


