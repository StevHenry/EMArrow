package com.uecepi.emarrow.networking.account;

public class IdentificationResponsePacket {

    private boolean responseValue;

    public IdentificationResponsePacket() {
    }

    public IdentificationResponsePacket(boolean response) {
        this.responseValue = response;
    }

    public boolean getResponse() {
        return responseValue;
    }
}
