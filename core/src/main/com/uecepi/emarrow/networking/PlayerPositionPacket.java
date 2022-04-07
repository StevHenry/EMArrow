package com.uecepi.emarrow.networking;

public class PlayerPositionPacket {

    private int x ;
    private int y ;

    public PlayerPositionPacket(){}

    public PlayerPositionPacket(int xpos, int ypos){

        this.x = xpos ;
        this.y = ypos ;

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
