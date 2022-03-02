package com.emarrow.uecepi.testserver;

import com.emarrow.uecepi.server.ServerStarter;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.ConnectionPacket;
import com.uecepi.emarrow.networking.PlayerData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class ServerTest {

    @Test
    public void testServer() {
        new ServerStarter();

        Client client = new Client();
        client.start();
        try {
            client.connect(5000, "127.0.0.1", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerData data = new PlayerData("moi", UUID.randomUUID());
        ConnectionPacket connectionPacket = new ConnectionPacket(data);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
                if (o instanceof ConnectionPacket) {
                    ConnectionPacket connectionPacket = (ConnectionPacket) o;
                    Assert.assertEquals(connectionPacket.getPlayerData().getUUID(), data.getUUID());
                }
            }
        });
        client.sendTCP(connectionPacket);
    }
}
