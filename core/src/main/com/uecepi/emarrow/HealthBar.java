package com.uecepi.emarrow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBar extends ProgressBar{
    private int hpMax;
    private int hpCurrent;
    private Texture texture;
    private ProgressBar progressBar;
    private Vector2 position;

    public HealthBar(int width,int height, int hpMax, Vector2 position){
        super(0f, hpMax, 1f, false, new ProgressBarStyle());
        getStyle().background = getColoredDrawable(width, height, Color.RED);
        getStyle().knob = getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.GREEN);

        setPosition(position.x,position.y);
        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(100f);

        setAnimateDuration(0.25f);
    }

    public Texture getTexture() {
        return texture;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public static Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

    @Override
    public String toString() {
        return "HealthBar{" +
                "value=" + getValue() +
                '}';
    }
}
