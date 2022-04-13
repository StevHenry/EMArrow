package com.uecepi.emarrow.networking;

public class SkinIndexPacket {

    private int skinIndex = 1;

    public SkinIndexPacket(){

    }

    public SkinIndexPacket(int index){
        this.skinIndex = index;
    }

    public int getSkinIndex() {
        return skinIndex;
    }
}
