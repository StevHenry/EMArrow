package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.networking.PacketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStarter.class);

    private final Server server;

    public ServerStarter(int tcpPort, int udpPort) {
        server = new Server();
        LOGGER.info("Game server starting...");
        server.start();
        PacketManager.registerGamePackets(server.getKryo());

        server.addListener(new ServerListener());
        try {
            server.bind(tcpPort, udpPort);
            LOGGER.info("Server bound on ports: {} (TCP) and {} (UDP)", tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
