package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uecepi.emarrow.assets.Assets;

/**
 * Abstract class of menus.
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
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    //____________________________________________//
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {

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
