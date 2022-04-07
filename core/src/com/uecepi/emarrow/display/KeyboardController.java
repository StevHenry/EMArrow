package com.uecepi.emarrow.display;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController  implements InputProcessor {
    public boolean left,right,jump, dash, shoot,up,down;
    public boolean isMouse1Down, isMouse2Down,isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2(0,0);
    private int leftKey, rightKey, upKey, downKey, jumpKey, dashKey, shootKey;
    public KeyboardController(){
        this.upKey = Keys.UP;
        this.downKey = Keys.DOWN;
        this.leftKey = Keys.Q;
        this.rightKey = Keys.RIGHT;
        this.dashKey = Keys.E;
        this.jumpKey = Keys.SPACE;
        this.shootKey = Keys.A;

    }



    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) //switch code base on the variable keycode
        {
            case Keys.LEFT:     //if keycode is the same as Keys.LEFT a.k.a 21
                left = (leftKey == Keys.LEFT);
                keyProcessed = true;
                break;
            case Keys.RIGHT:    //if keycode is the same as Keys.LEFT a.k.a 22
                right = (rightKey == Keys.RIGHT);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.UP:    //if keycode is the same as Keys.LEFT a.k.a 22
                up = (upKey == Keys.UP);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.DOWN:    //if keycode is the same as Keys.LEFT a.k.a 22
                down = (downKey == Keys.DOWN);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.Q:     //if keycode is the same as Keys.LEFT a.k.a 21
                left =  (leftKey == Keys.Q);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.D:    //if keycode is the same as Keys.LEFT a.k.a 22
                right = (rightKey == Keys.D);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.SPACE:       //if keycode is the same as Keys.LEFT a.k.a 19
                jump = (jumpKey == Keys.SPACE);
                keyProcessed = true;//we have reacted to a keypress
                break;
            case Keys.E:       //if keycode is the same as Keys.LEFT a.k.a 19
                dash = (dashKey == Keys.E);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.SHIFT_LEFT:       //if keycode is the same as Keys.LEFT a.k.a 19
                dash = (dashKey == Keys.SHIFT_LEFT);
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.A:       //if keycode is the same as Keys.LEFT a.k.a 19
                shoot = true;      //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
        }
        return keyProcessed;    // return our peyProcessed flag
    }
    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) //switch code base on the variable keycode
        {
            case Keys.LEFT:     //if keycode is the same as Keys.LEFT a.k.a 21
                left = false;   //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.RIGHT:    //if keycode is the same as Keys.LEFT a.k.a 22
                right = false;  //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.UP:    //if keycode is the same as Keys.LEFT a.k.a 22
                up = false;  //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.DOWN:    //if keycode is the same as Keys.LEFT a.k.a 22
                down = false;  //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.Q:     //if keycode is the same as Keys.LEFT a.k.a 21
                left = false;   //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.D:    //if keycode is the same as Keys.LEFT a.k.a 22
                right = false;  //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.SPACE:       //if keycode is the same as Keys.LEFT a.k.a 19
                jump = false;      //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.E:       //if keycode is the same as Keys.LEFT a.k.a 19
                dash = false;      //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.SHIFT_LEFT:   //if keycode is the same as Keys.LEFT a.k.a 19
                dash = false;   //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
            case Keys.A:       //if keycode is the same as Keys.LEFT a.k.a 19
                shoot = false;      //do this
                keyProcessed = true;    //we have reacted to a keypress
                break;
        }
        return keyProcessed;    // return our peyProcessed flag
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        //System.out.println(button);
        if(button == 0){
            isMouse1Down = false;
        }else if(button == 1){
            isMouse2Down = false;
        }else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public void setJumpKey(int jumpKey) {
        this.jumpKey = jumpKey;
    }

    public void setDashKey(int dashKey) {
        this.dashKey = dashKey;
    }

    public void setShoot(int shootKey) {
        this.shootKey = shootKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public int getDashKey() {
        return dashKey;
    }

    public int getJumpKey() {
        return jumpKey;
    }

    public int getShootKey() {
        return shootKey;
    }
}