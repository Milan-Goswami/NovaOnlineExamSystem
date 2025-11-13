-- =====================================================================
-- Nova Online Examination System - Initial MySQL Schema
-- File: db_init.sql
-- Description:
--   Creates database `nova_exam` and core tables:
--     * student(id, name, email, password)
--     * question(id, question_text, option_a, option_b, option_c, option_d, correct_option)
--     * result(id, student_id, score)
--   Adds FK: result.student_id -> student.id
--   Uses InnoDB and utf8mb4 for full Unicode support
-- Requirements: MySQL 8.0+
-- Usage: run `mysql -u root -p < db_init.sql` from a terminal on Windows.
-- =====================================================================

-- Create database (if not exists) with utf8mb4 collation
CREATE DATABASE IF NOT EXISTS `nova_exam`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

-- Switch to the database
USE `nova_exam`;

-- Optional: set strict SQL mode for better data integrity
SET SESSION sql_mode = CONCAT(@@SESSION.sql_mode, ',STRICT_TRANS_TABLES');

-- ---------------------------------------------------------------------
-- Table: student
-- Stores basic student information and login credentials
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `student` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- Table: question
-- Stores exam questions with four options and a single correct option
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `question` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_text` TEXT NOT NULL,
  `option_a` VARCHAR(255) NOT NULL,
  `option_b` VARCHAR(255) NOT NULL,
  `option_c` VARCHAR(255) NOT NULL,
  `option_d` VARCHAR(255) NOT NULL,
  `correct_option` ENUM('A','B','C','D') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- Table: result
-- Stores the outcome of a student's exam attempt
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `result` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` INT UNSIGNED NOT NULL,
  `score` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_result_student_id` (`student_id`),
  CONSTRAINT `fk_result_student`
    FOREIGN KEY (`student_id`) REFERENCES `student`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- Table: teacher
-- Stores teacher accounts used to manage exam questions
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `teacher` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teacher_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- Sample sanity data (optional) - comment out if not needed
-- ---------------------------------------------------------------------
-- INSERT INTO `student` (`name`, `email`, `password`) VALUES
--   ('Alice Nova', 'alice@example.com', '$2y$12$REPLACE_WITH_HASH'),
--   ('Bob Orion', 'bob@example.com', '$2y$12$REPLACE_WITH_HASH');
-- 
-- INSERT INTO `question` (`question_text`, `option_a`, `option_b`, `option_c`, `option_d`, `correct_option`) VALUES
--   ('What is 2 + 2?', '3', '4', '5', '6', 'B');
-- 
-- INSERT INTO `result` (`student_id`, `score`) VALUES
--   (1, 10);
--
-- INSERT INTO `teacher` (`name`, `email`, `password`) VALUES
--   ('Teacher One', 'teacher1@example.com', '12345');

-- Insert or update a default teacher login for quick testing
INSERT INTO `teacher` (`name`, `email`, `password`)
VALUES ('Teacher One', 'teacher1@example.com', '12345')
ON DUPLICATE KEY UPDATE
  `name` = VALUES(`name`),
  `password` = VALUES(`password`);

-- End of schema


