package com.uecepi.emarrow.display;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.*;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.menus.ScreenMenu;
import com.uecepi.emarrow.map.Map;

import java.util.*;

public class GameScreen extends ScreenMenu {

    public static final float SCALE = 2.0f;
    public static final float TIME_STEP = 1 / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    private final SpriteBatch batch;
    private final Box2DDebugRenderer debugRenderer;

    private long startingTimeGameOver = 0;

    private Label titleLabel, subtitleLabel;
    private HashMap<Character, Label> names;

    public GameScreen() {
        super();
        batch = new SpriteBatch();
        names = new HashMap<>();
        debugRenderer = new Box2DDebugRenderer();
        GameEngine gameEngine = GameEngine.getInstance();
        //gameEngine.startGame();
        gameEngine.getWorld().setContactListener(new CollisionListener());
        titleLabel = new Label(null, skin);
        titleLabel.setWrap(true);
        titleLabel.setFontScale(1);
        titleLabel.setBounds(100, 0, 500, 260);

        subtitleLabel = new Label(null, skin);
        subtitleLabel.setWrap(true);
        subtitleLabel.setFontScale(1);
        subtitleLabel.setBounds(100, -25, 500, 260);
    }

    @Override
    public void show() {
        startingTimeGameOver = 0;
        Gdx.input.setInputProcessor(GameEngine.getInstance().getInputManager().getController());
        names.clear();
        skin.getFont("default-font").getData().setScale(0.33f, 0.33f);
    }

    @Override
    protected void create() {
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.top().left();
        table.add(titleLabel).size(200, 100).center();
        table.row();
        table.add(subtitleLabel).size(100, 50).center().padTop(50);
    }

    @Override
    public void render(float delta) {
        //Window
        ScreenUtils.clear(0, 0.6f, 0.8f, 1);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        // Easy access variables
        GameEngine gameEngine = GameEngine.getInstance();

        //Init batches
        Box2D.init();
        batch.begin();

        //Calculations
        update();
        gameEngine.getMap().render();

        // Entities
        for (Character character : gameEngine.getCharacters()) {
            if (character.isAlive()) {
                character.update();
                HealthBar healthBar = character.getHealthBar();
                character.getAnimator().render(batch,
                        (int) character.getBody().getPosition().x - (Animator.width / 2),
                        (int) character.getBody().getPosition().y - (Animator.height / 2));
                healthBar.draw(batch, 1);
                if (!names.containsKey(character))
                    names.put(character, new Label(character.getInfo().getName(), skin));

                names.get(character).setBounds(healthBar.getX(), healthBar.getY() + 10f,
                        names.get(character).getPrefWidth(), names.get(character).getPrefHeight());
                names.get(character).draw(batch, 1);
            }
        }

        for (Projectile projectile : gameEngine.getProjectiles()) {
            int width = projectile.getTexture().getRegionWidth(), height = projectile.getTexture().getRegionHeight();
            Vector2 position = projectile.getBody().getPosition();
            batch.draw(projectile.getTexture(), position.x - (width / 2f), position.y - (height / 2f),
                    width / 2f, height / 2f, width, height, 1, 1, projectile.getRotation(), true);
        }

        titleLabel.draw(batch, 1f);
        subtitleLabel.draw(batch, 1f);

        batch.end();
        //debugRenderer.render(world, gameEngine.getMap().getCamera().combined);
    }

    private void update() {
        GameEngine engine = GameEngine.getInstance();
        Map map = engine.getMap();
        engine.getInputManager().processInput();
        destroyDeadBodies();
        destroyInactiveBodies(map.getWorld());
        map.getWorld().step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        batch.setProjectionMatrix(map.getCamera().combined);
        for (Character player : GameEngine.getInstance().getCharacters()) {
            player.getHealthBar().setPosition(player.getBody().getPosition().x - Animator.width / 2f,
                    player.getBody().getPosition().y + Animator.height / 2f);
            player.getHealthBar().updateVisualValue();
        }
        engine.getProjectiles().forEach(Projectile::update);
    }

    @Override
    public void resize(int width, int height) {
        GameEngine.getInstance().getMap().getCamera().setToOrtho(false,
                width / SCALE, height / SCALE);

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
        debugRenderer.dispose();
        for (Character player : GameEngine.getInstance().getCharacters()) {
            //player.getAnimator().
        }
        batch.dispose();
        GameEngine.getInstance().getWorld().dispose();
    }

    private void destroyDeadBodies() {
        GameEngine engine = GameEngine.getInstance();
        Gdx.app.postRunnable(() -> {
            engine.disableBodies();
            engine.clearDeadBodies();
        });
    }

    private void destroyInactiveBodies(World world) {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body b : bodies) {
            if (!b.isActive()) {
                world.destroyBody(b);
            }
        }
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setSubtitle(String subtitle) {
        subtitleLabel.setText(subtitle);
    }
}