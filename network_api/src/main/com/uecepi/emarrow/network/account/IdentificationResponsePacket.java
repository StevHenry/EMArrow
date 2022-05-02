package com.uecepi.emarrow.network.account;

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
