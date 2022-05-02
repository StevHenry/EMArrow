package com.uecepi.emarrow.network.game.player_action;

import java.util.UUID;

public class PlayerDamagedPacket {

    private String damagedUid;
    private int damage;

    public PlayerDamagedPacket() {
    }

    public PlayerDamagedPacket(UUID damaged, int damage) {
        this.damagedUid = damaged.toString();
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public UUID getDamagedUid() {
        return UUID.fromString(damagedUid);
    }
}
