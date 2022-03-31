package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.*;
import com.uecepi.emarrow.audio.MusicManager;

public class MainMenu extends ScreenMenu {

    Image titleImage;
    TextButton signInButton;
    TextButton logInButton;
    TextButton controlButton;
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

        signInButton = new TextButton("Sign In", skin);
        signInButton.setColor(new Color(0.25f,1f,0f,1f));
        table.add(signInButton).height(80).width(200).row();
        signInButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new SignInMenu());


            }
        });

        logInButton = new TextButton("Log In", skin);
        logInButton.setColor(new Color(0.5f,0.25f,1f,1f));
        table.add(logInButton).height(80).width(200).padTop(20).row();
        logInButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new LogInMenu());


            }
        });

        controlButton = new TextButton("Controls", skin);
        controlButton.setColor(new Color(1f,1f,0f,1f));
        table.add(controlButton).height(80).width(200).padTop(20).row();
        controlButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new ControlsMenu());
                }

        });

        settingsButton = new TextButton("Settings", skin);
        table.add(settingsButton).height(80).width(200).padTop(20).row();
        settingsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Emarrow.getInstance().setScreen(new SettingsMenu());
            }
        });

        exitButton = new TextButton("Exit", skin);
        exitButton.setColor(new Color(1f,0.25f,0f,1f));
        table.add(exitButton).height(80).width(200).padTop(20).row();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }


}
