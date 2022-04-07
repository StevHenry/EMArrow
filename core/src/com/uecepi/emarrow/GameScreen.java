package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uecepi.emarrow.display.ScreenMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        world = GameEngine.getInstance().getWorld();
        world.setContactListener(new ListenerClass());
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
        for (Character player : GameEngine.getInstance().getPlayers()) {
            player.getAnimator().render(batch,(int) player.getBody().getPosition().x - (player.getAnimator().width / 2), (int) player.getBody().getPosition().y - (player.getAnimator().height / 2));
            player.getHealthBar().draw(batch,1);
        }
        drawProjectiles();
        batch.end();
        box2DDebugRenderer.render(world, GameEngine.getInstance().getMap().getCamera().combined);
    }

    private void update() {
        GameEngine.getInstance().processInput();
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        destroyDeadBodies();
        batch.setProjectionMatrix(GameEngine.getInstance().getMap().getCamera().combined);
        for (Character player : GameEngine.getInstance().getPlayers()) {
            player.getHealthBar().setPosition(player.getBody().getPosition().x-player.getAnimator().width/2,player.getBody().getPosition().y+player.getAnimator().height/2);
            player.getHealthBar().updateVisualValue();
            for (Projectile projectile : player.getProjectilesShooted()){
                projectile.update();
            }
        }
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
        for (Character player : GameEngine.getInstance().getPlayers()) {
            //player.getAnimator().
        }
        batch.dispose();
        world.dispose();
    }

    private void drawProjectiles() {
        for (Character player : GameEngine.getInstance().getPlayers()) {
            for (Projectile projectile : player.getProjectilesShooted()){
                batch.draw(projectile.getTexture(), projectile.getBody().getPosition().x - (projectile.getTexture().getRegionWidth() / 2), projectile.getBody().getPosition().y - (projectile.getTexture().getRegionHeight() / 2), projectile.getTexture().getRegionWidth() / 2, projectile.getTexture().getRegionHeight() / 2, projectile.getTexture().getRegionWidth(), projectile.getTexture().getRegionHeight(), 1, 1, projectile.getRotation(), true);
            }
        }
    }

    private void destroyDeadBodies(){
        for (Body deadBody : GameEngine.getInstance().getDeadBodies()){
            //world.destroyBody(deadBody);
            deadBody.setActive(false);

        }
        GameEngine.getInstance().getDeadBodies().clear();
    }

}