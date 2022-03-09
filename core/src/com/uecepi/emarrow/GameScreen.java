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
    private OrthographicCamera orthographicCamera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private SpriteBatch batch;
    private Texture texture;

    public GameScreen(){
        super();
        create();
    }

    private void create() {
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        GameEngine.start();
        batch = new SpriteBatch();
        texture = new Texture("images/char/1/1.png");
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
        batch.draw(texture, GameEngine.getInstance().getPlayer1().getBody().getPosition().x - (texture.getWidth() / 2), GameEngine.getInstance().getPlayer1().getBody().getPosition().y - (texture.getHeight() / 2));
        batch.end();
        box2DDebugRenderer.render(world, orthographicCamera.combined);

    }

    private void update() {
        GameEngine.getInstance().processInput();
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        cameraUpdate();
        batch.setProjectionMatrix(orthographicCamera.combined);
    }

    private void cameraUpdate() {
        Vector3 position = orthographicCamera.position;
        position.x = GameEngine.getInstance().getPlayer1().getBody().getPosition().x ;
        position.y = GameEngine.getInstance().getPlayer1().getBody().getPosition().y ;
        orthographicCamera.position.set(position);
        orthographicCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, width / SCALE, height / SCALE);

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
        texture.dispose();
        batch.dispose();
        world.dispose();
    }
}
