package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Server;
import com.uecepi.emarrow.network.PacketManager;
import com.uecepi.emarrow.server.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GameServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private static GameServer instance;
    private Game game;

    GameServer(int tcpPort, int udpPort) {
        instance = this;
        LOGGER.info("Game server starting...");
        Server server = new Server();
        server.start();
        PacketManager.registerGamePackets(server.getKryo());
        game = new Game(2);

        server.addListener(new ConnectionProcedureListener());
        server.addListener(new PlayerActionListener());
        try {
            server.bind(tcpPort, udpPort);
            LOGGER.info("Server bound on ports: {} (TCP) and {} (UDP)", tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error while");
            System.exit(1);
            return;
        }
        LOGGER.debug("Game server started...");
    }

    public static GameServer getInstance() {
        return instance;
    }

    public void nextGame() {
        LOGGER.info("Game is finished! Creating a new one!");

        game = new Game(game.getMaxPlayersCount());
    }

    public Game getGame() {
        return game;
    }
}
