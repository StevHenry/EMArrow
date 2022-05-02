package com.uecepi.emarrow.network.game.update;

public class TitleChangedPacket {

    private String title;

    public TitleChangedPacket() {
    }

    public TitleChangedPacket(String newTitle) {
        this.title = newTitle;
    }

    public String getTitle() {
        return title;
    }
}
