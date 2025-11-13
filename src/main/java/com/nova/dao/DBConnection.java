package com.nova.dao; // Declares the package for data-access-related classes

import java.sql.Connection; // JDBC interface representing a connection to the database
import java.sql.DriverManager; // Class that creates connections to the database
import java.sql.SQLException; // Exception type for SQL/database errors

/**
 * DBConnection provides a single place to obtain JDBC connections
 * to the MySQL database used by Nova Online Exam System.
 * This file includes beginner-friendly comments explaining each line.
 */
public class DBConnection { // Defines a public utility class for DB connections

    // -------------------- Configuration (adjust as needed) --------------------
    private static final String URL = "jdbc:mysql://localhost:3306/nova_exam"; // JDBC URL including host, port, and database name
    private static final String USERNAME = "root"; // TODO: replace with your MySQL username
    private static final String PASSWORD = "8238366756@Milan"; // TODO: replace with your MySQL password (never hard-code secrets in production)

    /**
     * Returns a live JDBC Connection to the MySQL database.
     * This method loads the MySQL driver class and then asks DriverManager
     * to open a connection using the configured URL, username, and password.
     *
     * @return a Connection if successful; null if an error occurs
     */
    public static Connection getConnection() { // Static method so you can call DBConnection.getConnection()
        try {
            // Load the MySQL JDBC driver class.
            // Modern drivers auto-register, but this call helps beginners understand the step
            // and avoids issues in some environments.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ask the DriverManager to create a new connection using our credentials.
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) { // Thrown if the driver class isn't found on the classpath
            System.err.println("MySQL JDBC Driver not found. Ensure mysql-connector-j is on the classpath.");
            e.printStackTrace(); // Print the detailed error for debugging
        } catch (SQLException e) { // Thrown for database connection issues
            System.err.println("Failed to connect to database: " + URL);
            e.printStackTrace(); // Print the detailed SQL error information
        }

        // If we reach here, something went wrong; return null to indicate failure.
        return null;
    }
}


