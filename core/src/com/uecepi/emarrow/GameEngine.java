package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.uecepi.emarrow.map.Map;

public class GameEngine {
    private Map map;
    private World world;
    private float accumulator = 0;
    private Character player;
    private KeyboardController controller;
    private static GameEngine gameEngine;

    public static GameEngine getInstance() {
        return gameEngine;
    }

    public GameEngine() {
        map = new Map("map1");
        this.world = new World(new Vector2(0, -150), true);
        this.player = new Character(this);
        this.createGround();
        controller = new KeyboardController();
        Gdx.input.setInputProcessor(controller);
    }

    public static void start() {
        gameEngine = new GameEngine();
    }

    public void createGround() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(2);  // assuming the layer at index on contains tiles
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                if (layer.getCell(i, j) != null) {
                    // Create our body definition
                    BodyDef groundBodyDef = new BodyDef();
                    // Set its world position
                    groundBodyDef.position.set(i * layer.getTileWidth() + layer.getTileWidth() * .5f, j * layer.getTileHeight() + layer.getTileHeight() * .5f);


                    Body groundBody = world.createBody(groundBodyDef);

                    // Create a polygon shape
                    PolygonShape groundBox = new PolygonShape();
                    // (setAsBox takes half-width and half-height as arguments)
                    groundBox.setAsBox(layer.getTileWidth() / 2f, layer.getTileHeight() / 2f);
                    // Create a fixture from our polygon shape and add it to our ground body
                    groundBody.createFixture(groundBox, 0.0f);
                    // Clean up after ourselves
                    groundBox.dispose();
                }
            }
        }
    }

    public void processInput() {
        if (controller.left) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x-1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            player.getBody().applyLinearImpulse(new Vector2(-player.getSpeed(), 0), player.getBody().getPosition(), true);
        }
        else if (controller.right) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x+1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            player.getBody().applyLinearImpulse(new Vector2(player.getSpeed(), 0), player.getBody().getPosition(), true);
        }
        else {
            // Stop moving in the Y direction
            player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
        }
        if (controller.jump){ //TODO améliorer la facon de sauter : quand on se deplace lateralement en l'air
            //&& player.isGrounded()) {
            player.getBody().applyLinearImpulse(new Vector2(0, 100), player.getBody().getPosition(), true);
            //player.getBody().applyForceToCenter(0, 8000f, true);

        }

        if (controller.dash) {
            player.getBody().applyLinearImpulse(new Vector2(player.getBody().getLinearVelocity().x * 10, player.getBody().getLinearVelocity().y * 10), player.getBody().getPosition(), true);
        }
    }

    public World getWorld() {
        return world;
    }

    public Map getMap() {
        return map;
    }

    public Character getPlayer1() {
        return player;
    }
}