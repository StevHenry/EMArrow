package com.uecepi.emarrow.network.game.player_action;

import com.badlogic.gdx.math.Vector2;
import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class ForceAppliedPacket extends PlayerAssignedAction {

    private float forceX, forceY;
    private float pointX, pointY;
    private boolean wake;

    public ForceAppliedPacket() {
    }

    public ForceAppliedPacket(UUID playerUid, Vector2 force, Vector2 point, boolean wake) {
        super(playerUid);
        this.forceX = force.x;
        this.forceY = force.y;
        this.pointX = point.x;
        this.pointY = point.y;
        this.wake = wake;
    }

    public Vector2 getForce() {
        return new Vector2(forceX, forceY);
    }

    public Vector2 getApplicationPoint() {
        return new Vector2(pointX, pointY);
    }

    public boolean isWake() {
        return wake;
    }
}
