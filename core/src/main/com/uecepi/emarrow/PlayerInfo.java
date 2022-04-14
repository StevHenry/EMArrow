package com.uecepi.emarrow;

import com.esotericsoftware.kryonet.Client;

import java.util.UUID;

public class PlayerInfo {

    private final Client clientConnection;
    private final String name;
    private final UUID uuid;
    private final Character character;

    public PlayerInfo(Character character, UUID uuid, String name) {
        this.character = character;
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
}
