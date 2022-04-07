package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.Animator;

import com.uecepi.emarrow.display.menus.MainMenu;
import com.uecepi.emarrow.map.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Map map;
    private World world;
    private float accumulator = 0;
    private static List<Character> players;
    private List<Body> deadBodies;
    private Label gameFinished;

    private static GameEngine gameEngine = new GameEngine();
    private static final int DASH_IMPULSE = 42000;

    public static GameEngine getInstance(){
        return gameEngine;
    }

    public GameEngine() {
        map = new Map("map1");
        this.world = new World(new Vector2(0, -150), true);
        players = new ArrayList<>();
        deadBodies = new ArrayList<>();

        this.createGround();
        Gdx.input.setInputProcessor(Emarrow.getInstance().getController());
    }

    public static void start() {
        gameEngine = new GameEngine();
        gameEngine.players.add(new Character("1")); //TODO mettre en parametre pour pouvoir chosir skin));
        gameEngine.players.add(new Character("2"));
    }

    public boolean isRoundDone(){
        int playersAlive =0;
        for (Character character : players){
            if (character.getHealthBar().getValue()>0)
                playersAlive++;
        }
        return playersAlive>1;
    }

    public void finishRound(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Emarrow.getInstance().setScreen(new MainMenu());
                    }
                });
            }
        },3000);
    }

    public void createGround() {
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getTiledMap().getLayers().get(2);  // assuming the layer at index on contains tiles
        for (int i=0; i<layer.getWidth();i++){
            for (int j=0; j< layer.getHeight();j++){
                if (layer.getCell(i,j)!=null)
                {
                    // Create our body definition
                    BodyDef groundBodyDef = new BodyDef();
                    // Set its world position
                    groundBodyDef.position.set(i*layer.getTileWidth() + layer.getTileWidth()*.5f,j*layer.getTileHeight()+ layer.getTileHeight()*.5f);

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

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type=BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(187,230);

        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        FixtureDef groundFixture = new FixtureDef();

        groundBox.setAsBox(72f,10f,new Vector2(-67f,20f),0f);
        groundFixture.shape=groundBox;

        groundBody.createFixture(groundFixture).setUserData("Ground");

        groundBox.setAsBox(72f,10f,new Vector2(141f,20f),0f);
        groundFixture.shape=groundBox;

        groundBody.createFixture(groundFixture).setUserData("Ground");

    }


    public void processInput() {//TODO CHANGER players.get(0) EN ACTIVE PLAYER (CELUI QUI JOUE sur le pc)
        if (Emarrow.getInstance().getController().left) {
            players.get(0).getBody().applyForce(new Vector2(-200, 0), players.get(0).getBody().getPosition(), true);
            players.get(0).getAnimator().setFlippedToLeft(true);
            if (players.get(0).isGrounded()) {
                if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.RUNNING_ANIMATION)) {
                    players.get(0).getAnimator().setCurrentAnimation(Animator.RUNNING_ANIMATION);
                }
            } else {
                if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.FLYING_ANIMATION)) {
                    players.get(0).getAnimator().setCurrentAnimation(Animator.FLYING_ANIMATION);
                }
            }

        } else if (Emarrow.getInstance().getController().right) {
            players.get(0).getBody().applyForce(new Vector2(200, 0), players.get(0).getBody().getPosition(), true);
            players.get(0).getAnimator().setFlippedToLeft(false);
            if (players.get(0).isGrounded()) {
                if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.RUNNING_ANIMATION)) {
                    players.get(0).getAnimator().setCurrentAnimation(Animator.RUNNING_ANIMATION);
                }
            } else {
                if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.FLYING_ANIMATION)) {
                    players.get(0).getAnimator().setCurrentAnimation(Animator.FLYING_ANIMATION);
                }
            }

        } else {
            if (players.get(0).getBody().getLinearVelocity().x < -10) {
                players.get(0).getBody().applyForce(new Vector2(150,players.get(0).getBody().getLinearVelocity().y),players.get(0).getBody().getPosition(),true);
            } else if (players.get(0).getBody().getLinearVelocity().x > 10) {
                players.get(0).getBody().applyForce(new Vector2(-150,players.get(0).getBody().getLinearVelocity().y),players.get(0).getBody().getPosition(),true);
            } else {
                players.get(0).getBody().setLinearVelocity(0, players.get(0).getBody().getLinearVelocity().y);
            }

            if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.STANDING_ANIMATION)) {
                players.get(0).getAnimator().setCurrentAnimation(Animator.STANDING_ANIMATION);
            } else if (!players.get(0).getAnimator().getCurrentAnimation().equals(Animator.FLYING_ANIMATION)) {
                players.get(0).getAnimator().setCurrentAnimation(Animator.FLYING_ANIMATION);            // Stop moving in the Y direction
            }
        }

        if (Emarrow.getInstance().getController().down) {
            players.get(0).getBody().applyForceToCenter(0, -500f, true);
        }

        if (Emarrow.getInstance().getController().jump) {
            if (players.get(0).getJumpLeft() > 0) {
                MusicManager.playSE(MusicManager.JUMP_SE);
                players.get(0).getAnimator().setCurrentAnimation(Animator.JUMPING_ANIMATION);
                players.get(0).setGrounded(false);
                players.get(0).setJumpLeft(players.get(0).getJumpLeft() - 1);
                //players.get(0).getBody().applyLinearImpulse(new Vector2(0, 150), players.get(0).getBody().getPosition(), true);
                players.get(0).getBody().applyForceToCenter(0, 8000f, true);
            }
            Emarrow.getInstance().getController().jump = false;
        }

        if (Emarrow.getInstance().getController().dash) {
            if (players.get(0).getDashLeft() > 0) {
                MusicManager.playSE(MusicManager.DASH_SE);
                players.get(0).setGrounded(false);
                players.get(0).setDashLeft(players.get(0).getDashLeft() - 1);

                Body body = players.get(0).getBody();
                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.input.getY();
                double projectileX = body.getPosition().x*1740/445;
                double projectileY = 950 - body.getPosition().y*1740/445;
                double norm = Math.sqrt((mouseX - projectileX)*(mouseX - projectileX) + (mouseY - projectileY)*(mouseY - projectileY) );
                Vector2 dashDirection = new Vector2((float) ((mouseX - projectileX)/norm), (float) ( (mouseY - projectileY)/norm));

                body.applyLinearImpulse(new Vector2(DASH_IMPULSE * dashDirection.x, -DASH_IMPULSE * dashDirection.y), body.getPosition(), true);
            }
        }

        if (Emarrow.getInstance().getController().shoot){
            if (players.get(0).isGrounded())
                players.get(0).getAnimator().setCurrentAnimation(Animator.STANDING_SHOT_ANIMATION);
            else
                players.get(0).getAnimator().setCurrentAnimation(Animator.FLYING_SHOT_ANIMATION);
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

    public List<Body> getDeadBodies() {
        return deadBodies;
    }

    public Label getGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(Label gameFinished) {
        this.gameFinished = gameFinished;
    }
}
