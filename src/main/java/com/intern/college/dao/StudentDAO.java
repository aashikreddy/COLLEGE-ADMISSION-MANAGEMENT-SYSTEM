package com.intern.college.dao;

import com.intern.college.DBUtil;
import com.intern.college.model.Student;

import java.sql.*;
import java.time.LocalDate;

public class StudentDAO {
    public int insert(Student s) throws Exception {
        String sql = "INSERT INTO Students (first_name,last_name,email,phone,dob,highschool_percentage,entrance_score) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            if (s.getDob() != null) ps.setDate(5, Date.valueOf(s.getDob())); else ps.setNull(5, Types.DATE);
            if (s.getHighschoolPercentage() != null) ps.setDouble(6, s.getHighschoolPercentage()); else ps.setNull(6, Types.DOUBLE);
            if (s.getEntranceScore() != null) ps.setDouble(7, s.getEntranceScore()); else ps.setNull(7, Types.DOUBLE);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public Student findById(int id) throws Exception {
        String sql = "SELECT * FROM Students WHERE student_id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    Date d = rs.getDate("dob");
                    if (d != null) s.setDob(d.toLocalDate());
                    s.setHighschoolPercentage(rs.getDouble("highschool_percentage"));
                    s.setEntranceScore(rs.getDouble("entrance_score"));
                    return s;
                }
            }
        }
        return null;
    }
}
