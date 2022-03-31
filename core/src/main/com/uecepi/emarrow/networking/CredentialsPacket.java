package com.uecepi.emarrow.networking;

public class CredentialsPacket {

    private final String identifier;
    private final String password;

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
