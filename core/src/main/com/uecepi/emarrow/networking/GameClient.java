package com.uecepi.emarrow.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.uecepi.emarrow.network.PacketManager;

import java.io.IOException;

public class GameClient {

    private Client client;

    public GameClient() {
        create();
    }

    private void create() {
        Gdx.app.log("game_client", "Game client starting...");
        client = new Client();
        client.start();
        PacketManager.registerGamePackets(client.getKryo());
        client.addListener(new GameListener());
        client.addListener(new ClientPlayerActionListener());
        Gdx.app.log("game_client", "Game client started.");
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void sendTCP(Object object) {
        client.sendTCP(object);
    }


    public boolean connect(String ip) {
        try {
            client.connect(5000, ip, 54556, 54778);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void reconnect() {
        try {
            client.reconnect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        client.close();
    }
}

