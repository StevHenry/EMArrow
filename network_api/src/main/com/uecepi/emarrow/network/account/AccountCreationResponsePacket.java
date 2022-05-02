package com.uecepi.emarrow.network.account;

public class AccountCreationResponsePacket {

    private boolean responseValue;

    public AccountCreationResponsePacket() {
    }

    /**
     * true = account successfully created
     * false = account not created!
     */
    public AccountCreationResponsePacket(boolean response) {
        this.responseValue = response;
    }

    public boolean getResponseValue() {
        return responseValue;
    }
}
