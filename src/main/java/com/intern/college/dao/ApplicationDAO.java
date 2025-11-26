package com.intern.college.dao;

import com.intern.college.DBUtil;
import com.intern.college.model.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
    public int insert(Application app) throws Exception {
        String sql = "INSERT INTO Applications (student_id, course_id, merit_score, status, admin_notes) VALUES (?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, app.getStudentId());
            ps.setInt(2, app.getCourseId());
            if (app.getMeritScore() != null) ps.setDouble(3, app.getMeritScore()); else ps.setNull(3, Types.DOUBLE);
            ps.setString(4, app.getStatus() == null ? "PENDING" : app.getStatus());
            ps.setString(5, app.getAdminNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void updateMerit(int applicationId, double merit) throws Exception {
        String sql = "UPDATE Applications SET merit_score=? WHERE application_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, merit);
            ps.setInt(2, applicationId);
            ps.executeUpdate();
        }
    }

    public List<Application> findByCourseOrderByMeritDesc(int courseId) throws Exception {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE course_id=? ORDER BY merit_score DESC, applied_on ASC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Application a = new Application();
                    a.setApplicationId(rs.getInt("application_id"));
                    a.setStudentId(rs.getInt("student_id"));
                    a.setCourseId(rs.getInt("course_id"));
                    a.setMeritScore(rs.getDouble("merit_score"));
                    a.setStatus(rs.getString("status"));
                    a.setAdminNotes(rs.getString("admin_notes"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    public void updateStatus(int applicationId, String status, String notes) throws Exception {
        String sql = "UPDATE Applications SET status=?, admin_notes=? WHERE application_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, notes);
            ps.setInt(3, applicationId);
            ps.executeUpdate();
        }
    }

    public List<Application> findAllForExport(int courseId) throws Exception {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE course_id=? ORDER BY merit_score DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Application a = new Application();
                    a.setApplicationId(rs.getInt("application_id"));
                    a.setStudentId(rs.getInt("student_id"));
                    a.setCourseId(rs.getInt("course_id"));
                    a.setMeritScore(rs.getDouble("merit_score"));
                    a.setStatus(rs.getString("status"));
                    a.setAdminNotes(rs.getString("admin_notes"));
                    list.add(a);
                }
            }
        }
        return list;
    }
}
