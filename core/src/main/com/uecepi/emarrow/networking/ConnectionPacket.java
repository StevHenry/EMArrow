package com.uecepi.emarrow.networking;

public class ConnectionPacket {

    private PlayerData data;

    public ConnectionPacket(PlayerData data){
        this.data = data;
    }

    public PlayerData getPlayerData(){
        return data;
    }
}
