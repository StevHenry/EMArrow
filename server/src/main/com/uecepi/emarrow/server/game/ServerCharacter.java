package com.uecepi.emarrow.server.game;

import com.badlogic.gdx.math.Vector2;
import com.uecepi.emarrow.network.Constants;

public class ServerCharacter {

    private final Vector2 position;
    private boolean lookingLeft = false;
    private int life = Constants.MAX_HP;

    public ServerCharacter() {
        position = new Vector2();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(position.x, position.y);
    }

    public boolean isLookingLeft() {
        return lookingLeft;
    }

    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    public void damage(int damage) {
        if (life < damage) {
            life = 0;
        } else {
            life -= damage;
        }
    }

    public boolean isDead() {
        return life == 0f;
    }
}
