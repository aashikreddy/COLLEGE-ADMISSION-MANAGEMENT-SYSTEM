# System Architecture – College Admission Management System

## 📌 Overview

The College Admission Management System is a **Java-based console application** backed by **MySQL** and structured using a layered architecture.  
The design ensures clean separation of concerns, scalability, and easy maintainability.

---

# 🏗 Architecture Diagram (Conceptual)

```
                +--------------------------+
                |        Application       |
                |       (User Console)     |
                +------------+-------------+
                             |
                             v
                +--------------------------+
                |        Service Layer     |
                |  (Business Logic, Merit  |
                |    Calculation, Export)  |
                +------------+-------------+
                             |
                             v
                +--------------------------+
                |      DAO Layer (JDBC)    |
                | Database Queries & CRUD  |
                +------------+-------------+
                             |
                             v
                +--------------------------+
                |     MySQL Database       |
                |  Courses, Students, Apps |
                +--------------------------+
```

---

# 🧱 Architecture Components

## 1️⃣ **Presentation Layer (Console UI)**

File: `Main.java`  
Responsibilities:

- Display menu
- Take user input
- Call service layer methods
- Show results/errors

This layer contains **no business logic**.

---

## 2️⃣ **Service Layer (Business Logic)**

Package: `service/`

Includes:

- `CourseService`
- `StudentService`
- `ApplicationService`
- `MeritService`
- `ExportService`

Responsibilities:

- Validate inputs
- Calculate merit
- Auto-allocate seats
- Handle cut-offs
- Convert data into CSV/PDF
- Delegate database actions to DAO layer

---

## 3️⃣ **DAO Layer (Data Access via JDBC)**

Package: `dao/`

Files include:

- `CourseDAO`
- `StudentDAO`
- `ApplicationDAO`
- `DBConnection`

Responsibilities:

- SQL queries
- Insert / Update / Select operations
- Connection management
- Mapping result sets to model objects

Uses **PreparedStatement** for safety.

---

## 4️⃣ **Model Layer**

Package: `model/`

Entities:

- `Course`
- `Student`
- `Application`

These classes represent table structures and help in object mapping.

---

## 5️⃣ **Database Layer (MySQL)**

Tables created from **schema.sql**:

- `courses`
- `students`
- `applications`

Responsible for:

- Data integrity
- Foreign key linking
- Fast queries for merit sorting & selection

---

# 📦 Maven Structure

```
college-admissions/
│
├── src/main/java/com/intern/college/
│   ├── model/
│   ├── dao/
│   ├── service/
│   └── Main.java
│
├── src/main/resources/
│   └── application.properties
│
├── outputs/
│   ├── admission_list.csv
│   └── admission_list.pdf
│
├── pom.xml
├── schema.sql
└── README.md
```

---

# 🔗 Workflow of the System

### 1. User chooses an action

↓

### 2. Service layer validates and processes

↓

### 3. DAO layer performs DB operations

↓

### 4. Results returned to UI

↓

### 5. Optionally generate CSV/PDF

---

# 📊 Data Flow Example – Merit Allocation

```
Student → Apply → ApplicationDAO → MeritService → ApplicationDAO.update()
```

---

# ⚙️ Design Principles Used

- **Separation of Concerns**
- **Layered Architecture**
- **Single Responsibility Principle**
- **Reusability of DB connection**
- **Clean OOP modeling**
- **Modularity for future GUI/Web upgrade**

---

# 🚀 Scalability Notes

This architecture allows easy upgrade to:

- Spring Boot
- Web UI / React
- REST APIs
- Cloud deployment

---

# 🧑‍💻 Author

**Aashik Reddy**
