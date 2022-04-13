package com.uecepi.emarrow.networking.account;

public class IdentificationPacket {

    private String identifier;
    private String password;

    public IdentificationPacket(){}

    public IdentificationPacket(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }
}
