package com.nova.model; // Declares the package this class belongs to

/**
 * Student model represents a student in the Nova Online Exam System.
 * This version includes beginner-friendly comments explaining each line.
 */
public class Student { // Defines a public class named Student

    // Private fields to encapsulate the student's data
    private int id; // Unique identifier for the student (matches DB column `student.id`)
    private String name; // Student's full name
    private String email; // Student's email address (should be unique)
    private String password; // Student's password (store hashed in production)

    /**
     * No-argument constructor.
     * Creates an empty Student object so frameworks/tools can instantiate it.
     */
    public Student() { // Constructor without parameters
        // No initialization required here; fields get default values
    }

    /**
     * All-arguments constructor.
     * Quickly creates a fully-initialized Student instance.
     *
     * @param id        unique student ID
     * @param name      student's full name
     * @param email     student's email
     * @param password  student's password (hash recommended)
     */
    public Student(int id, String name, String email, String password) { // Constructor with all fields
        this.id = id; // Assigns constructor parameter to the field `id`
        this.name = name; // Assigns constructor parameter to the field `name`
        this.email = email; // Assigns constructor parameter to the field `email`
        this.password = password; // Assigns constructor parameter to the field `password`
    }

    // -------------------- Getters and Setters (Encapsulation) --------------------

    /**
     * Gets the student's unique ID.
     * @return id value
     */
    public int getId() { // Getter for id
        return id; // Returns the value of the field `id`
    }

    /**
     * Sets the student's unique ID.
     * @param id new identifier
     */
    public void setId(int id) { // Setter for id
        this.id = id; // Updates the field `id` with the provided value
    }

    /**
     * Gets the student's name.
     * @return name value
     */
    public String getName() { // Getter for name
        return name; // Returns the value of the field `name`
    }

    /**
     * Sets the student's name.
     * @param name new name
     */
    public void setName(String name) { // Setter for name
        this.name = name; // Updates the field `name`
    }

    /**
     * Gets the student's email.
     * @return email value
     */
    public String getEmail() { // Getter for email
        return email; // Returns the value of the field `email`
    }

    /**
     * Sets the student's email.
     * @param email new email
     */
    public void setEmail(String email) { // Setter for email
        this.email = email; // Updates the field `email`
    }

    /**
     * Gets the student's password.
     * Note: in a real system, avoid returning raw passwords.
     * @return password value
     */
    public String getPassword() { // Getter for password
        return password; // Returns the value of the field `password`
    }

    /**
     * Sets the student's password.
     * Note: in production, store a secure hash instead of plain text.
     * @param password new password
     */
    public void setPassword(String password) { // Setter for password
        this.password = password; // Updates the field `password`
    }

    /**
     * Returns a readable string with key student information.
     * Avoids printing sensitive data like the full password.
     */
    @Override // Indicates we are overriding the method from Object
    public String toString() { // Defines how to convert the object to text
        return "Student{" + // Begins the string with the class name
                "id=" + id + // Appends the id field
                ", name='" + name + '\'' + // Appends the name field
                ", email='" + email + '\'' + // Appends the email field
                ", password='***'" + // Masks the password for safety
                '}'; // Closes the string representation
    }
}


