package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.Emarrow;
import com.uecepi.emarrow.audio.MusicManager;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Classe qui permet de modifier les paramètres de son
 */
public class SettingsMenu extends ScreenMenu {
    Label settings;
    TextButton backButton;
    Label generalVolumeLabel;
    Label bgmVolumeLabel;
    Label seVolumeLabel;
    Slider generalVolumeButton; //General volume cursor
    Slider bgmVolumeButton;//Background music volume cursor
    Slider seVolumeButton;//sound effect volume cursor

    /**
     * Constructeur de SettingsMenu
     */
    public SettingsMenu(){
        super();
        create();
    }

    /**
     * Gère la création des éléments de Settings menu
     */
    private void create(){
        //Création du titre du menu
        settings = new Label("SETTINGS", skin);
        table.add(settings).row();
        settings.setFontScale(3);

        //Création du slider permettant de changer le volume général du jeu
        generalVolumeLabel = new Label("General volume", skin);
        table.add(generalVolumeLabel).padTop(30).row();
        generalVolumeButton = new Slider(0, 100,1, false, skin);
        table.add(generalVolumeButton).width(200).height(100).padTop(0).row();
        generalVolumeButton.setValue(100);
        generalVolumeButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MusicManager.setVolume(generalVolumeButton.getValue()/100f);
                return false;
            }
        });

        //Création du slider permettant de changer le volume de la musique de fond du jeu
        bgmVolumeLabel = new Label("Music volume", skin);
        table.add(bgmVolumeLabel).padTop(30).row();
        bgmVolumeButton = new Slider(0, 100,1, false, skin);
        table.add(bgmVolumeButton).width(200).height(100).padTop(0).row();
        bgmVolumeButton.setValue(100);
        bgmVolumeButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MusicManager.setBGMVolume(bgmVolumeButton.getValue()/100f);
                return false;
            }
        });

        //Création du slider permettant de changer le volume du son des effets du jeu
        seVolumeLabel = new Label("Sound effects volume", skin);
        table.add(seVolumeLabel).padTop(30).row();
        seVolumeButton = new Slider(0, 100,1, false, skin);
        table.add(seVolumeButton).width(200).height(100).padTop(0).row();
        seVolumeButton.setValue(100);
        seVolumeButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MusicManager.setSEVolume(seVolumeButton.getValue()/100f);
                return false;
            }
        });

        //Création d'un bouton permettant d'aller sur un nouveau MainMenu
        backButton = new TextButton("Back", skin);
        table.add(backButton).width(200).height(100).padTop(30).row();
        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new MainMenu());
            }
        });
        backButton.setColor(new Color(1f,0.25f,0f,1f));

    }
}
