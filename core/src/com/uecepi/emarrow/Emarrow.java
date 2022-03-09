package com.uecepi.emarrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.map.Map;

public class Emarrow extends Game {
    private static final float SCALE = 2.0f;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private SpriteBatch batch;
    private GameEngine gameEngine;

    @Override
    public void create () {
        batch = new SpriteBatch();
        box2DDebugRenderer = new Box2DDebugRenderer();
        gameEngine = new GameEngine();
        world = gameEngine.getPlayer1().getBody().getWorld();
    }

    @Override
    public void render () {
        Box2D.init();
        ScreenUtils.clear(0, 0.6f, 0.8f, 1);
        update();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        batch.begin();
        gameEngine.getMap().render();
        batch.draw(gameEngine.getPlayer1().getTexture(), gameEngine.getPlayer1().getBody().getPosition().x - (gameEngine.getPlayer1().getTexture().getWidth() / 2), gameEngine.getPlayer1().getBody().getPosition().y - (gameEngine.getPlayer1().getTexture().getHeight() / 2));
        batch.end();
        box2DDebugRenderer.render(world, gameEngine.getMap().getCamera().combined);
    }

    private void update() {
        gameEngine.processInput();
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        //cameraUpdate();
        batch.setProjectionMatrix(gameEngine.getMap().getCamera().combined);
    }

    private void cameraUpdate() {
        Vector3 position = gameEngine.getMap().getCamera().position;
        position.x = gameEngine.getPlayer1().getBody().getPosition().x ;
        position.y = gameEngine.getPlayer1().getBody().getPosition().y ;
        gameEngine.getMap().getCamera().position.set(position);
        gameEngine.getMap().getCamera().update();
    }

    @Override
    public void resize(int width, int height) {
        gameEngine.getMap().getCamera().setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        gameEngine.getPlayer1().getTexture().dispose();
        batch.dispose();
        world.dispose();
    }
}