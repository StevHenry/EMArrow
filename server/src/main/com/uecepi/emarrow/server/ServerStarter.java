package com.uecepi.emarrow.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.actions.ForceAppliedPacket;
import com.uecepi.emarrow.networking.game.actions.PlayerPositionPacket;
import com.uecepi.emarrow.networking.game.actions.PlayerShootPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStarter.class);

    private final Server server;

    public ServerStarter() {
        server = new Server();
        LOGGER.info("Game server starting...");
        server.start();
        registerPackets();

        server.addListener(new ServerListener());
        try {
            server.bind(54556, 54778);
            LOGGER.info("Server bound on ports: 54556 (TCP) and 54778 (UDP)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(PlayerDataPacket.class);
        kryo.register(PlayerPositionPacket.class);
        kryo.register(PlayerShootPacket.class);
        kryo.register(ForceAppliedPacket.class);
        kryo.register(SkinIndexPacket.class);
    }
}
