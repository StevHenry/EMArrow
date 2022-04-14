package com.emarrow.uecepi.accountserver;

import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.networking.PacketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AccountServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServerStarter.class);

    private final Server server;

    public AccountServerStarter(int tcpPort, int udpPort) {
        server = new Server();
        LOGGER.info("Account server starting...");
        server.start();
        PacketManager.registerAccountPackets(server.getKryo());

        server.addListener(new AccountServerListener());
        try {
            server.bind(tcpPort, udpPort);
            LOGGER.info("Server bound on ports: {} (TCP) and {} (UDP)", tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseConnector.getInstance().connect();
        if (!DatabaseConnector.getInstance().isConnected()) {
            LOGGER.error("Account server closing.");
            System.exit(0);
        }
    }
}
