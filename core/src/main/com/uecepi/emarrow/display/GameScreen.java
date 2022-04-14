package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.*;
import com.uecepi.emarrow.display.menus.ScreenMenu;
import com.uecepi.emarrow.map.Map;

import java.util.Date;
import java.util.Optional;

public class GameScreen extends ScreenMenu {

    public static final float SCALE = 2.0f;
    public static final float TIME_STEP = 1 / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    private final SpriteBatch batch;
    private final Box2DDebugRenderer debugRenderer;

    private long startingTimeGameOver = 0;

    public GameScreen() {
        super();
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        GameEngine gameEngine = GameEngine.getInstance();
        //gameEngine.startGame();
        gameEngine.getWorld().setContactListener(new CollisionListener());
    }

    @Override
    public void show() {
        startingTimeGameOver = 0;
        Gdx.input.setInputProcessor(GameEngine.getInstance().getInputManager().getController());
    }

    @Override
    protected void create() {
    }

    @Override
    public void render(float delta) {
        //Window
        ScreenUtils.clear(0, 0.6f, 0.8f, 1);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        // Easy access variables
        GameEngine gameEngine = GameEngine.getInstance();
        World world = gameEngine.getWorld();

        //Init batches
        Box2D.init();
        batch.begin();

        //Calculations
        update();
        gameEngine.getMap().render();

        // Entities
        for (Character character : gameEngine.getCharacters()) {
            character.update();
            character.getAnimator().render(batch,
                    (int) character.getBody().getPosition().x - (Animator.width / 2),
                    (int) character.getBody().getPosition().y - (Animator.height / 2));
            character.getHealthBar().draw(batch, 1);
        }
        for (Character player : GameEngine.getInstance().getCharacters()) {
            for (Projectile projectile : player.getProjectilesShot()) {
                batch.draw(projectile.getTexture(),
                        projectile.getBody().getPosition().x - (projectile.getTexture().getRegionWidth() / 2f),
                        projectile.getBody().getPosition().y - (projectile.getTexture().getRegionHeight() / 2f),
                        projectile.getTexture().getRegionWidth() / 2f, projectile.getTexture().getRegionHeight() / 2f,
                        projectile.getTexture().getRegionWidth(), projectile.getTexture().getRegionHeight(),
                        1, 1, projectile.getRotation(), true);
            }
        }

        // If game is ended
        if (gameEngine.isRoundFinished()) {
            if (startingTimeGameOver == 0)
                startingTimeGameOver = new Date().getTime();
            long remainingTime = (3 - ((new Date().getTime() - startingTimeGameOver) / 1000));
            Optional<PlayerInfo> winner = gameEngine.seekWinner();
            if(winner.isPresent()){
                Label gameFinished = new Label("Game is over ! Player %s won !\nBack to the main menu in %d"
                        .formatted(winner.get().getName(), remainingTime), skin);
                gameFinished.setWrap(true);
                table.add(gameFinished).row();
                gameFinished.setFontScale(1);
                gameFinished.setBounds(100, 0, 500, 260);
                gameFinished.draw(batch, 1f);
                if (remainingTime <= 0) {
                    Gdx.app.postRunnable(() -> {
                        GameEngine.getInstance().getGameClient().disconnect();
                        Screens.setScreen(Screens.MAIN_MENU);
                        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
                    });
                }
            }
        }
        batch.end();
        debugRenderer.render(world, gameEngine.getMap().getCamera().combined);
    }

    private void update() {
        Map map = GameEngine.getInstance().getMap();
        GameEngine.getInstance().getInputManager().processInput();
        destroyDeadBodies();
        destroyInactiveBodies(map.getWorld());
        map.getWorld().step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        batch.setProjectionMatrix(map.getCamera().combined);
        for (Character player : GameEngine.getInstance().getCharacters()) {
            player.getHealthBar().setPosition(player.getBody().getPosition().x - Animator.width / 2f,
                    player.getBody().getPosition().y + Animator.height / 2f);
            player.getHealthBar().updateVisualValue();
            for (Projectile projectile : player.getProjectilesShot()) {
                projectile.update();
            }
        }
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
        Gdx.app.postRunnable(() -> {
            for (int i = GameEngine.getInstance().getDeadBodies().size() - 1; i >= 0; i--) {
                GameEngine.getInstance().getDeadBodies().get(i).setActive(false);
                //world.destroyBody(GameEngine.getInstance().getDeadBodies().get(i).getBody());
            }
            GameEngine.getInstance().getDeadBodies().clear();
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

}