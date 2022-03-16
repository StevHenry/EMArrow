package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uecepi.emarrow.display.ScreenMenu;

public class GameScreen extends ScreenMenu {
    private static final float SCALE = 2.0f;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private SpriteBatch batch;

    public GameScreen() {
        super();
        create();
    }

    private void create() {
        GameEngine.start();
        batch = new SpriteBatch();
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = GameEngine.getInstance().getPlayer1().getBody().getWorld();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Box2D.init();
        ScreenUtils.clear(0, 0.6f, 0.8f, 1);
        update();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        batch.begin();
        GameEngine.getInstance().getMap().render();
        batch.draw(GameEngine.getInstance().getPlayer1().getTexture(), GameEngine.getInstance().getPlayer1().getBody().getPosition().x - (GameEngine.getInstance().getPlayer1().getTexture().getWidth() / 2), GameEngine.getInstance().getPlayer1().getBody().getPosition().y - (GameEngine.getInstance().getPlayer1().getTexture().getHeight() / 2));
        batch.end();
        box2DDebugRenderer.render(world, GameEngine.getInstance().getMap().getCamera().combined);

    }

    private void update() {
        GameEngine.getInstance().processInput();
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        //cameraUpdate();
        batch.setProjectionMatrix(GameEngine.getInstance().getMap().getCamera().combined);
    }

    private void cameraUpdate() {
        Vector3 position = GameEngine.getInstance().getMap().getCamera().position;
        position.x = GameEngine.getInstance().getPlayer1().getBody().getPosition().x;
        position.y = GameEngine.getInstance().getPlayer1().getBody().getPosition().y;
        GameEngine.getInstance().getMap().getCamera().position.set(position);
        GameEngine.getInstance().getMap().getCamera().update();
    }

    @Override
    public void resize(int width, int height) {
        GameEngine.getInstance().getMap().getCamera().setToOrtho(false, width / SCALE, height / SCALE);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        GameEngine.getInstance().getPlayer1().getTexture().dispose();
        batch.dispose();
        world.dispose();
    }
}