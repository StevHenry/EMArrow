package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;
import com.uecepi.emarrow.map.Map;

public class GameTest extends Game {

    private Map map;

    @Override
    public void create() {
        Assets.load();
        setScreen(new MainMenu());
    }

    @Override
    public void render() {
        super.render();
    }
}
