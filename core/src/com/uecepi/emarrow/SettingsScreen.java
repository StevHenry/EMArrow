package com.uecepi.emarrow;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.w3c.dom.Text;

public class SettingsScreen extends ScreenMenu {
    Label settings;
    TextButton backButton;
    Label generalVolumeLabel;
    Label bgmVolumeLabel;
    Label seVolumeLabel;
    Slider generalVolumeButton; //General volume cursor
    Slider bgmVolumeButton;//Background music volume cursor
    Slider seVolumeButton;//sound effect volume cursor

    public SettingsScreen(){
        super();
        create();
    }

    private void create(){
        settings = new Label("SETTINGS", skin);
        table.add(settings).row();
        settings.setFontScale(10);

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

        backButton = new TextButton("Back", skin);
        table.add(backButton).width(200).height(100).padTop(30).row();
        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new MainMenu());
            }
        });
    }
}
