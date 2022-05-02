package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.Screens;

/**
 * Main Menu class.
 */
public class MainMenu extends ScreenMenu {

    private Image titleImage;
    private TextButton signInButton;
    private TextButton logInButton;
    private TextButton controlButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    public MainMenu() {
        super();
    }

    @Override
    protected void create() {
        MusicManager.setMusic(MusicManager.MUSIC1_BGM);

        addTitle();
        addSignInButton();
        addLogInButton();
        addControlButton();
        addSettingsButton();
        addExitButton();
    }


    //_____________________ Extracted Methods _________________________//

    private void addExitButton() {
        //Création du bouton qui permet de fermer le jeu
        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).height(80).width(200).padTop(20).row();
        exitButton.setColor(new Color(1f, 0.25f, 0f, 1f));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void addSettingsButton() {
        //Création du bouton redirigeant vers le SettingsMenu, menu qui change les paramètres de son
        settingsButton = new TextButton("Settings", skin);
        table.add(settingsButton).height(80).width(200).padTop(20).row();
        settingsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.SETTINGS_MENU);
            }
        });
    }

    private void addControlButton() {
        //Création du bouton redirigeant vers le ControlsMen, menu qui change les commandes
        controlButton = new TextButton("Controls", skin);
        table.add(controlButton).height(80).width(200).padTop(20).row();
        controlButton.setColor(new Color(0.5f, 0.4f, 0.2f, 1f));
        controlButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.CONTROLS_MENU);
            }

        });
    }

    private void addLogInButton() {
        //Création du bouton redirigeant vers le LogInMenu
        logInButton = new TextButton("Log In", skin);
        logInButton.setColor(new Color(0.5f, 0.25f, 1f, 1f));
        logInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.LOG_IN_MENU);
            }
        });
        table.add(logInButton).height(80).width(200).padTop(20).row();
    }

    private void addSignInButton() {
        //Création du bouton redirigeant vers le SignInMenu
        signInButton = new TextButton("Sign In", skin);
        table.add(signInButton).height(80).width(200).padTop(20).row();
        signInButton.setColor(new Color(0.25f, 1f, 0f, 1f));
        signInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.SIGN_IN_MENU);
            }
        });
    }

    private void addTitle() {
        //Titre du menu : Image au nom du jeu
        titleImage = new Image(new Texture("images/title.png"));
        table.add(titleImage).row();
    }

    @Override
    public void show() {
        super.show();
        skin.getFont("default-font").getData().setScale(1f, 1f);
    }
}
