package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameEngine {
    private World world;
    private float accumulator = 0;
    private Character player;
    private KeyboardController controller;
    private static GameEngine gameEngine;

    public static GameEngine getInstance(){
        return gameEngine;
    }

    public GameEngine() {
        this.world = new World(new Vector2(0, -150), true);
        this.player = new Character(this);
        this.createGround();
        controller = new KeyboardController();
        Gdx.input.setInputProcessor(controller);
    }

    public static void start(){
        gameEngine = new GameEngine();
    }

    public void createGround(){
// Create our body definition
        BodyDef groundBodyDef = new BodyDef();
// Set its world position
        groundBodyDef.position.set(new Vector2(0, 10.0f));


        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(100.0f, 10.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }

    public void processInput() {
        if (controller.left) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x-1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            player.getBody().applyLinearImpulse(new Vector2(-5, 0), player.getBody().getPosition(), true);
        }
        if (controller.right) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x+1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            player.getBody().applyLinearImpulse(new Vector2(5, 0), player.getBody().getPosition(), true);

        }
        if (controller.jump){
            //&& player.isGrounded()) {
            player.getBody().applyLinearImpulse(new Vector2(0, 100), player.getBody().getPosition(), true);
        }

        if (controller.dash) {
            player.getBody().applyLinearImpulse(new Vector2(player.getBody().getLinearVelocity().x * 10, player.getBody().getLinearVelocity().y * 10), player.getBody().getPosition(), true);
        }
    }

    public World getWorld() {
        return world;
    }
    public Character getPlayer1() {
        return player;
    }
}