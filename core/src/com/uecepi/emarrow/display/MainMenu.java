package com.uecepi.emarrow.display;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.GameScreen;
import com.uecepi.emarrow.Emarrow;

public class MainMenu extends ScreenMenu{
    Emarrow game;
    Label emarrow;
    TextButton controlButton;
    TextButton playButton;
    TextButton exitButton;




    public MainMenu(Emarrow game){
        super();
        create();
        this.game = game;

    }

    private void create() {
        emarrow = new Label("Em'Arrow", skin);
        table.add(emarrow);
        table.row();

        playButton = new TextButton("Play", skin);
        table.add(playButton);
        table.row();
        onClick(playButton);

        controlButton = new TextButton("Controls", skin);
        table.add(controlButton);
        table.row();

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton);
        table.row();
    }

    private void onClick(TextButton button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });
    }








}
