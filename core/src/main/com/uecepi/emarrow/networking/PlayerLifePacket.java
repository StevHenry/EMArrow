package com.uecepi.emarrow.networking;

public class PlayerLifePacket {

    private int life ;
    private String uuid ;

    public PlayerLifePacket(){}

    public PlayerLifePacket(int life, String uuid){

        this.life = life ;
        this.uuid = uuid ;


    }

    public String getUuid() {
        return uuid;
    }

    public int getLife() {
        return life;
    }


}
