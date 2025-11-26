-- schema.sql
CREATE DATABASE IF NOT EXISTS college_admissions;
USE college_admissions;

CREATE TABLE IF NOT EXISTS Students (
  student_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  email VARCHAR(150) UNIQUE NOT NULL,
  phone VARCHAR(20),
  dob DATE,
  highschool_percentage DECIMAL(5,2),
  entrance_score DECIMAL(6,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Courses (
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  course_code VARCHAR(20) UNIQUE NOT NULL,
  name VARCHAR(150) NOT NULL,
  seats INT NOT NULL DEFAULT 0,
  cutoff DECIMAL(6,2) DEFAULT 0.0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Applications (
  application_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  merit_score DECIMAL(6,3) DEFAULT NULL,
  status ENUM('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING',
  admin_notes VARCHAR(500),
  FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);
s