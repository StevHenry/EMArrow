package com.uecepi.emarrow.display;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController implements InputProcessor {

    public boolean left, right, jump, dash, shoot, up, down;
    public boolean isMouse1Down, isMouse2Down, isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2(0, 0);
    private int leftKey, rightKey, upKey, downKey, jumpKey, dashKey, shootKey;

    public KeyboardController() {
        this.upKey = Keys.Z;
        this.downKey = Keys.S;
        this.leftKey = Keys.Q;
        this.rightKey = Keys.D;
        this.dashKey = Keys.SHIFT_LEFT;
        this.jumpKey = Keys.SPACE;
        this.shootKey = Keys.A;
    }


    public boolean keyDown(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Keys.LEFT -> left = (leftKey == Keys.LEFT);
            case Keys.RIGHT -> right = (rightKey == Keys.RIGHT);
            case Keys.DOWN -> down = (downKey == Keys.DOWN);
            case Keys.Q -> left = (leftKey == Keys.Q);
            case Keys.D -> right = (rightKey == Keys.D);
            case Keys.S -> down = (downKey == Keys.S);
            case Keys.SPACE -> jump = (jumpKey == Keys.SPACE);
            case Keys.E -> dash = (dashKey == Keys.E);
            case Keys.SHIFT_LEFT -> dash = (dashKey == Keys.SHIFT_LEFT);
            case Keys.A -> shoot = true;
            default -> keyProcessed = false;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Keys.LEFT, Keys.Q -> left = false;
            case Keys.RIGHT, Keys.D -> right = false;
            case Keys.UP, Keys.Z -> up = false;
            case Keys.DOWN, Keys.S -> down = false;
            case Keys.SPACE -> jump = false;
            case Keys.E, Keys.SHIFT_LEFT -> dash = false;
            case Keys.A -> shoot = false;
            default -> keyProcessed = false;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0) {
            isMouse1Down = true;
            shoot = true;
        } else if (button == 1) {
            isMouse2Down = true;
        } else if (button == 2) {
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
        if (button == 0) {
            isMouse1Down = false;
            shoot = false;
        } else if (button == 1) {
            isMouse2Down = false;
        } else if (button == 2) {
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

    public void setShoot(int shootKey) {
        this.shootKey = shootKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public int getDashKey() {
        return dashKey;
    }

    public void setDashKey(int dashKey) {
        this.dashKey = dashKey;
    }

    public int getJumpKey() {
        return jumpKey;
    }

    public void setJumpKey(int jumpKey) {
        this.jumpKey = jumpKey;
    }

    public int getShootKey() {
        return shootKey;
    }
}