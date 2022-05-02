package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.uecepi.emarrow.assets.Assets;
import com.uecepi.emarrow.display.Screens;
import com.uecepi.emarrow.networking.AccountClient;

public class Emarrow extends Game {

    private static final Emarrow instance = new Emarrow();
    private AccountClient accountClient;

    public static Emarrow getInstance() {
        return instance;
    }

    @Override
    public void create() {
        accountClient = new AccountClient();
        Assets.load();
        Screens.setScreen(Screens.MAIN_MENU);
    }

    @Override
    public void render() {
        super.render();
    }

    public AccountClient getAccountClient() {
        return accountClient;
    }
}
