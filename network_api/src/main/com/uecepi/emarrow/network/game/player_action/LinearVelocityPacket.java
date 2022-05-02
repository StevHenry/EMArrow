package com.uecepi.emarrow.network.game.player_action;

import com.badlogic.gdx.math.Vector2;
import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class LinearVelocityPacket extends PlayerAssignedAction {

    private float velocityX, velocityY;


    public LinearVelocityPacket() {
    }

    public LinearVelocityPacket(UUID playerUid, Vector2 velocity) {
        super(playerUid);
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
    }

    public Vector2 getVelocity() {
        return new Vector2(velocityX, velocityY);
    }
}
