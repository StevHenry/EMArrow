package com.uecepi.emarrow.network.game.player_action;

import com.badlogic.gdx.math.Vector2;
import com.uecepi.emarrow.network.game.PlayerAssignedAction;

import java.util.UUID;

public class PlayerShootPacket extends PlayerAssignedAction {

    private float directionX, directionY;
    private String entityUid;

    public PlayerShootPacket() {
    }

    public PlayerShootPacket(UUID playerUid, Vector2 direction, UUID entityUid) {
        super(playerUid);
        this.directionX = direction.x;
        this.directionY = direction.y;
        this.entityUid = entityUid.toString();
    }

    public Vector2 getDirection() {
        return new Vector2(directionX, directionY);
    }

    public UUID getEntityUid() {
        return UUID.fromString(entityUid);
    }

}
