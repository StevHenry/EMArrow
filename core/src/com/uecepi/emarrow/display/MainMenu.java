package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.*;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.audio.MusicManager;

public class MainMenu extends ScreenMenu{
    Image titleImage;
    TextButton controlButton;
    TextButton playButton;
    TextButton settingsButton;
    TextButton exitButton;

    public MainMenu(){
        super();
        create();
    }

    private void create() {
        MusicManager.setMusic(MusicManager.MUSIC1_BGM);
        titleImage = new Image(new Texture("images/title.png") );
        table.add(titleImage).row();

        playButton = new TextButton("Play", skin);
        table.add(playButton).height(100).width(200).row();
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

        settingsButton = new TextButton("Settings", skin);
        table.add(settingsButton).height(100).width(200).padTop(30).row();
        settingsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new SettingsScreen());
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
