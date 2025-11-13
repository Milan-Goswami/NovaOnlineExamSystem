package com.nova.model; // Declares the package for teacher-related models

/**
 * Teacher model represents a teacher account that can manage questions.
 * Every field maps directly to a column in the `teacher` database table.
 * Lots of comments are included to help beginners follow along.
 */
public class Teacher {

    // -------------------- Fields (a.k.a. properties) --------------------
    private int id;          // Unique identifier for the teacher (AUTO_INCREMENT in DB)
    private String name;     // Teacher's display name
    private String email;    // Teacher's login email (must be unique)
    private String password; // Teacher's password (store as plain text only for demo/tests)

    /**
     * No-argument constructor.
     * Frameworks, ORMs, and JSPs often need this to create empty objects.
     */
    public Teacher() {
        // Empty on purpose: default constructor just exists for convenience
    }

    /**
     * All-arguments constructor for quickly setting every field at once.
     *
     * @param id        database ID
     * @param name      teacher's full name
     * @param email     teacher's login email
     * @param password  teacher's password
     */
    public Teacher(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Convenience constructor without the ID.
     * Useful before the record is inserted and the ID is generated.
     *
     * @param name      teacher's full name
     * @param email     teacher's login email
     * @param password  teacher's password
     */
    public Teacher(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // -------------------- Getters and Setters --------------------

    /** @return current value of id */
    public int getId() {
        return id;
    }

    /** @param id sets the unique identifier */
    public void setId(int id) {
        this.id = id;
    }

    /** @return current value of name */
    public String getName() {
        return name;
    }

    /** @param name sets the teacher's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return current value of email */
    public String getEmail() {
        return email;
    }

    /** @param email sets the teacher's email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return current value of password */
    public String getPassword() {
        return password;
    }

    /** @param password sets the teacher's password */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * toString helps when logging or debugging.
     * Avoid printing raw passwords outside of demos.
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='***'" +
                '}';
    }
}

