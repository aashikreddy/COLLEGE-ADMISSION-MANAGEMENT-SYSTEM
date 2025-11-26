# College Admission Management System (Java + MySQL + Maven)

A console-based application to manage courses, students, applications, merit lists, and exporting results to CSV and PDF.
This project demonstrates Java, JDBC, OOP, database design, and Maven build automation.

## 🚀 Features

- Create and list courses
- Register students
- Students can apply for courses
- Merit list calculation by marks
- Auto-allocation (Accepted / Rejected)
- Export admission list in CSV format
- Export admission list in PDF format
- Fully interactive console UI

## 🛠️ Tech Stack

- Java 17
- MySQL
- Maven
- PDFBox (PDF export)
- Apache Commons CSV (CSV export)

## Database Setup

Run the following SQL:

```sql
CREATE DATABASE college;

USE college;

CREATE TABLE courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  seats INT NOT NULL
);

CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255),
  marks DOUBLE
);

CREATE TABLE applications (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  course_id INT,
  status VARCHAR(20),
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

## Build Instructions

### Prerequisites

- Java 17
- Maven installed
- MySQL running

### Build JAR

```
mvn clean package
```

### Run Application

```
java -jar target/college-admissions-1.0-SNAPSHOT.jar
```

## 📂 Project Structure

```
college-admissions/
│
├── pom.xml
├── schema.sql
├── README.md
│
├── src/main/java/com/intern/college/
│   ├── db/
│   ├── model/
│   ├── service/
│   ├── util/
│   └── Main.java
│
└── target/
    ├── college-admissions-1.0-SNAPSHOT.jar
```

## ✨ Author

**Aashik Reddy**

📧 [Contact Me](aashikreddythatiparthi03@gmail.com)   
🐙 [GitHub](https://github.com/aashikreddy)
