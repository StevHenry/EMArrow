package com.uecepi.emarrow.networking.game.actions;

import java.util.UUID;

public class PlayerPositionPacket {

    private int x;
    private int y;
    private String playerUid;

    public PlayerPositionPacket() {
    }

    public PlayerPositionPacket(UUID playerUid, int xpos, int ypos) {
        this.x = xpos;
        this.y = ypos;
        this.playerUid = playerUid.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
