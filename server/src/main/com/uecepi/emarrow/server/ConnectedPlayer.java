package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.UUID;

public class ConnectedPlayer {

    private final Connection connection;
    private final UUID uuid;
    private final String nickname;

    public ConnectedPlayer(Connection connection, UUID uuid, String nickname) {
        this.connection = connection;
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public Connection getConnection() {
        return connection;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }
}
