package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.Animator;
import com.uecepi.emarrow.display.KeyboardController;
import com.uecepi.emarrow.networking.game.actions.ForceAppliedPacket;

public class InputManager {

    private static final int DASH_IMPULSE = 42000;

    private final KeyboardController controller;

    public InputManager() {
        controller = new KeyboardController();
        controller.setLeftKey(Input.Keys.Q);
        controller.setRightKey(Input.Keys.D);
        controller.setDashKey(Input.Keys.SHIFT_LEFT);
    }

    public void processInput() {//TODO CHANGER players.get(0) EN ACTIVE PLAYER (CELUI QUI JOUE sur le pc)
        Character self = GameEngine.getInstance().getCharacters().get(0);
        if (controller.left) {
            movePlayer(self, -200, true);
        } else if (controller.right) {
            movePlayer(self, 200, false);
        } else {
            stopPlayer(self);
        }

        if (controller.down)
            self.getBody().applyForceToCenter(0, -500f, true);

        if (controller.jump)
            jump(self);

        if (controller.dash)
            dash(self);

        if (controller.shoot)
            shoot(self);

    }

    public void resetInputs() {
        controller.left = controller.right = controller.up = controller.down = false;
        controller.jump = controller.dash = controller.shoot = false;
    }

    private void shoot(Character self) {
        if (self.isGrounded())
            self.getAnimator().setCurrentAnimation(Animator.STANDING_SHOT_ANIMATION);
        else
            self.getAnimator().setCurrentAnimation(Animator.FLYING_SHOT_ANIMATION);
        self.shoot();
    }

    private void dash(Character self) {
        if (self.getDashLeft() > 0) {
            MusicManager.playSE(MusicManager.DASH_SE);
            self.setGrounded(false);
            self.setDashLeft(self.getDashLeft() - 1);

            Body body = self.getBody();
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            double projectileX = body.getPosition().x * 1740 / 445;
            double projectileY = 950 - body.getPosition().y * 1740 / 445;
            double norm = Math.sqrt((mouseX - projectileX) * (mouseX - projectileX) + (mouseY - projectileY) * (mouseY - projectileY));
            Vector2 dashDirection = new Vector2((float) ((mouseX - projectileX) / norm), (float) ((mouseY - projectileY) / norm));

            body.applyLinearImpulse(new Vector2(DASH_IMPULSE * dashDirection.x, -DASH_IMPULSE * dashDirection.y), body.getPosition(), true);
        }
    }

    private void jump(Character self) {
        if (self.getJumpLeft() > 0) {
            MusicManager.playSE(MusicManager.JUMP_SE);
            self.getAnimator().setCurrentAnimation(Animator.JUMPING_ANIMATION);
            self.setGrounded(false);
            self.setJumpLeft(self.getJumpLeft() - 1);
            //self.getBody().applyLinearImpulse(new Vector2(0, 150), self.getBody().getPosition(), true);
            self.getBody().applyForceToCenter(0, 8000f, true);
        }
        controller.jump = false;
    }

    private void stopPlayer(Character self) {
        if (self.getBody().getLinearVelocity().x < -10) {
            self.getBody().applyForce(new Vector2(150, self.getBody().getLinearVelocity().y), self.getBody().getPosition(), true);
        } else if (self.getBody().getLinearVelocity().x > 10) {
            self.getBody().applyForce(new Vector2(-150, self.getBody().getLinearVelocity().y), self.getBody().getPosition(), true);
        } else {
            self.getBody().setLinearVelocity(0, self.getBody().getLinearVelocity().y);
        }

        if (!self.getAnimator().getCurrentAnimation().equals(Animator.STANDING_ANIMATION)) {
            self.getAnimator().setCurrentAnimation(Animator.STANDING_ANIMATION);
        } else if (!self.getAnimator().getCurrentAnimation().equals(Animator.FLYING_ANIMATION)) {
            self.getAnimator().setCurrentAnimation(Animator.FLYING_ANIMATION);            // Stop moving in the Y direction
        }
    }

    private void movePlayer(Character self, int i, boolean b) {
        Vector2 force = new Vector2(i, 0), point = self.getBody().getPosition();
        boolean wake = true;

        self.getBody().applyForce(force, point, wake);
        GameEngine.getInstance().getGameClient().sendTCP(
                new ForceAppliedPacket(GameEngine.getInstance().getPlayers().get(0).getUuid(), force, point, wake));
        self.getAnimator().setFlippedToLeft(b);
        if (self.isGrounded()) {
            if (!self.getAnimator().getCurrentAnimation().equals(Animator.RUNNING_ANIMATION)) {
                self.getAnimator().setCurrentAnimation(Animator.RUNNING_ANIMATION);
            }
        } else {
            if (!self.getAnimator().getCurrentAnimation().equals(Animator.FLYING_ANIMATION)) {
                self.getAnimator().setCurrentAnimation(Animator.FLYING_ANIMATION);
            }
        }
    }

    public KeyboardController getController() {
        return controller;
    }
}
