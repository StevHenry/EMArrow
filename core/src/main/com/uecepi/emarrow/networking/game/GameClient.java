package com.uecepi.emarrow.networking.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.game.actions.ForceAppliedPacket;
import com.uecepi.emarrow.networking.game.actions.PlayerPositionPacket;
import com.uecepi.emarrow.networking.game.actions.PlayerShootPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;

import java.io.IOException;

public class GameClient {

    private Client client;

    public GameClient() {
        create();
    }

    private void create() {
        client = new Client();
        Gdx.app.log("game_client", "Game client starting...");
        client.start();
        registerPackets();
        client.addListener(new GameListener());
    }

    private void registerPackets() {
        Kryo kryo = client.getKryo();
        kryo.register(PlayerDataPacket.class);
        kryo.register(PlayerPositionPacket.class);
        kryo.register(PlayerShootPacket.class);
        kryo.register(ForceAppliedPacket.class);
        kryo.register(SkinIndexPacket.class);
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void sendTCP(Object object) {
        client.sendTCP(object);
    }


    public void connect(String ip) {
        try {
            client.connect(5000, ip, 54556, 54778);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconnect() {
        try {
            client.reconnect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

