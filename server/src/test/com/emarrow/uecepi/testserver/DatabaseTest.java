package com.emarrow.uecepi.testserver;

import com.emarrow.uecepi.database.DatabaseConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    @Test
    public void connectionTest(){
        Assertions.assertTrue(DatabaseConnector.getInstance().isConnected());
    }

    @Test
    public void hashTest(){
        String hashedPass = DatabaseConnector.getInstance().hashPassword("1234")[0];
        Assertions.assertEquals(hashedPass, "D404559F602EAB6FD602AC7680DACBFAADD13630335E951F097AF3900E9DE176B6DB28512F2E000B9D04FBA5133E8B1C6E8DF59DB3A8AB9D60BE4B97CC9E81DB");
    }
}
