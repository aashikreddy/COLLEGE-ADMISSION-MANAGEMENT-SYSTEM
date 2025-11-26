package com.intern.college;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            p.load(in);
            URL = p.getProperty("db.url");
            USER = p.getProperty("db.user");
            PASS = p.getProperty("db.pass");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
