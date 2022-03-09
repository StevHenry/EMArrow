package com.uecepi.emarrow.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uecepi.emarrow.Emarrow;
import com.uecepi.emarrow.GameTest;
import com.uecepi.emarrow.assets.Assets;

public class DesktopLauncher {

	public static final int WIDTH = 1740;
	public static final int HEIGHT = 950;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 1920;
		config.height = 1080;
		configure(config);;
		new LwjglApplication(new GameTest(), config);
	}

	private static void configure(LwjglApplicationConfiguration config) {
		config.title = "Em'Arrow";
		config.width = WIDTH;
		config.height = HEIGHT;
		config.addIcon("images/icon.png", Files.FileType.Internal);
		config.resizable = false;
		config.x = 80;
		config.y = 0;
	}

}
