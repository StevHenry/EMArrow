package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.uecepi.emarrow.display.ScreenMenu;
import com.uecepi.emarrow.map.Map;

public class GameTest extends Game {

    private Map map;

    @Override
    public void create() {
        setScreen(new ScreenMenu() {

        });
        map = new Map("map1");
    }

    @Override
    public void render() {
        super.render();
        map.render();
    }
}
