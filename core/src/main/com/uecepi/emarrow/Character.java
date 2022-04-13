package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.Animator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Character extends Actor {

    private final static float FIRE_RATE = 100f;
    private final static float SPEED = 25f;
    public static final int MAX_HP = 100;

    //Animation
    private final Animator animator;
    private final List<Projectile> projectilesShot;
    private final HealthBar healthBar;
    /* Character physics properties */
    // Rigid body used for physics
    private Body body;
    private int jumpLeft = 2;
    private int dashLeft = 1;
    private boolean isGrounded = true;
    private boolean canShoot = true;

    /* Internal properties */
    private long lastShotTime = 0;
    private int life = MAX_HP;

    public Character(int skinNumber) {
        this.animator = new Animator(skinNumber); //TODO mettre en parametre pour pouvoir chosir skin
        this.projectilesShot = new ArrayList<>();
        this.createHitBox();
        this.healthBar = new HealthBar(Animator.width, 2, MAX_HP, body.getPosition());
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
    public void shoot() {
        if (canShoot && System.currentTimeMillis() - lastShotTime >= FIRE_RATE) {
            projectilesShot.add(new Projectile(this));
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
            body.setTransform(435, pos.y, 0);
        } else if (pos.x >= 440) {
            body.setTransform(0, pos.y, 0);
        } else if (pos.y <= -5) {
            body.setTransform(pos.x, 230, 0);
        } else if (pos.y >= 240) {
            body.setTransform(pos.x, 10, 0);
            body.applyLinearImpulse(new Vector2(0, 100000), pos, true);
        }
    }

    /**
     * Called when the character dies
     */
    private void die() {
        for (Character character : GameEngine.getInstance().getCharacters()) {
            character.setCanShoot(false);
        }

        // TODO PLAY DIE ANIM
        // this.animator.setCurrentAnimation(Animator.STANDING_ANIMATION);
    }


    /**
     * Decreases life by the specified value or goes to 0
     * Plays the hurt animation
     * Updates the HealthBar
     * Dies if life is zero
     * @param damage specified damage
     */
    public void damage(int damage) {
        life = life >= damage ? life -= damage : 0;
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

    public float getSpeed() {
        return SPEED;
    }

    public List<Projectile> getProjectilesShot() {
        return projectilesShot;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public int getLife() {
        return life;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }
}
