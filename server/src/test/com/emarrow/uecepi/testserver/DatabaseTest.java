package com.emarrow.uecepi.testserver;

import com.emarrow.uecepi.database.DatabaseConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTest.class);

    @Test
    public void connectionTest() {
        DatabaseConnector.getInstance().connect();
        Assertions.assertTrue(DatabaseConnector.getInstance().isConnected());
    }

    @Test
    public void hashTest() {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        try {
            Method method = DatabaseConnector.class.getDeclaredMethod("hashPassword", String.class, String.class);
            method.setAccessible(true);
            String hashingData = (String) method.invoke(connector, "1234", "");
            Assertions.assertEquals("D404559F602EAB6FD602AC7680DACBFAADD13630335E951F097A" +
                    "F3900E9DE176B6DB28512F2E000B9D04FBA5133E8B1C6E8DF59DB3A8AB9D60BE4B97CC9E81DB", hashingData);

            String hashingData2 = (String) method.invoke(connector, "mypass", "abc");
            Assertions.assertEquals("91B58BD2B72F862591B2D2F4C1617194EA31CAD03EE0754FAF28966E0C50836" +
                    "18F291043A9ECEB568D81BC032F0A0D1D94030832A37AC2F705491E13645299E9", hashingData2);
            method.setAccessible(false);

        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Cannot hash the password. {}", e.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void accountCreationTest() {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        connector.createAccount("demoAccount", "demopass", "SupameN");
    }

    @Test
    public void wrongLoginTest() {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertFalse(connector.logIn("demoAccount", "wrongpass"));
        Assertions.assertFalse(connector.logIn("unknownaccount", ""));
    }

    @Test
    public void goodLoginTest() {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertTrue(connector.logIn("demoAccount", "demopass"));
    }
}
