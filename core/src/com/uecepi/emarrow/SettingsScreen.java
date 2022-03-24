package com.uecepi.emarrow;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.w3c.dom.Text;

public class SettingsScreen extends ScreenMenu {
    Label settings;
    TextButton backButton;

    public SettingsScreen(){
        super();
        create();
    }

    private void create(){
        settings = new Label("SETTINGS", skin);
        table.add(settings).row();

        backButton = new TextButton("Back", skin);
        table.add(backButton).width(200).height(100).padTop(30).row();
        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new MainMenu());
            }
        });
    }
}
