package com.uecepi.emarrow.networking.game.actions;

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
