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
import com.uecepi.emarrow.audio.MusicManager;

public class Projectile {
    private float speed;
    public float damage;
    private BodyDef bodyDef;
    private Body body;
    private Sprite texture;
    private Character shooter;
    private Vector2 projectileDirection;
    private float rotation;


    public Projectile(Character shooter){
        this.texture = new Sprite(new Texture(Gdx.files.internal("images/char/arrow.png")));
        this.bodyDef = new BodyDef();
        this.shooter = shooter;
        this.speed = -6;
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
        hitBox.setAsBox(texture.getRegionWidth()/2f, texture.getRegionHeight()/2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;
        fixtureDef.isSensor = true;

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData("Projectile");
        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        hitBox.dispose();
        this.body.setGravityScale(0.1f);
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        double projectileX = body.getPosition().x*1740/445;
        double projectileY = 950 - body.getPosition().y*1740/445;
        double norm = Math.sqrt((mouseX - projectileX)*(mouseX - projectileX) + (mouseY - projectileY)*(mouseY - projectileY) );
        projectileDirection = new Vector2((float) ((mouseX - projectileX)/norm), (float) ( (mouseY - projectileY)/norm));
        //body.applyLinearImpulse(new Vector2(  (-speed  * projectileDirection.x), -(-speed * projectileDirection.y)), body.getPosition(), true);
        rotation = 45 - projectileDirection.angleDeg();
        MusicManager.playSE(MusicManager.SHOT_SE);
    }

    public void update() {
        body.setTransform(new Vector2(  (-speed * projectileDirection.x) + body.getPosition().x , speed *projectileDirection.y + body.getPosition().y), 0f);
        Vector2 pos = body.getPosition();
        if(pos.x <= -5) {
            body.setTransform(435, body.getPosition().y,0);
        } else if(pos.x >= 440) {
            body.setTransform(0, body.getPosition().y,0);
        } else if(pos.y <= -5) {
            body.setTransform(body.getPosition().x, 230,0);
        } else if(pos.y >= 240) {
            body.setTransform(body.getPosition().x, 10,0);
            body.applyLinearImpulse(new Vector2(0,100000), body.getPosition(), true);

        }
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
        return rotation;

    }
}
