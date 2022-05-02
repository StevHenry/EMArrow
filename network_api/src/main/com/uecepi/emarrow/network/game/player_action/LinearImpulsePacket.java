package com.uecepi.emarrow.network.game.player_action;

import com.badlogic.gdx.math.Vector2;
import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class LinearImpulsePacket extends PlayerAssignedAction {

    private float impulseX, impulseY;
    private float pointX, pointY;
    private boolean wake;


    public LinearImpulsePacket() {
    }

    public LinearImpulsePacket(UUID playerUid, Vector2 impulse, Vector2 point, boolean wake) {
        super(playerUid);
        this.impulseX = impulse.x;
        this.impulseY = impulse.y;
        this.pointX = point.x;
        this.pointY = point.y;
        this.wake = wake;
    }

    public Vector2 getImpulse() {
        return new Vector2(impulseX, impulseY);
    }

    public Vector2 getApplicationPoint() {
        return new Vector2(pointX, pointY);
    }

    public boolean isWake() {
        return wake;
    }
}
