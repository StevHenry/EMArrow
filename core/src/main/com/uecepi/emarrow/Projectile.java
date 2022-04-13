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

public class Projectile {

    private final float speed;
    private final int damage;
    private final Sprite texture;
    private final Character shooter;
    private Body body;

    private Vector2 initialDirection;

    /**
     * @param shooter {@link Character} who launches the projectile
     */
    public Projectile(Character shooter) {
        this.texture = new Sprite(new Texture(Gdx.files.internal("images/char/arrow.png")));
        this.shooter = shooter;
        this.speed = 6;
        this.damage = 25;
        this.createHitBox();
        this.calculateDirection();
        body.setBullet(true);

    }

    /**
     * Constructor used when a packet needs to recreate the projectile launched externally
     * @param shooter  {@link Character} who launches the projectile
     * @param direction normalized vector of the initial direction of the projectile
     */
    public Projectile(Character shooter, Vector2 direction) {
        this(shooter);
        this.initialDirection = direction;
    }

    /**
     * Creates the physical object
     */
    public void createHitBox() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(shooter.getBody().getPosition());

        this.body = GameEngine.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(this);

        PolygonShape hitBox = new PolygonShape();
        hitBox.setAsBox(texture.getRegionWidth() / 2f, texture.getRegionHeight() / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("Projectile");
        hitBox.dispose();

        body.setGravityScale(0.1f);
        //body.applyLinearImpulse(new Vector2(  (-speed  * projectileDirection.x), -(-speed * projectileDirection.y)), body.getPosition(), true);
    }

    /**
     * Calculates the initial direction for the projectile
     */
    private void calculateDirection() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        float projectileX = body.getPosition().x * 1740 / 445;
        float projectileY = 950 - body.getPosition().y * 1740 / 445;
        initialDirection = new Vector2(projectileX - mouseX, projectileY - mouseY);
        initialDirection.nor();
    }

    /**
     * Updates the projectile movement
     */
    public void update() {
        body.setTransform(new Vector2((-speed * initialDirection.x) + body.getPosition().x, speed * initialDirection.y + body.getPosition().y), 0f);
        Vector2 pos = body.getPosition();
        if (pos.x <= -5) {
            body.setTransform(435, body.getPosition().y, 0);
        } else if (pos.x >= 440) {
            body.setTransform(0, body.getPosition().y, 0);
        } else if (pos.y <= -5) {
            body.setTransform(body.getPosition().x, 230, 0);
        } else if (pos.y >= 240) {
            body.setTransform(body.getPosition().x, 10, 0);
            body.applyLinearImpulse(new Vector2(0, 100000), body.getPosition(), true);
        }
    }

    /**
     * @return the projectile texture
     */
    public TextureRegion getTexture() {
        return texture;
    }

    /**
     * @return the Character who shot the projectile
     */
    public Character getShooter() {
        return shooter;
    }

    /**
     * @return the rotation in order to rotate the image for the rendering
     */
    public float getRotation() {
        return 225 - initialDirection.angleDeg();
    }

    /**
     * @return a normalized Vector2 of the projectile initial direction
     */
    public Vector2 getInitialDirection() {
        return initialDirection;
    }

    /**
     * @return the life amount removed after a hit
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return the physical object controlling the movement
     */
    public Body getBody() {
        return body;
    }
}
