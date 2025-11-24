package com.esports.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionManager {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();

            InputStream is = ConnectionManager.class
                    .getClassLoader()
                    .getResourceAsStream("config.xml");

            if (is == null) {
                throw new RuntimeException("No s'ha trobat config.xml");
            }

            props.loadFromXML(is);

            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");

        } catch (Exception e) {
            throw new RuntimeException("Error carregant config.xml", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
