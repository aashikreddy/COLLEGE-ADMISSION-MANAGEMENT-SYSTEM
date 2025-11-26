package com.intern.college.model;

public class Course {
    private Integer courseId;
    private String courseCode;
    private String name;
    private Integer seats;
    private Double cutoff;

    public Course() {}

    // getters/setters
    public Integer getCourseId(){return courseId;}
    public void setCourseId(Integer id){this.courseId=id;}
    public String getCourseCode(){return courseCode;}
    public void setCourseCode(String c){this.courseCode=c;}
    public String getName(){return name;}
    public void setName(String n){this.name=n;}
    public Integer getSeats(){return seats;}
    public void setSeats(Integer s){this.seats=s;}
    public Double getCutoff(){return cutoff;}
    public void setCutoff(Double c){this.cutoff=c;}
}
