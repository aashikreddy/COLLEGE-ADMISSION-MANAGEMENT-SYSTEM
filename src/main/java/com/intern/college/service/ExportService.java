package com.intern.college.service;

import com.intern.college.dao.ApplicationDAO;
import com.intern.college.dao.CourseDAO;
import com.intern.college.dao.StudentDAO;
import com.intern.college.model.Application;
import com.intern.college.model.Course;
import com.intern.college.model.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.*;
import java.util.List;

public class ExportService {
    private final ApplicationDAO appDao = new ApplicationDAO();
    private final StudentDAO studentDao = new StudentDAO();
    private final CourseDAO courseDao = new CourseDAO();

    public void exportCsvForCourse(int courseId, String filePath) throws Exception {
        Course course = courseDao.findById(courseId);
        List<Application> apps = appDao.findAllForExport(courseId);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ApplicationId","StudentId","StudentName","Email","Merit","Status","Course"))) {
            for (Application a : apps) {
                Student s = studentDao.findById(a.getStudentId());
                String name = s == null ? "" : s.getFirstName() + " " + s.getLastName();
                String email = s == null ? "" : s.getEmail();
                printer.printRecord(a.getApplicationId(), a.getStudentId(), name, email, a.getMeritScore(), a.getStatus(), course == null ? "" : course.getName());
            }
        }
        System.out.println("CSV exported to " + filePath);
    }

    public void exportPdfForCourse(int courseId, String filePath) throws Exception {
        Course course = courseDao.findById(courseId);
        List<Application> apps = appDao.findAllForExport(courseId);

        // Create document and first page
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.LETTER);
        doc.addPage(page);

        // Load a system TTF font (Windows Arial). If the font is not available, fallback to system default.
        PDType0Font font = null;
        try {
            File arial = new File("C:\\Windows\\Fonts\\arial.ttf");
            if (arial.exists()) {
                font = PDType0Font.load(doc, arial);
            } else {
                // try arial unicode as fallback
                File arialUni = new File("C:\\Windows\\Fonts\\ARIALUNI.TTF");
                if (arialUni.exists()) font = PDType0Font.load(doc, arialUni);
            }
        } catch (Exception ex) {
            // ignore, font will remain null and we'll try to proceed
            font = null;
        }

        // If font is still null, create the doc without loading a TTF (use PDFBox default portability)
        if (font == null) {
            System.out.println("Warning: Could not load Windows font (arial.ttf). PDF text appearance may differ.");
        }

        PDPageContentStream cs = new PDPageContentStream(doc, page);
        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float y = yStart;
        float leading = 14f;

        // Title
        cs.beginText();
        if (font != null) cs.setFont(font, 14); else cs.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\arial.ttf")), 14);
        cs.newLineAtOffset(margin, y);
        String title = "Admission List - " + (course == null ? "Course " + courseId : course.getName());
        cs.showText(title);
        cs.endText();
        y -= (leading + 6);

        // Header row
        cs.beginText();
        if (font != null) cs.setFont(font, 10); 
        cs.newLineAtOffset(margin, y);
        cs.showText("AppId | StudentId | Name | Email | Merit | Status");
        cs.endText();
        y -= leading;

        // Rows
        for (Application a : apps) {
            if (y < margin + 50) {
                // close current content stream and add new page
                cs.close();
                page = new PDPage(PDRectangle.LETTER);
                doc.addPage(page);
                cs = new PDPageContentStream(doc, page);
                y = page.getMediaBox().getHeight() - margin;
            }

            Student s = studentDao.findById(a.getStudentId());
            String name = s == null ? "" : (s.getFirstName() + " " + s.getLastName());
            String email = s == null ? "" : s.getEmail();
            String meritStr = a.getMeritScore() == null ? "0.00" : String.format("%.2f", a.getMeritScore());
            String line = String.format("%d | %d | %s | %s | %s | %s",
                    a.getApplicationId(), a.getStudentId(), safeForPdf(name), safeForPdf(email), meritStr, a.getStatus());

            cs.beginText();
            if (font != null) cs.setFont(font, 10);
            cs.newLineAtOffset(margin, y);
            cs.showText(line);
            cs.endText();
            y -= leading;
        }

        // cleanup
        cs.close();
        doc.save(filePath);
        doc.close();
        System.out.println("PDF exported to " + filePath);
    }

    // helper to avoid nulls or illegal characters
    private String safeForPdf(String s) {
        if (s == null) return "";
        return s.replaceAll("\\p{Cc}", ""); // remove control chars
    }
}
