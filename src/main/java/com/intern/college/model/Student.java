package com.intern.college.model;

import java.time.LocalDate;

public class Student {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private Double highschoolPercentage;
    private Double entranceScore;

    // constructors, getters, setters
    public Student() {}
    // getters and setters omitted for brevity - implement all
    // ... (generate using your IDE)
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getFirstName(){return firstName;}
    public void setFirstName(String fn){this.firstName=fn;}
    public String getLastName(){return lastName;}
    public void setLastName(String ln){this.lastName=ln;}
    public String getEmail(){return email;}
    public void setEmail(String e){this.email=e;}
    public String getPhone(){return phone;}
    public void setPhone(String p){this.phone=p;}
    public LocalDate getDob(){return dob;}
    public void setDob(LocalDate d){this.dob=d;}
    public Double getHighschoolPercentage(){return highschoolPercentage;}
    public void setHighschoolPercentage(Double v){this.highschoolPercentage=v;}
    public Double getEntranceScore(){return entranceScore;}
    public void setEntranceScore(Double v){this.entranceScore=v;}
}
