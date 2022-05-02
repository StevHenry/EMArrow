package com.uecepi.emarrow.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.uecepi.emarrow.network.game.player_action.TransformationPacket;
import com.uecepi.emarrow.server.GameServer;

import java.util.UUID;

public class ConnectedPlayer {

    private final Connection connection;
    private final ServerCharacter serverCharacter;
    private final UUID uuid;
    private final String nickname;
    private final int skinId;

    public ConnectedPlayer(Connection connection, UUID uuid, String nickname, int skinId) {
        this.connection = connection;
        this.uuid = uuid;
        this.nickname = nickname;
        this.skinId = skinId;
        this.serverCharacter = new ServerCharacter();
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

    /**
     * Getter of the {@link #serverCharacter} attribute
     */
    public ServerCharacter getServerCharacter() {
        return serverCharacter;
    }

    public void setPosition(float x, float y) {
        serverCharacter.setPosition(x, y);
        GameServer.getInstance().getGame().sendTCPToAllPlayer(new TransformationPacket(uuid, x, y, 0));
    }
}
