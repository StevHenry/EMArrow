package com.uecepi.emarrow.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uecepi.emarrow.display.Animator;
import java.util.HashMap;
import com.uecepi.emarrow.audio.MusicManager;

public class Assets {


    private static AssetManager assetManager = new AssetManager();

    public static final AssetDescriptor<Skin> SKIN_VIS = new AssetDescriptor<>("default/uiskin.json", Skin.class, new SkinLoader.SkinParameter("default/uiskin.atlas"));

    // Animation sprite sheets
    public static final HashMap<String, Animation<TextureRegion>> ANIMATIONS = new HashMap<>();

    public static void load() {
        Animator.load();
        MusicManager.load();
        assetManager.load(SKIN_VIS);
        assetManager.finishLoading();

    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

}
