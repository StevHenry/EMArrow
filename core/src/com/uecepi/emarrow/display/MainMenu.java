package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
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
        table.add(emarrow).row();
        emarrow.setFontScale(10);

        playButton = new TextButton("Play", skin);
        table.add(playButton).height(100).width(200).padTop(30).row();
        onClick(playButton);
        //playButton.setLabel().setFontScale(10);

        controlButton = new TextButton("Controls", skin);
        table.add(controlButton).height(100).width(200).padTop(30).row();

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).height(100).width(200).padTop(30).row();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


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
