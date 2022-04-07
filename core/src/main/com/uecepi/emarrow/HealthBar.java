package com.uecepi.emarrow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class HealthBar {
    private int hpMax;
    private int hpCurrent;
    private Texture texture;
    private ProgressBar progressBar;
    private Vector2 position;

    public HealthBar(int hpMax, Vector2 position){
        this.hpMax = hpMax;
        this.hpCurrent = hpMax;
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(/*skin.newDrawable("white", Color.DARK_GRAY), new Texture(pixmap)*/);
        progressBar = new ProgressBar(0, 10, 0.5f, false, barStyle);
        progressBar.setPosition(position.x,position.y);
        progressBar.setSize(290, progressBar.getPrefHeight());
        progressBar.setAnimateDuration(2);
    }

    public Texture getTexture() {
        return texture;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
