package com.uecepi.emarrow.networking.account;

public class CredentialsPacket {

    private String identifier;
    private String password;

    public CredentialsPacket(){}

    public CredentialsPacket(String identifier, String password) {
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
