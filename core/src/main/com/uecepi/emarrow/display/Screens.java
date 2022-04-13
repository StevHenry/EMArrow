package com.uecepi.emarrow.display;

import com.uecepi.emarrow.Emarrow;
import com.uecepi.emarrow.display.menus.*;

/**
 * This enum helps to manage the ScreenMenus
 */
public enum Screens {

    MAIN_MENU(new MainMenu()),
    SETTINGS_MENU(new SettingsMenu()),
    CONTROLS_MENU(new ControlsMenu()),
    LOG_IN_MENU(new LogInMenu()),
    SIGN_IN_MENU(new SignInMenu()),
    CONNECTION_MENU(new ConnectionMenu()),
    GAME_SCREEN(new GameScreen());

    private final ScreenMenu screenMenu;

    Screens(ScreenMenu screenMenu) {
        this.screenMenu = screenMenu;
    }

    /**
     * Used in order to change the current screen
     * @param screen the new {@link ScreenMenu} wanted
     */
    public static void setScreen(Screens screen) {
        Emarrow.getInstance().setScreen(screen.screenMenu);
    }

    /**
     * @return the instance of the selected Screens' ScreenMenu attribute.
     */
    public ScreenMenu getScreenMenu() {
        return screenMenu;
    }
}
