package com.uecepi.emarrow.networking.account;

import java.util.UUID;

public class PlayerDataPacket {

    private String uuid;
    private String nickname;

    public PlayerDataPacket() {

    }

    public PlayerDataPacket(UUID uuid, String nickname) {
        this.uuid = uuid.toString();
        this.nickname = nickname;
    }

    public UUID getUUID() {
        return UUID.fromString(uuid);
    }

    public String getNickname() {
        return nickname;
    }
}
