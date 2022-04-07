package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.KeyboardController;
import com.uecepi.emarrow.display.menus.MainMenu;
import com.uecepi.emarrow.map.Map;
import com.uecepi.emarrow.networking.account.AccountClient;

public class Emarrow extends Game {

    private static final Emarrow instance = new Emarrow();
    private AccountClient client;
    private KeyboardController controller;
    private Map map;


    public static Emarrow getInstance() {
        return instance;
    }

    @Override
    public void create() {
        controller = new KeyboardController();
        Assets.load();
        Emarrow.getInstance().getController().setLeftKey(Input.Keys.LEFT);
        Emarrow.getInstance().getController().setRightKey(Input.Keys.RIGHT);
        Emarrow.getInstance().getController().setDashKey(Input.Keys.SHIFT_LEFT);
        setScreen(new MainMenu());
        client = new AccountClient();
    }

    @Override
    public void render() {
        super.render();
    }

    public KeyboardController getController() {
        return controller;
    }

    public void setController(KeyboardController Controller) {
        this.controller = controller;
    }

    public AccountClient getAccountClient() {
        return client;
    }
}
