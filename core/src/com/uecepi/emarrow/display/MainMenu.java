package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.ControlsMenu;
import com.uecepi.emarrow.GameScreen;
import com.uecepi.emarrow.Emarrow;

public class MainMenu extends ScreenMenu{
    Label emarrow;
    TextButton controlButton;
    TextButton playButton;
    TextButton exitButton;
    TextButton backButton;




    public MainMenu(){
        super();
        create();
}

    private void create() {
        emarrow = new Label("Em'Arrow", skin);
        table.add(emarrow).row();
        //emarrow.setFontScale(10);

        playButton = new TextButton("Play", skin);
        table.add(playButton).height(100).width(200).padTop(30).row();
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new GameScreen());

            }
        });

        controlButton = new TextButton("Controls", skin);
        table.add(controlButton).height(100).width(200).padTop(30).row();
        controlButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new ControlsMenu());
                }

        });

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).height(100).width(200).padTop(30).row();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


    }




}
