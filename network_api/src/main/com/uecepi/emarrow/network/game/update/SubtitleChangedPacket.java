package com.uecepi.emarrow.network.game.update;

public class SubtitleChangedPacket {

    private String subtitle;

    public SubtitleChangedPacket() {
    }

    public SubtitleChangedPacket(String newSubtitle) {
        this.subtitle = newSubtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
