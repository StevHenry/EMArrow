package com.uecepi.emarrow.networking.account;

public class AccountCreationPacket {

    private String identifier;
    private String pass;
    private String nickname;

    public AccountCreationPacket(){

    }

    public AccountCreationPacket(String identifier, String pass, String nickname) {
        this.identifier = identifier;
        this.pass = pass;
        this.nickname = nickname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPass() {
        return pass;
    }

    public String getNickname() {
        return nickname;
    }
}
