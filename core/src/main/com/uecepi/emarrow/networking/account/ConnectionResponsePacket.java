package com.uecepi.emarrow.networking.account;

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
