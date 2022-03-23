package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;
import com.uecepi.emarrow.map.Map;

public class Emarrow extends Game {

    private static final Emarrow instance = new Emarrow();

    private Emarrow(){

    }
    public static Emarrow getInstance() {
        return instance;
    }

    KeyboardController controller = new KeyboardController();
    private Map map;

    @Override
    public void create() {
        Assets.load();
        Emarrow.getInstance().getController().setLeftKey(Input.Keys.Q);
        Emarrow.getInstance().getController().setRightKey(Input.Keys.D);
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
