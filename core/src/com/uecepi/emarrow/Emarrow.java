package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;
import com.uecepi.emarrow.map.Map;

public class Emarrow extends Game {

    private Map map;

    @Override
    public void create() {
        Assets.load();
        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
