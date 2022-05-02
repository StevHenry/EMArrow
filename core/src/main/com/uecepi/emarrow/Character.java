package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.Animator;
import com.uecepi.emarrow.network.Constants;
import com.uecepi.emarrow.network.game.player_action.*;

import java.util.Timer;
import java.util.TimerTask;

public class Character extends Actor {

    //Animation
    private final Animator animator;
    private final HealthBar healthBar;
    private final PlayerInfo info;
    /* Character physics properties */
    // Rigid body used for physics
    private Body body;
    private int jumpLeft = 2;
    private int dashLeft = 1;
    private boolean isGrounded = true;
    private boolean canShoot = true;
    /* Internal properties */
    private long lastShotTime = 0;
    private int life = Constants.MAX_HP;

    public Character(PlayerInfo info) {
        this.animator = new Animator();
        this.createHitBox();
        this.healthBar = new HealthBar(Animator.width, 2, Constants.MAX_HP, body.getPosition());
        this.info = info;
    }

    /**
     * @return the Body controlling the character's physics
     */
    public Body getBody() {
        return body;
    }

    /**
     * Defines the body properties
     */
    private void createHitBox() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(50, 100f));
        this.body = GameEngine.getInstance().getMap().getWorld().createBody(bodyDef);
        body.setUserData(this);
        PolygonShape hitBox = new PolygonShape();
        hitBox.setAsBox(Animator.width / 4f, Animator.height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;
        this.body.createFixture(fixtureDef).setUserData("Character");
        hitBox.setAsBox(4, 2, new Vector2(0f, -13f), 0f);
        fixtureDef.shape = hitBox;
        fixtureDef.isSensor = true;
        this.body.createFixture(fixtureDef).setUserData("GroundHitBox");
        hitBox.dispose();
    }

    /**
     * Shoots a new {@link Projectile}
     */
    public void shoot(Projectile projectile) {
        if (canShoot && System.currentTimeMillis() - lastShotTime >= Constants.FIRE_RATE) {
            GameEngine.getInstance().addProjectile(projectile);
            MusicManager.playSE(MusicManager.SHOT_SE);
            lastShotTime = System.currentTimeMillis();
        }
    }

    /**
     * Updates the body at each frame
     */
    public void update() {
        Vector2 pos = body.getPosition();
        if (pos.x <= -5) {
            setTransform(435, pos.y, 0);
        } else if (pos.x >= 440) {
            setTransform(0, pos.y, 0);
        } else if (pos.y <= -5) {
            setTransform(pos.x, 230, 0);
        } else if (pos.y >= 240) {
            setTransform(pos.x, 10, 0);
            applyLinearImpulse(new Vector2(0, 100000), pos, true);
        }
    }

    /**
     * Called when the character dies
     */
    private void die() {

        setCanShoot(false);
        body.setActive(false);
        //body.getFixtureList().removeIndex(0);
        //GameEngine.getInstance().killBody(body);

        // TODO PLAY DIE ANIM
        // this.animator.setCurrentAnimation(Animator.STANDING_ANIMATION);
    }


    /**
     * Decreases life by the specified value or goes to 0
     * Plays the hurt animation
     * Updates the HealthBar
     * Dies if life is zero
     *
     * @param damage specified damage
     */
    public void damage(int damage) {
        life = life >= damage ? life - damage : 0;
        healthBar.setValue(life);
        animator.setHurt(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> animator.setHurt(false));
            }
        }, 60);
        if (life <= 0)
            die();
    }

    public int getJumpLeft() {
        return jumpLeft;
    }

    public void setJumpLeft(int jumpLeft) {
        this.jumpLeft = jumpLeft;
    }

    public int getDashLeft() {
        return dashLeft;
    }

    public void setDashLeft(int dashLeft) {
        this.dashLeft = dashLeft;
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    /**
     * Method from {@link Body#setTransform(float, float, float)} and packet sending
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new TransformationPacket(GameEngine.getInstance().getSelfPlayer().getUuid(), x, y, angle));
    }

    /**
     * Method from {@link Body#applyLinearImpulse(Vector2, Vector2, boolean)} and packet sending
     */
    public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake) {
        body.applyLinearImpulse(impulse, point, wake);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new LinearImpulsePacket(GameEngine.getInstance().getSelfPlayer().getUuid(), impulse, point, wake));
    }

    /**
     * Method from {@link Body#applyForce(Vector2, Vector2, boolean)} and packet sending
     */
    public void applyForce(Vector2 force, Vector2 point, boolean wake) {
        body.applyForce(force, point, wake);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new ForceAppliedPacket(GameEngine.getInstance().getSelfPlayer().getUuid(), force, point, wake));
    }

    /**
     * Method from {@link Body#setLinearVelocity(Vector2)} and packet sending
     */
    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new LinearVelocityPacket(GameEngine.getInstance().getSelfPlayer().getUuid(), velocity));
    }

    /**
     * Method from {@link Body#applyForceToCenter(float, float, boolean)} and packet sending
     */
    public void applyForceToCenter(float forceX, float forceY, boolean wake) {
        body.applyForceToCenter(forceX, forceY, wake);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new ForceToCenterPacket(GameEngine.getInstance().getSelfPlayer().getUuid(), forceX, forceY, wake));
    }

    /**
     * Method from {@link Animator#setFlippedToLeft(boolean)} and packet sending
     */
    public void setFlippedToLeft(boolean flip) {
        animator.setFlippedToLeft(flip);
        GameEngine.getInstance().getSelfClient().sendTCP(
                new CharacterFlipPacket(GameEngine.getInstance().getSelfPlayer().getUuid(), flip));
    }

    /**
     * @return the {@link PlayerInfo} linked to the Character
     */
    public PlayerInfo getInfo() {
        return info;
    }

}
