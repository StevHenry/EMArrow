package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.UUID;

public class ConnectedPlayer {

    private final Connection connection;
    private final UUID uuid;
    private final String nickname;
    private final int skinId;

    public ConnectedPlayer(Connection connection, UUID uuid, String nickname, int skinId) {
        this.connection = connection;
        this.uuid = uuid;
        this.nickname = nickname;
        this.skinId = skinId;
    }

    /**
     * Getter of the {@link #connection} attribute
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Getter of the {@link #uuid} attribute
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Getter of the {@link #nickname} attribute
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter of the {@link #skinId} attribute
     */
    public int getSkinId() {
        return skinId;
    }
}
