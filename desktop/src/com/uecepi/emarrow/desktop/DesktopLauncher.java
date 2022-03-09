package com.uecepi.emarrow.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uecepi.emarrow.Emarrow;

public class DesktopLauncher {

	public static final int WIDTH = 1740;
	public static final int HEIGHT = 950;


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		configure(config);;
		Emarrow game = new Emarrow();
		new LwjglApplication(game, config);
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
