package com.emarrow.uecepi.accountserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class DatabaseConnector {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);
    private static DatabaseConnector instance;
    private Connection connection;

    protected DatabaseConnector() {
    }

    /**
     * @return the instance of DatabaseConnector used previously, or creates a new one
     */
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    /**
     * Tries to connect to the database using the configuration
     * Sets the {@link #connection} variable
     */
    public void connect() {
        try {
            Map<String, String> properties = loadConfiguration();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://%s:%s/%s".formatted(properties.get("ip"), properties.get("port"),
                            properties.get("database_name")), properties.get("identifier"), properties.get("password"));
            this.connection = connection;
            LOGGER.info("Connection to database established");
        } catch (IOException exception) {
            LOGGER.warn("Could not load configuration! (Cause {})", exception.getMessage());
        } catch (SQLException exception) {
            LOGGER.warn("Could not connect to the database!");
            LOGGER.warn(exception.getMessage());
        }
    }

    /**
     * @return whether the connection is up or not
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(0);
        } catch (SQLException exception) {
            LOGGER.debug("Error while checking connection status: %s".formatted(exception.getMessage()));
            return false;
        }
    }

    /**
     * Loads the database connection info (host, port, database_name, credentials)
     *
     * @return a {@link Map} with keys paired to their values using the configuration file called credentials.properties
     * @throws IOException when the file is not found
     */
    private Map<String, String> loadConfiguration() throws IOException {
        Map<String, String> properties = new HashMap<>();
        Properties dbProperties = new Properties();

        dbProperties.load(new FileInputStream("../properties/credentials.properties"));
        for (String key : new String[]{"ip", "port", "identifier", "password", "database_name"}) {
            properties.put(key, dbProperties.getProperty(key));
        }
        return properties;
    }

    /**
     * Creates an account on the database without checks
     * Checks have to be before this method is called
     * An entry is added to the database by generating the hash and salt
     *
     * @param identifier client identifier
     * @param pass       password used by the client
     */
    public boolean createAccount(String identifier, String pass, String nickname) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO accounts VALUES(?,?,?,?,?)");
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, identifier);

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            stmt.setString(3, hashPassword(pass, salt));
            stmt.setString(4, nickname);
            stmt.setBytes(5, salt);
            stmt.execute();
        } catch (SQLException exception) {
            LOGGER.warn("Could not create an account! (Cause = {})", exception.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Deletes an account on the database with checks
     *
     * @param identifier client identifier
     */
    public boolean deleteAccount(String identifier, String pass) {
        try {
            if (attemptLogIn(identifier, pass)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM accounts WHERE identifier = ?");
                stmt.setString(1, identifier);
                stmt.execute();
            } else {
                LOGGER.warn("Could not delete the account!");
                return false;
            }
        } catch (SQLException exception) {
            LOGGER.warn("Could not delete an account! (Cause = {})", exception.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @param pass password to hash
     * @param salt salt used to hash this password
     * @return a String containing the hashed password
     */
    private String hashPassword(String pass, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(pass.getBytes());
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes to convert into Hex String
     * @return a string of an hex value
     */
    private String bytesToHex(byte[] bytes) {
        return String.format("%x", new BigInteger(1, bytes)).toUpperCase();
    }

    /**
     * Verifies the provided password with the real one
     *
     * @param identifier player's identifier
     * @param pass       player's password
     * @return whether the connection is successful or not
     */
    public boolean attemptLogIn(String identifier, String pass) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT pass, salt FROM accounts WHERE identifier=?");
            stmt.setString(1, identifier);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                String hashedPass = result.getString("pass");
                byte[] salt = result.getBytes("salt");
                String currentHashedPassword = hashPassword(pass, salt);
                return hashedPass.equals(currentHashedPassword);
            }
            stmt.close();
        } catch (SQLException exception) {
            LOGGER.warn("Could not verify credentials! (Cause = {})", exception.getMessage());
        }
        return false;
    }

    /**
     * @param identifier player account identifier
     * @return a String array containing [uuid, nickname]
     */
    public String[] getPlayerData(String identifier){
        String[] data = {"00000000-0000-0000-0000-00000", ""};
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT uuid, nickname FROM accounts WHERE identifier=?");
            stmt.setString(1, identifier);
            ResultSet result = stmt.executeQuery();
            if (result.next()){
                data[0] = result.getString("uuid");
                data[1] = result.getString("nickname");
            }
            stmt.close();
        } catch (SQLException exception) {
            LOGGER.warn("Could not get player data! (Cause = {})", exception.getMessage());
        }
        return data;
    }
}
