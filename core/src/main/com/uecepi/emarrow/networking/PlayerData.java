package com.uecepi.emarrow.networking;

import java.util.UUID;

public class PlayerData {

    private String nickname;
    private final UUID uuid;

    public PlayerData(String nickname, UUID uuid){
        this.nickname = nickname;
        this.uuid = uuid;
    }

    /**
     * @return the player name displayed in game
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player unique identifier as a {@link UUID}
     */
    public UUID getUUID() {
        return uuid;
    }
}
