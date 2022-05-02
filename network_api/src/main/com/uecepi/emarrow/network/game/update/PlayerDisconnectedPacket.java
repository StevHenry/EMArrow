package com.uecepi.emarrow.network.game.update;

import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class PlayerDisconnectedPacket extends PlayerAssignedAction {

    public PlayerDisconnectedPacket() {

    }

    public PlayerDisconnectedPacket(UUID playerUid) {
        super(playerUid);
    }
}
