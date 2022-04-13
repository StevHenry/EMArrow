package com.uecepi.emarrow.networking.game.actions;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class ForceAppliedPacket {

    private float forceX, forceY;
    private float pointX, pointY;
    private boolean wake;

    private String playerUid;

    public ForceAppliedPacket() {
    }

    public ForceAppliedPacket(UUID playerUid, Vector2 force, Vector2 point, boolean wake) {
        this.playerUid = playerUid.toString();
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

    public String getPlayerUid() {
        return playerUid;
    }
}
