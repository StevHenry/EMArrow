package com.emarrow.uecepi;

import com.emarrow.uecepi.accountserver.DatabaseConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTest.class);

    /**
     * Unit test that attempts a connection to the database
     */
    @Test
    public void connectionTest() {
        LOGGER.debug("Start of connectionTest.");
        DatabaseConnector.getInstance().connect();
        Assertions.assertTrue(DatabaseConnector.getInstance().isConnected());
        LOGGER.debug("End of connectionTest.");
    }

    /**
     * Unit test that verifies that the hashing method is correct
     */
    @Test
    public void hashTest() {
        LOGGER.debug("Start of hashTest.");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        try {
            Method method = DatabaseConnector.class.getDeclaredMethod("hashPassword", String.class, byte[].class);
            method.setAccessible(true);
            String hashingData = (String) method.invoke(connector, "1234", new byte[0]);
            Assertions.assertEquals("D404559F602EAB6FD602AC7680DACBFAADD13630335E951F097A" +
                    "F3900E9DE176B6DB28512F2E000B9D04FBA5133E8B1C6E8DF59DB3A8AB9D60BE4B97CC9E81DB", hashingData);

            String hashingData2 = (String) method.invoke(connector, "mypass", "abc".getBytes());
            Assertions.assertEquals("91B58BD2B72F862591B2D2F4C1617194EA31CAD03EE0754FAF28966E0C50836" +
                    "18F291043A9ECEB568D81BC032F0A0D1D94030832A37AC2F705491E13645299E9", hashingData2);
            method.setAccessible(false);

        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Cannot hash the password. {}", e.getMessage());
            Assertions.fail();
        }
        LOGGER.debug("End of hashTest.");
    }

    /**
     * Unit test that attempts to create a dummy account in the database
     */
    @Test
    public void accountCreationTest() {
        LOGGER.debug("Start of accountCreationTest.");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        connector.createAccount("demoAccount", "demopass", "SupameN");
        LOGGER.debug("End of accountCreationTest.");
    }

    /**
     * Unit test that ensures that a wrong password leads to a refusal
     */
    @Test
    public void wrongLoginTest() {
        LOGGER.debug("Start of wrongLoginTest.");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertFalse(connector.attemptLogIn("demoAccount", "wrongpass"));
        Assertions.assertFalse(connector.attemptLogIn("unknownaccount", ""));
        LOGGER.debug("End of wrongLoginTest.");
    }

    /**
     * Unit test that ensures that a wrong password leads to an acceptance
     */
    @Test
    public void goodLoginTest() {
        LOGGER.debug("Start of goodLoginTest.");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertTrue(connector.attemptLogIn("demoAccount", "demopass"));
        LOGGER.debug("End of goodLoginTest.");
    }

    /**
     * Unit test that removes the account created in the {@link #accountCreationTest()}
     */
    @AfterAll
    public static void deleteAccountTest(){
        LOGGER.debug("Start of deleteAccountTest.");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertTrue(connector.deleteAccount("demoAccount", "demopass"));
        LOGGER.debug("End of deleteAccountTest.");
    }

    /**
     * Unit test that ensures that a UUID is well gettable
     */
    @Test
    public void getUUIDTest() {
        LOGGER.debug("Start of getUUIDTest");
        DatabaseConnector connector = DatabaseConnector.getInstance();
        connector.connect();
        Assertions.assertNotEquals(connector.getPlayerData("demoAccount"), "00000000-0000-0000-0000-00000");
        LOGGER.debug("End of getUUIDTest.");
    }
}
