package com.uecepi.emarrow.network.game.player_action;

import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class CharacterFlipPacket extends PlayerAssignedAction {

    private boolean flippedLeft;

    public CharacterFlipPacket() {
    }

    public CharacterFlipPacket(UUID uuid, boolean flippedLeft) {
        super(uuid);
        this.flippedLeft = flippedLeft;
    }

    public boolean isFlippedLeft() {
        return flippedLeft;
    }

}
