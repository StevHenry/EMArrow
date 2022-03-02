package com.emarrow.uecepi.server;

import com.esotericsoftware.kryonet.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStarter.class);

    private Server server;

    public ServerStarter(){
        server = new Server();
        LOGGER.info("Server starting...");
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Server binded on ports: 54555 (TCP) and 54777 (UDP)");
        server.addListener(new ServerListener());
    }
}
