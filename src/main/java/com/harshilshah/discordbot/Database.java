package com.harshilshah.discordbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {

    public static Connection getConnection() throws Exception {

        String rawUrl = System.getenv("DATABASE_URL");

        if (rawUrl == null) {
            throw new RuntimeException("DATABASE_URL not set");
        }

        String withoutProtocol = rawUrl.replace("postgresql://", "");

        String[] parts = withoutProtocol.split("@");
        String[] userPass = parts[0].split(":");
        String hostPart = parts[1];

        String user = userPass[0];
        String pass = userPass[1];

        String jdbcUrl = "jdbc:postgresql://" + hostPart;

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        props.setProperty("sslmode", "require");
        props.setProperty("prepareThreshold", "0");

        return DriverManager.getConnection(jdbcUrl, props);
    }
}
