package com.uecepi.emarrow.network.game;

import java.util.UUID;

public class PlayerAssignedAction {

    private String playerUid;

    public PlayerAssignedAction() {

    }

    public PlayerAssignedAction(UUID playerUid) {
        this.playerUid = playerUid.toString();
    }

    public UUID getPlayerUid() {
        return UUID.fromString(playerUid);
    }
}
