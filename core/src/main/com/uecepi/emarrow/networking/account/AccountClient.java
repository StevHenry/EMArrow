package com.uecepi.emarrow.networking.account;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.PlayerInfo;
import com.uecepi.emarrow.display.menus.LogInMenu;
import com.uecepi.emarrow.display.menus.SignInMenu;
import com.uecepi.emarrow.networking.PacketManager;
import com.uecepi.emarrow.networking.PingPacket;

import java.io.IOException;
import java.util.UUID;

public class AccountClient {

    private Client client;
    private SignInMenu signInMenu;
    private LogInMenu logInMenu;

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
                    logInMenu.responseReceived(((IdentificationResponsePacket) object).getResponse());
                } else if (object instanceof AccountCreationResponsePacket) {
                    signInMenu.responseReceived(((AccountCreationResponsePacket) object).getResponseValue());
                } else if (object instanceof PlayerDataPacket) {
                    PlayerDataPacket packet = (PlayerDataPacket) object;
                    Gdx.app.postRunnable(() -> GameEngine.getInstance().setSelfPlayer(
                            new PlayerInfo(new Character(), UUID.randomUUID(), packet.getNickname())));
                }
            }
        });
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void setSignInMenuInstance(SignInMenu menu) {
        this.signInMenu = menu;
    }

    public void setLogInMenuInstance(LogInMenu menu) {
        this.logInMenu = menu;
    }

    public void sendTCP(Object object){
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

    public void disconnect(){
        client.close();
    }
}