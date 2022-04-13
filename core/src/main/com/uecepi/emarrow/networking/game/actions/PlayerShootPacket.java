package com.uecepi.emarrow.networking.game.actions;

import com.badlogic.gdx.math.Vector2;

public class PlayerShootPacket {

    private String uuid ;
    private Vector2 direction ;

    public PlayerShootPacket(){}

    public PlayerShootPacket(String playerUid, Vector2 direction){

        this.direction = direction ;
        this.uuid = playerUid ;

    }

    public String getUuid() {
        return uuid;
    }

    public Vector2 getDirection() {
        return direction;
    }

}
