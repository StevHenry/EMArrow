package com.uecepi.emarrow.networking;

public class ConnectionResponsePacket {

    private boolean responseValue;

    public ConnectionResponsePacket() {
    }

    public ConnectionResponsePacket(boolean response) {
        this.responseValue = response;
    }

    public boolean getResponse() {
        return responseValue;
    }
}
