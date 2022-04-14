package com.uecepi.emarrow.networking.game.actions;

import java.util.UUID;

public class ForceToCenterPacket extends PlayerAssignedAction {

    private float forceX, forceY;
    private boolean wake;

    public ForceToCenterPacket() {
    }

    public ForceToCenterPacket(UUID playerUid, float forceX, float forceY, boolean wake) {
        super(playerUid);
        this.forceX = forceX;
        this.forceY = forceY;
        this.wake = wake;
    }

    public float getForceX() {
        return forceX;
    }

    public float getForceY() {
        return forceY;
    }

    public boolean isWake() {
        return wake;
    }

}
