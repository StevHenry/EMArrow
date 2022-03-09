package com.uecepi.emarrow.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu extends ScreenMenu{

    Label emarrow;
    TextButton controlButton;
    TextButton playButton;
    TextButton exitButton;




    public MainMenu(){
        super();
        create();

    }

    private void create() {
        emarrow = new Label("Em'Arrow",skin);
        table.add(emarrow);
        table.row();

        playButton = new TextButton("Play", skin);
        table.add(playButton);
        table.row();

        controlButton = new TextButton("Controls", skin);
        table.add(controlButton);
        table.row();

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton);
        table.row();

        exitButton.getClickListener();


    }





}
