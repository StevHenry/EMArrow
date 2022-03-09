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

public class Emarrow extends Game {
    private static final float SCALE = 2.0f;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private OrthographicCamera orthographicCamera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private SpriteBatch batch;
    private Texture texture;
    private GameEngine gameEngine;


    @Override
    public void create () {
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        batch = new SpriteBatch();
        texture = new Texture("sprite1.png");
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
        batch.draw(texture, gameEngine.getPlayer1().getBody().getPosition().x - (texture.getWidth() / 2), gameEngine.getPlayer1().getBody().getPosition().y - (texture.getHeight() / 2));
        batch.end();
        box2DDebugRenderer.render(world, orthographicCamera.combined);
    }

    private void update() {
        gameEngine.processInput();
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        cameraUpdate();
        batch.setProjectionMatrix(orthographicCamera.combined);
    }

    private void cameraUpdate() {
        Vector3 position = orthographicCamera.position;
        position.x = gameEngine.getPlayer1().getBody().getPosition().x ;
        position.y = gameEngine.getPlayer1().getBody().getPosition().y ;
        orthographicCamera.position.set(position);
        orthographicCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        texture.dispose();
        batch.dispose();
        world.dispose();
    }
}