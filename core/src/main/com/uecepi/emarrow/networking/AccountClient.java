package com.uecepi.emarrow.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.PlayerInfo;
import com.uecepi.emarrow.display.Screens;
import com.uecepi.emarrow.display.menus.LogInMenu;
import com.uecepi.emarrow.display.menus.SignInMenu;
import com.uecepi.emarrow.network.PacketManager;
import com.uecepi.emarrow.network.account.AccountCreationResponsePacket;
import com.uecepi.emarrow.network.account.IdentificationResponsePacket;
import com.uecepi.emarrow.network.account.PlayerDataPacket;

import java.io.IOException;

public class AccountClient {

    private Client client;

    public AccountClient() {
        create();
    }

    private void create() {
        client = new Client();
        Gdx.app.log("client", "Account client starting...");
        client.start();
        PacketManager.registerAccountPackets(client.getKryo());

        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                Gdx.app.log("account_client", "Account client connected.");
            }

            @Override
            public void disconnected(Connection connection) {
                Gdx.app.log("account_client", "Account client disconnected.");
            }

            @Override
            public void received(Connection connection, Object object) {
                Gdx.app.log("account_client", "Received a packet!");
                if (object instanceof IdentificationResponsePacket) {
                    ((LogInMenu) Screens.LOG_IN_MENU.getScreenMenu()).responseReceived(((IdentificationResponsePacket) object).getResponse());
                } else if (object instanceof AccountCreationResponsePacket) {
                    ((SignInMenu) Screens.SIGN_IN_MENU.getScreenMenu()).responseReceived(((AccountCreationResponsePacket) object).getResponseValue());
                } else if (object instanceof PlayerDataPacket) {
                    PlayerDataPacket packet = (PlayerDataPacket) object;
                    Gdx.app.postRunnable(() -> GameEngine.getInstance().setSelfPlayer(
                            new PlayerInfo(packet.getUUID(), packet.getNickname())));
                }
            }
        });
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void sendTCP(Object object) {
        client.sendTCP(object);
    }

    public void connect() {
        try {
            client.connect(5000, "127.0.0.1", 54555, 54777);
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

    public void disconnect() {
        client.close();
    }
}
