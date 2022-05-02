package com.uecepi.emarrow.network;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int MAX_HP = 100;
    public static final float FIRE_RATE = 1000f;
    public static final float SPEED = 25f;
    /**
     * Time in seconds
     */
    public static final int PREPARING_STATE_DURATION = 3;

    public static final Vector2[] INITIAL_POSITIONS = {new Vector2(25, 110), new Vector2(225, 125),
            new Vector2(425, 110)};

    public static final Vector2 FALLBACK_POSITION = new Vector2(50, 100f);

    /**
     * Time in seconds
     */
    public static final int TIME_BEFORE_DISCONNECTING = 3;

    //TODO: Add some positions

}
