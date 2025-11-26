package com.intern.college;

import com.intern.college.dao.ApplicationDAO;
import com.intern.college.dao.CourseDAO;
import com.intern.college.dao.StudentDAO;
import com.intern.college.model.Application;
import com.intern.college.model.Course;
import com.intern.college.model.Student;
import com.intern.college.service.AdminService;
import com.intern.college.service.ExportService;
import com.intern.college.service.MeritService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO studentDao = new StudentDAO();
    private static final CourseDAO courseDao = new CourseDAO();
    private static final ApplicationDAO applicationDao = new ApplicationDAO();
    private static final MeritService meritService = new MeritService();
    private static final AdminService adminService = new AdminService();
    private static final ExportService exportService = new ExportService();

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n--- College Admission Management ---");
            System.out.println("1. Create Course");
            System.out.println("2. List Courses");
            System.out.println("3. Register Student");
            System.out.println("4. Apply to Course");
            System.out.println("5. Calculate Merits for Course");
            System.out.println("6. Auto-allocate (Admin)");
            System.out.println("7. Export CSV for Course");
            System.out.println("8. Export PDF for Course");
            System.out.println("9. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1 -> createCourse();
                case 2 -> listCourses();
                case 3 -> registerStudent();
                case 4 -> applyToCourse();
                case 5 -> calcMerits();
                case 6 -> autoAllocate();
                case 7 -> exportCsv();
                case 8 -> exportPdf();
                case 9 -> { System.out.println("Bye"); return; }
                default -> System.out.println("Invalid");
            }
        }
    }

    private static void createCourse() throws Exception {
        Course c = new Course();
        System.out.print("Course Code: "); c.setCourseCode(sc.nextLine());
        System.out.print("Name: "); c.setName(sc.nextLine());
        System.out.print("Seats: "); c.setSeats(Integer.parseInt(sc.nextLine()));
        System.out.print("Cutoff: "); c.setCutoff(Double.parseDouble(sc.nextLine()));
        int id = courseDao.insert(c);
        System.out.println("Created course id: " + id);
    }

    private static void listCourses() throws Exception {
        List<Course> list = courseDao.findAll();
        System.out.println("Courses:");
        for (Course c : list) {
            System.out.printf("%d: %s - %s (seats=%d cutoff=%.2f)\n", c.getCourseId(), c.getCourseCode(), c.getName(), c.getSeats(), c.getCutoff());
        }
    }

    private static void registerStudent() throws Exception {
        Student s = new Student();
        System.out.print("First name: "); s.setFirstName(sc.nextLine());
        System.out.print("Last name: "); s.setLastName(sc.nextLine());
        System.out.print("Email: "); s.setEmail(sc.nextLine());
        System.out.print("Phone: "); s.setPhone(sc.nextLine());
        System.out.print("DOB (yyyy-mm-dd) or blank: ");
        String d = sc.nextLine();
        if (!d.isBlank()) s.setDob(LocalDate.parse(d));
        System.out.print("Highschool % (0-100) or blank: ");
        String h = sc.nextLine();
        if (!h.isBlank()) s.setHighschoolPercentage(Double.parseDouble(h));
        System.out.print("Entrance score or blank: ");
        String e = sc.nextLine();
        if (!e.isBlank()) s.setEntranceScore(Double.parseDouble(e));
        int id = studentDao.insert(s);
        System.out.println("Student created with id: " + id);
    }

    private static void applyToCourse() throws Exception {
        System.out.print("StudentId: "); int sid = Integer.parseInt(sc.nextLine());
        System.out.print("CourseId: "); int cid = Integer.parseInt(sc.nextLine());
        Application a = new Application();
        a.setStudentId(sid);
        a.setCourseId(cid);
        a.setStatus("PENDING");
        int aid = applicationDao.insert(a);
        System.out.println("Application submitted id: " + aid);
    }

    private static void calcMerits() throws Exception {
        System.out.print("CourseId to compute merits: "); int cid = Integer.parseInt(sc.nextLine());
        List<Application> apps = applicationDao.findByCourseOrderByMeritDesc(cid);
        meritService.computeMeritForCourse(cid, apps);
        System.out.println("Merits updated for course " + cid);
    }

    private static void autoAllocate() throws Exception {
        System.out.print("CourseId to auto-allocate: "); int cid = Integer.parseInt(sc.nextLine());
        adminService.autoAllocate(cid);
    }

    private static void exportCsv() throws Exception {
        System.out.print("CourseId to export CSV: "); int cid = Integer.parseInt(sc.nextLine());
        System.out.print("Output CSV file path (e.g., admission_list.csv): "); String path = sc.nextLine();
        exportService.exportCsvForCourse(cid, path);
    }

    private static void exportPdf() throws Exception {
        System.out.print("CourseId to export PDF: "); int cid = Integer.parseInt(sc.nextLine());
        System.out.print("Output PDF file path (e.g., admission_list.pdf): "); String path = sc.nextLine();
        exportService.exportPdfForCourse(cid, path);
    }
}
