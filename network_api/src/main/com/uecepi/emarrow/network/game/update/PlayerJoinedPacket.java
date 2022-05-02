package com.uecepi.emarrow.network.game.update;

import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class PlayerJoinedPacket extends PlayerAssignedAction {

    private int skinId;
    private String name;

    public PlayerJoinedPacket() {

    }

    public PlayerJoinedPacket(UUID playerUid, String name, int skinId) {
        super(playerUid);
        this.name = name;
        this.skinId = skinId;
    }

    public int getSkinId() {
        return skinId;
    }

    public String getName() {
        return name;
    }
}
