package com.nova.model;

/**
 * Question model represents a multiple-choice question with four options
 * and a single correct option (A/B/C/D).
 */
public class Question {

    private int id; // Unique identifier for the question
    private String questionText; // The text of the question
    private String optionA; // Option A text
    private String optionB; // Option B text
    private String optionC; // Option C text
    private String optionD; // Option D text
    private String correctOption; // The correct option label: "A", "B", "C", or "D"

    /** No-argument constructor. */
    public Question() {
    }

    /**
     * All-arguments constructor to initialize all fields.
     */
    public Question(int id, String questionText, String optionA, String optionB,
                    String optionC, String optionD, String correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    /**
     * Returns a nicely formatted string showing the question and its options.
     */
    public String displayQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q: ").append(questionText).append('\n');
        sb.append("A) ").append(optionA).append('\n');
        sb.append("B) ").append(optionB).append('\n');
        sb.append("C) ").append(optionC).append('\n');
        sb.append("D) ").append(optionD);
        return sb.toString();
    }
}


