package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class Character extends Actor {
    private int life;
    private BodyDef bodyDef;
    private Body body;
    private Texture texture;
    private float speed;

    private long lastShotTime = 0;
    private float fireRate = 1000f;
    private List<Projectile> projectilesShooted;
    private HealthBar healthBar;

    public Character(Texture texture){
        this.texture = texture; //TODO mettre en parametre pour pouvoir chosir skin
        this.bodyDef = new BodyDef();
        this.speed = 25f;
        this.projectilesShooted = new ArrayList<>();
        this.createHitBox();
        this.healthBar = new HealthBar(texture.getWidth(),2,100,body.getPosition());
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
        hitBox.setAsBox(texture.getWidth()/4, texture.getHeight()/2);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;



        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData("Player");
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
    public Texture getTexture() {
        return texture;
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
                "life=" + life +
                ", bodyDef=" + bodyDef +
                ", body=" + body +
                ", texture=" + texture +
                ", speed=" + speed +
                ", lastShotTime=" + lastShotTime +
                ", fireRate=" + fireRate +
                ", projectilesShooted=" + projectilesShooted +
                ", healthBar=" + healthBar +
                '}';
    }
}
