package com.intern.college.model;

import java.time.LocalDateTime;

public class Application {
    private Integer applicationId;
    private Integer studentId;
    private Integer courseId;
    private LocalDateTime appliedOn;
    private Double meritScore;
    private String status;
    private String adminNotes;

    public Application(){}

    // getters/setters
    public Integer getApplicationId(){return applicationId;}
    public void setApplicationId(Integer id){this.applicationId=id;}
    public Integer getStudentId(){return studentId;}
    public void setStudentId(Integer s){this.studentId=s;}
    public Integer getCourseId(){return courseId;}
    public void setCourseId(Integer c){this.courseId=c;}
    public LocalDateTime getAppliedOn(){return appliedOn;}
    public void setAppliedOn(LocalDateTime a){this.appliedOn=a;}
    public Double getMeritScore(){return meritScore;}
    public void setMeritScore(Double m){this.meritScore=m;}
    public String getStatus(){return status;}
    public void setStatus(String s){this.status=s;}
    public String getAdminNotes(){return adminNotes;}
    public void setAdminNotes(String n){this.adminNotes=n;}
}
