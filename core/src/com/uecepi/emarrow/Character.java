package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Character {
    private int life;
    private Weapon weapon;
    private BodyDef bodyDef;
    private GameEngine gameEngine;
    private Body body;

    public Character(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.bodyDef = new BodyDef();
        this.createHitBox();
    }

    public Body getBody() {
        return body;
    }

    public void createHitBox(){
        // First we create a body definition
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(new Vector2(0, 100f));

// Create our body in the world using our body definition
        this.body = this.gameEngine.getWorld().createBody(bodyDef);

// Create a circle shape and set its radius to 6
        PolygonShape hitBox = new PolygonShape();
        hitBox.setAsBox(50.0f, 50.0f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;


// Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        hitBox.dispose();
    }
}
