package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.bullet.Bullet;

import static java.lang.Math.abs;

public class Projectile {
    float speed;
    float damage;
    private BodyDef bodyDef;
    private Body body;
    private Sprite texture;
    private Character shooter;

    public Projectile(Character shooter){
        this.texture = new Sprite(new Texture(Gdx.files.internal("images/char/arrow.png")));
        this.bodyDef = new BodyDef();
        this.shooter = shooter;
        if (shooter.getAnimator().isFlippedToLeft())
            this.speed = 1E17f;
        else
            this.speed = -1E17f;
        this.createHitBox();
        this.damage = 25f;
    }

    public Body getBody() {
        return body;
    }

    public void createHitBox(){
        // First we create a body definition
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(shooter.getBody().getPosition());

        // Create our body in the world using our body definition
        this.body = GameEngine.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(this);
        // Create a circle shape and set its radius to 6
        PolygonShape hitBox = new PolygonShape();
        //hitBox.setAsBox(4.0f, 7.0f);
        hitBox.setAsBox(texture.getRegionWidth()/2, texture.getRegionHeight()/2);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;
        fixtureDef.isSensor = true;

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData("Projectile");
        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        hitBox.dispose();
        body.applyLinearImpulse(new Vector2(-speed, 0), body.getPosition(), true);

   }

    public void update() {
        //body.applyLinearImpulse(new Vector2(-speed/(abs(speed))*5, 0), body.getPosition(), true);
        //velocity.scl(1 - (0.98f * deltaTime));
        // Linear dampening, otherwise the ball will keep going at the original velocity forever
    }
    public TextureRegion getTexture() {
        return texture;
    }

    public float getSpeed() {
        return speed;
    }

    public Character getShooter() {
        return shooter;
    }

    public float getRotation(){
        return body.getLinearVelocity().angleDeg() + 50;
    }
}
