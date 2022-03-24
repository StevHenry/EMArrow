package com.emarrow.uecepi.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class DatabaseConnector {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);
    private static DatabaseConnector instance;
    private final Connection connection;

    protected DatabaseConnector() {
        connection = connect();
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection connect() {
        try {
            Map<String, String> properties = loadConfiguration();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://%s:%s/emadata".formatted(properties.get("ip"), properties.get("port")),
                    properties.get("identifier"),
                    properties.get("password"));
            LOGGER.info("CONNECTED");
            return connection;
        } catch (IOException exception) {
            LOGGER.warn("Could not load configuration! (Cause {})", exception.getMessage());
        } catch (SQLException exception) {
            LOGGER.warn("Could not connect to the database!");
            LOGGER.warn(exception.getMessage());
        }
        return null;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(0);
        } catch (SQLException exception) {
            LOGGER.debug("Error while checking connection status: %s".formatted(exception.getMessage()));
            return false;
        }
    }

    private Map<String, String> loadConfiguration() throws IOException {
        Map<String, String> properties = new HashMap<>();
        Properties dbProperties = new Properties();

        dbProperties.load(new FileInputStream("../properties/credentials.properties"));
        for (String key : new String[]{"ip", "port", "identifier", "password"}) {
            properties.put(key, dbProperties.getProperty(key));
        }
        return properties;
    }

    public void createAccount(String identifier, String pass) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO accounts VALUES(?,?,?,?,?)");
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, identifier);

        } catch (SQLException exception) {
            LOGGER.warn("Could not create an account! (Cause = {})", exception.getMessage());
        }
    }

    /**
     * @param pass : password to hash
     * @return an array of String containing the hashed password and the salt
     */
    public String[] hashPassword(String pass) {
        try {
           /* SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            md.update(salt);*/
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            return new String[]{new String(hashedPassword)};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
