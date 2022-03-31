package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.KeyboardController;
import com.uecepi.emarrow.display.menus.MainMenu;
import com.uecepi.emarrow.map.Map;

public class Emarrow extends Game {

    private static final Emarrow instance = new Emarrow();

    private Emarrow(){

    }
    public static Emarrow getInstance() {
        return instance;
    }

    private KeyboardController controller;
    private Map map;

    @Override
    public void create() {
        controller = new KeyboardController();
        Assets.load();
        Emarrow.getInstance().getController().setLeftKey(Input.Keys.LEFT);
        Emarrow.getInstance().getController().setRightKey(Input.Keys.RIGHT);
        Emarrow.getInstance().getController().setDashKey(Input.Keys.SHIFT_LEFT);
        setScreen(new MainMenu());
    }

    @Override
    public void render() {
        super.render();
    }

    public void setController(KeyboardController Controller) {
        this.controller = controller;
    }

    public KeyboardController getController() {
        return controller;
    }
}
