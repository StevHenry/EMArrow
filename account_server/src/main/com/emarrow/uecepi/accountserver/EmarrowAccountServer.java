package com.emarrow.uecepi.accountserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmarrowAccountServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmarrowAccountServer.class);

    public static void main(String[] args) {
        new AccountServerStarter();
    }
}
