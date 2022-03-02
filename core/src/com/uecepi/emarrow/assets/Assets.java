package com.uecepi.emarrow.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    private static AssetManager assetManager = new AssetManager();

    public static final AssetDescriptor<Skin> SKIN_VIS = new AssetDescriptor<>("skins/skin-composer/skin/skin-composer-ui.json", Skin.class, new SkinLoader.SkinParameter("skins/skin-composer/skin/skin-composer-ui.atlas"));

    public static void load() {
        assetManager.load(SKIN_VIS);
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }


}
