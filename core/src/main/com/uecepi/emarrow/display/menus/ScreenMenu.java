package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.display.Screens;
import com.uecepi.emarrow.assets.Assets;

/**
 * Abstract class of menus.
 * ScreenMenus should not be instantiated outside {@link Screens}
 */
public abstract class ScreenMenu implements Screen {

    protected Stage stage;
    protected Table table;
    protected Skin skin;

    public ScreenMenu() {
        stage = new Stage();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = Assets.getAssetManager().get(Assets.SKIN_VIS);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    protected Button createBackToMainMenuButton() {
        Button backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.MAIN_MENU);
            }
        });
        backButton.setColor(new Color(1f, 0.25f, 0f, 1f));
        return backButton;
    }

    protected abstract void create();

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        table.clear();
        create();
        Gdx.input.setInputProcessor(stage);
        Gdx.app.log("screen_menu", "Changed input processor");
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
    }
}
