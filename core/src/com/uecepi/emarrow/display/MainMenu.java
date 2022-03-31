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
import com.uecepi.emarrow.SettingsScreen;
import com.uecepi.emarrow.audio.MusicManager;

public class MainMenu extends ScreenMenu{
    Emarrow game;
    Label emarrow;
    TextButton controlButton;
    TextButton playButton;
    TextButton settingsButton;
    TextButton exitButton;

    public MainMenu(){
        super();
        create();
}

    private void create() {
        emarrow = new Label("Em'Arrow", skin);
        table.add(emarrow).row();
        emarrow.setFontScale(10);

        playButton = new TextButton("Play", skin);
        table.add(playButton).height(100).width(200).padTop(30).row();
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new GameScreen());
                MusicManager.playSE(2);
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
                MusicManager.setMusic(MusicManager.MUSIC1_BGM);
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
