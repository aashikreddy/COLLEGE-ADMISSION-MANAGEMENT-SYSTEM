package com.intern.college.service;

import com.intern.college.dao.ApplicationDAO;
import com.intern.college.dao.CourseDAO;
import com.intern.college.model.Application;
import com.intern.college.model.Course;

import java.util.List;

public class AdminService {
    private final ApplicationDAO appDao = new ApplicationDAO();
    private final CourseDAO courseDao = new CourseDAO();

    // Auto allocate seats based on merit and cutoff
    public void autoAllocate(int courseId) throws Exception {
        Course course = courseDao.findById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        int seats = course.getSeats() == null ? 0 : course.getSeats();
        double cutoff = course.getCutoff() == null ? 0.0 : course.getCutoff();
        List<Application> apps = appDao.findByCourseOrderByMeritDesc(courseId);

        int seatsLeft = seats;
        for (Application a : apps) {
            Double merit = a.getMeritScore();
            if (merit == null) {
                appDao.updateStatus(a.getApplicationId(), "REJECTED", "No merit calculated");
            } else if (merit >= cutoff && seatsLeft > 0) {
                appDao.updateStatus(a.getApplicationId(), "ACCEPTED", "Auto-allocated");
                seatsLeft--;
            } else {
                appDao.updateStatus(a.getApplicationId(), "REJECTED", "Cutoff/Seats not available");
            }
        }
        // update seats left in the courses table
        courseDao.updateSeatsAndCutoff(courseId, seatsLeft, cutoff);
        System.out.println("Auto-allocation complete. Seats remaining: " + seatsLeft);
    }
}
