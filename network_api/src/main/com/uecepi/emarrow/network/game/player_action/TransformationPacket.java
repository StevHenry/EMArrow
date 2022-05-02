package com.uecepi.emarrow.network.game.player_action;

import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class TransformationPacket extends PlayerAssignedAction {

    private float x, y, angle;

    public TransformationPacket() {
    }

    public TransformationPacket(UUID playerUid, float x, float y, float angle) {
        super(playerUid);
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

}
