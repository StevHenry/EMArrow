package com.uecepi.emarrow;

import com.esotericsoftware.kryonet.Client;

import java.util.UUID;

public class PlayerInfo {

    private final Client clientConnection;
    private final String name;
    private final UUID uuid;
    private Character character;

    public PlayerInfo(UUID uuid, String name) {
        this.character = new Character(this);
        this.name = name;
        this.clientConnection = new Client();
        this.uuid = uuid;
    }

    public Character getCharacter() {
        return character;
    }

    public Client getClientConnection() {
        return clientConnection;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * This method is called to regenerate the character (when a game is finished)
     */
    public void resetCharacter() {
        this.character = new Character(this);
    }

}
