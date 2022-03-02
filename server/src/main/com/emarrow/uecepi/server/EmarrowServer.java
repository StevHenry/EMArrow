package com.emarrow.uecepi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmarrowServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmarrowServer.class);

    public static void main(String[] args) {
        new ServerStarter();
    }
}
