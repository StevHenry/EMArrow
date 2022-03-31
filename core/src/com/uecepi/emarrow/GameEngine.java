package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.uecepi.emarrow.map.Map;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Map map;
    private World world;
    private float accumulator = 0;
    private static List<Character> players;
    private static GameEngine gameEngine = new GameEngine();

    public static GameEngine getInstance(){
        return gameEngine;
    }

    public GameEngine() {
        map = new Map("map1");
        this.world = new World(new Vector2(0, -150), true);
        this.createGround();
        Gdx.input.setInputProcessor(Emarrow.getInstance().getController());
    }

    public static void start() {
        gameEngine = new GameEngine();
        players = new ArrayList<>();
        players.add(new Character(new Texture("images/char/1/20_1.png"))); //TODO mettre en parametre pour pouvoir chosir skin));
        players.add(new Character(new Texture("images/char/2/2.png")));
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
                    FixtureDef groundFixture = new FixtureDef();
                    groundFixture.shape=groundBox;
                    // Create a fixture from our polygon shape and add it to our ground body
                    groundBody.createFixture(groundFixture).setUserData("Ground");
                    // Clean up after ourselves
                    groundBox.dispose();
                }
            }
        }
    }

    public void processInput() {//TODO CHANGER players.get(0) EN ACTIVE PLAYER (CELUI QUI JOUE sur le pc)
        if (Emarrow.getInstance().getController().left) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x-1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            //players.get(0).getBody().applyForceToCenter(new Vector2(-50, 0),true);

            players.get(0).getBody().applyLinearImpulse(new Vector2(-players.get(0).getSpeed(), 0), players.get(0).getBody().getPosition(), true);
        } else if (Emarrow.getInstance().getController().right) {
            //TODO Ameliorer la facon de déplacer
            //body.setTransform(body.getTransform().getPosition().x+1,body.getTransform().getPosition().y,body.getTransform().getRotation());
            //players.get(0).getBody().applyForceToCenter(new Vector2(50, 0),true);
            players.get(0).getBody().applyLinearImpulse(new Vector2(players.get(0).getSpeed(), 0), players.get(0).getBody().getPosition(), true);
        } else {
            // Stop moving in the X direction
            players.get(0).getBody().setLinearVelocity(0, players.get(0).getBody().getLinearVelocity().y);
        }
        if (Emarrow.getInstance().getController().jump) { //TODO améliorer la facon de sauter : quand on se deplace lateralement en l'air
            if (players.get(0).getJumpLeft() > 0) {
                players.get(0).setJumpLeft(players.get(0).getJumpLeft() - 1);
                //players.get(0).getBody().applyLinearImpulse(new Vector2(0, 150), players.get(0).getBody().getPosition(), true);
                players.get(0).getBody().applyForceToCenter(0, 8000f, true);
            }
            Emarrow.getInstance().getController().jump = false;
        }

        if (Emarrow.getInstance().getController().dash) {
            if (Emarrow.getInstance().getController().left && Emarrow.getInstance().getController().up) {
                players.get(0).getBody().applyLinearImpulse(new Vector2(-8000, 8000), players.get(0).getBody().getPosition(), true);
            }
        }

        if (Emarrow.getInstance().getController().shoot) {
            players.get(0).shoot();
        }

    }

    public World getWorld() {
        return world;
    }

    public Map getMap() {
        return map;
    }
    public List<Character> getPlayers() {
        return players;
    }


}