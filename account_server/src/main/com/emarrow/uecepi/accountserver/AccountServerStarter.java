package com.emarrow.uecepi.accountserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.networking.ConnectionResponsePacket;
import com.uecepi.emarrow.networking.CredentialsPacket;
import com.uecepi.emarrow.networking.PingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AccountServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServerStarter.class);

    private final Server server;

    public AccountServerStarter() {
        server = new Server();
        LOGGER.info("Account server starting...");
        server.start();
        registerPackets();

        server.addListener(new AccountServerListener());
        try {
            server.bind(54555, 54777);
            LOGGER.info("Server bound on ports: 54555 (TCP) and 54777 (UDP)");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseConnector.getInstance().connect();
        if (!DatabaseConnector.getInstance().isConnected()) {
            LOGGER.error("Account server closing.");
            System.exit(0);
        }
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(PingPacket.class);
        kryo.register(CredentialsPacket.class);
        kryo.register(ConnectionResponsePacket.class);
    }
}
