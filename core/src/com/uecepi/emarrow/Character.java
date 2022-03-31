package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private int life;
    private BodyDef bodyDef;
    private Body body;
    private Body detectBox;
    private Texture texture;
    private float speed;
    private long lastShotTime = 0;
    private float fireRate = 1000f;
    private List<Projectile> projectilesShooted;
    private HealthBar healthBar;
    private int jumpLeft = 2;

    public Character(Texture texture){
        this.texture = texture; //TODO mettre en parametre pour pouvoir chosir skin
        this.bodyDef = new BodyDef();
        this.speed = 25f;
        this.projectilesShooted = new ArrayList<>();
        this.createHitBox();
        this.healthBar = new HealthBar(100,body.getPosition());
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

        // Create a circle shape and set its radius to 6
        PolygonShape hitBox = new PolygonShape();
        //hitBox.setAsBox(4.0f, 7.0f);
        hitBox.setAsBox(texture.getWidth()/4, texture.getHeight()/2);

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
        System.out.println(System.currentTimeMillis()- lastShotTime);
        System.out.println(fireRate);
        if (System.currentTimeMillis() - lastShotTime >= fireRate)
        {
            //TODO
            projectilesShooted.add(new Projectile(this));
            lastShotTime = System.currentTimeMillis();
        }
    }
    
    public Texture getTexture() {
        return texture;
    }

    public int getJumpLeft() {
        return jumpLeft;
    }

    public void setJumpLeft(int jumpLeft) {
        this.jumpLeft = jumpLeft;
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
}
