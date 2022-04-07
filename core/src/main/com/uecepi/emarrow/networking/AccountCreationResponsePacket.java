package com.uecepi.emarrow.networking;

public class AccountCreationResponsePacket {

    /**
     * true = account successfully created
     * false = account not created!
     */
    private boolean responseValue;

    public AccountCreationResponsePacket() {

    }

    public AccountCreationResponsePacket(boolean response) {
        this.responseValue = response;
    }

    public boolean getResponseValue() {
        return responseValue;
    }
}
