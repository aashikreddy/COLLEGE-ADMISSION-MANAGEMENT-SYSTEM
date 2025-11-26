package com.intern.college.service;

import com.intern.college.DBUtil;
import com.intern.college.dao.ApplicationDAO;
import com.intern.college.dao.StudentDAO;
import com.intern.college.model.Application;
import com.intern.college.model.Student;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

public class MeritService {
    private double wHigh;
    private double wEntrance;
    private double entranceMax;

    public MeritService() {
        try (InputStream in = MeritService.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            p.load(in);
            wHigh = Double.parseDouble(p.getProperty("weight.highschool","0.6"));
            wEntrance = Double.parseDouble(p.getProperty("weight.entrance","0.4"));
            entranceMax = Double.parseDouble(p.getProperty("entrance.max","100"));
        } catch (Exception e) {
            wHigh = 0.6; wEntrance = 0.4; entranceMax = 100;
        }
    }

    // Compute merit for one application and persist
    public void computeMeritAndSave(Application app) throws Exception {
        StudentDAO sdao = new StudentDAO();
        Student s = sdao.findById(app.getStudentId());
        if (s == null) return;
        Double high = s.getHighschoolPercentage();
        Double entrance = s.getEntranceScore();
        double merit;
        if (entrance != null) {
            double entranceNormalized = (entrance / entranceMax) * 100.0;
            merit = ( (high==null?0.0:high) * wHigh ) + (entranceNormalized * wEntrance);
        } else {
            merit = (high == null ? 0.0 : high);
        }
        // normalize to two decimals
        merit = Math.round(merit * 100.0) / 100.0;
        // persist
        ApplicationDAO adao = new ApplicationDAO();
        adao.updateMerit(app.getApplicationId(), merit);
    }

    // compute for all applications of a course (useful before allocation)
    public void computeMeritForCourse(int courseId, List<Application> apps) throws Exception {
        for (Application a : apps) computeMeritAndSave(a);
    }
}
