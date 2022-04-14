package com.uecepi.emarrow.networking.game.actions;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class PlayerShootPacket extends PlayerAssignedAction {

    private float directionX, directionY;

    public PlayerShootPacket() {
    }

    public PlayerShootPacket(UUID playerUid, Vector2 direction) {
        super(playerUid);
        this.directionX = direction.x;
        this.directionY = direction.y;

    }

    public Vector2 getDirection() {
        return new Vector2(directionX, directionY);
    }

}
