package com.uecepi.emarrow.networking.game;

import com.uecepi.emarrow.networking.game.actions.PlayerAssignedAction;

import java.util.UUID;

public class PlayerDisconnectedPacket extends PlayerAssignedAction {

    public PlayerDisconnectedPacket() {

    }

    public PlayerDisconnectedPacket(UUID playerUid) {
        super(playerUid);
    }
}
