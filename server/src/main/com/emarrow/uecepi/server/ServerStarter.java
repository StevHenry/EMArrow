package com.emarrow.uecepi.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.networking.PingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStarter.class);

    private final Server server;

    public ServerStarter() {
        server = new Server();
        LOGGER.info("Server starting...");
        server.start();
        registerPackets();

        server.addListener(new ServerListener());
        try {
            server.bind(54555, 54777);
            LOGGER.info("Server bound on ports: 54555 (TCP) and 54777 (UDP)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(PingPacket.class);
    }
}
