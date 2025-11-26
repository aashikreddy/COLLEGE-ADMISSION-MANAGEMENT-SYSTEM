# User Guide – College Admission Management System

## 📌 Overview

This system allows colleges to manage courses, student registrations, applications, merit calculation, admission approvals, and exporting lists in CSV/PDF formats.

It is a **Java + MySQL + JDBC** console-based project.

---

## 🚀 How to Run the Application

### **1. Requirements**

- Java 17+
- MySQL 8+
- Maven

### **2. Set up the Database**

1. Open MySQL terminal:
   ```
   mysql -u root -p
   ```
2. Run the schema file:
   ```
   SOURCE path/to/schema.sql;
   ```

This will create:

- `students`
- `courses`
- `applications`

---

## ▶️ Running the Application

Inside your project folder, run:

```
java -jar target/college-admissions-1.0-SNAPSHOT.jar
```

---

# 📘 FEATURES

## 1️⃣ Create Course

Add course name, cutoff %, and max seats.

## 2️⃣ List Courses

Displays all courses.

## 3️⃣ Register Student

Collects name, email, 10th %, 12th %.

## 4️⃣ Apply to Course

Student applies for a course.

## 5️⃣ Calculate Merit

Formula:

```
merit = (0.4 * tenthMarks) + (0.6 * twelfthMarks)
```

## 6️⃣ Auto Allocate Seats

Allocates seats based on merit & cutoff.

## 7️⃣ Export CSV

Generates `outputs/admission_list.csv`.

## 8️⃣ Export PDF

Generates `outputs/admission_list.pdf`.

---

# 🛠 Troubleshooting

- Ensure schema is executed.
- Ensure jar exists in `target/`.
- Run `mvn clean package` if dependencies fail.

---

# 🧑‍💻 Author

**Aashik Reddy**
