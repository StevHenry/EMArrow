package com.uecepi.emarrow.networking;

import java.util.UUID;

public class SkinIndexPacket {

    private int skinIndex = 1;
    private String uuid;

    public SkinIndexPacket() {

    }

    public SkinIndexPacket(UUID uuid, int index) {
        this.skinIndex = index;
        this.uuid = uuid.toString();
    }

    public int getSkinIndex() {
        return skinIndex;
    }

    public UUID getUUID() {
        return UUID.fromString(uuid);
    }
}
