package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uecepi.emarrow.display.Animator;

import java.util.ArrayList;
import java.util.List;

public class Character extends Actor {
    private BodyDef bodyDef;
    private Body body;
    private Animator animator;
    private Body detectBox;
    private float speed;
    private String name;

    private long lastShotTime = 0;
    private float fireRate = 700f;
    private List<Projectile> projectilesShooted;
    private HealthBar healthBar;
    private int jumpLeft = 2;
    private boolean isGrounded=true;

    public Character(String nb){
        this.animator = new Animator(nb); //TODO mettre en parametre pour pouvoir chosir skin
        this.bodyDef = new BodyDef();
        this.speed = 25f;
        this.projectilesShooted = new ArrayList<>();
        this.name = "Player " + nb;
        this.createHitBox();
        this.healthBar = new HealthBar(animator.width,2,100,body.getPosition());
    }

    public Body getBody() {
        return body;
    }

    public void createHitBox(){
        // First we create a body definition
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(new Vector2(50, 100f));

        // Create our body in the world using our body definition
        this.body = GameEngine.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(this);


        // Create a circle shape and set its radius to 6
        PolygonShape hitBox = new PolygonShape();
        //hitBox.setAsBox(4.0f, 7.0f);
        hitBox.setAsBox(animator.width/4, animator.height/2);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;



        // Create our fixture and attach it to the body

        this.body.createFixture(fixtureDef).setUserData("Player");

        hitBox.setAsBox(4, 2, new Vector2(0f, -13f),0f);
        fixtureDef.shape = hitBox;
        fixtureDef.isSensor = true;

        this.body.createFixture(fixtureDef).setUserData("GroundHitBox");

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        hitBox.dispose();
    }

    public void shoot(){
        if (System.currentTimeMillis() - lastShotTime >= fireRate)
        {
            //TODO
            projectilesShooted.add(new Projectile(this));
            lastShotTime = System.currentTimeMillis();
        }
    }

    public void die(){
        System.out.println("DIED");
        GameEngine.getInstance().getDeadBodies().add(this.body);

        // TODO PLAY DIE ANIM
        // this.animator.setCurrentAnimation(Animator.STANDING_ANIMATION);
        if (GameEngine.getInstance().isRoundDone())
            System.out.println("FINISH ROUND");
            GameEngine.getInstance().finishRound();
    }

    public int getJumpLeft() {
        return jumpLeft;
    }

    public void setJumpLeft(int jumpLeft) {
        this.jumpLeft = jumpLeft;
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }

    public float getSpeed() {
        return speed;
    }
    public List<Projectile> getProjectilesShooted() {
        return projectilesShooted;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }



    @Override
    public String toString() {
        return "Character{" +
                ", bodyDef=" + bodyDef +
                ", body=" + body +
                ", speed=" + speed +
                ", lastShotTime=" + lastShotTime +
                ", fireRate=" + fireRate +
                ", projectilesShooted=" + projectilesShooted +
                ", healthBar=" + healthBar +
                '}';
    }

    public Animator getAnimator() {
        return animator;
    }
}
