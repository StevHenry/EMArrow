package com.uecepi.emarrow.network.game.player_action;

import java.util.UUID;

public class ProjectileCollisionPacket {

    private String entityUid;

    public ProjectileCollisionPacket() {
    }

    public ProjectileCollisionPacket(UUID entityUid) {
        this.entityUid = entityUid.toString();
    }

    public UUID getEntityUid() {
        return UUID.fromString(entityUid);
    }
}
