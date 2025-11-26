package com.intern.college.dao;

import com.intern.college.DBUtil;
import com.intern.college.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public int insert(Course cobj) throws Exception {
        String sql = "INSERT INTO Courses (course_code,name,seats,cutoff) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cobj.getCourseCode());
            ps.setString(2, cobj.getName());
            ps.setInt(3, cobj.getSeats());
            ps.setDouble(4, cobj.getCutoff() == null ? 0.0 : cobj.getCutoff());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public Course findById(int id) throws Exception {
        String sql = "SELECT * FROM Courses WHERE course_id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setName(rs.getString("name"));
                    course.setSeats(rs.getInt("seats"));
                    course.setCutoff(rs.getDouble("cutoff"));
                    return course;
                }
            }
        }
        return null;
    }

    public List<Course> findAll() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setName(rs.getString("name"));
                course.setSeats(rs.getInt("seats"));
                course.setCutoff(rs.getDouble("cutoff"));
                list.add(course);
            }
        }
        return list;
    }

    public void updateSeatsAndCutoff(int courseId, int seats, double cutoff) throws Exception {
        String sql = "UPDATE Courses SET seats=?, cutoff=? WHERE course_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, seats);
            ps.setDouble(2, cutoff);
            ps.setInt(3, courseId);
            ps.executeUpdate();
        }
    }
}
