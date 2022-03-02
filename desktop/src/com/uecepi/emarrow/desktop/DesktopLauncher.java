package com.uecepi.emarrow.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uecepi.emarrow.Emarrow;
import com.uecepi.emarrow.map.Map;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		configure(config);
		new LwjglApplication(new Map(), config);
	}

	private static void configure(LwjglApplicationConfiguration config) {
		config.title = "Em'Arrow";
		config.width = 1740;
		config.height = 950;
		config.addIcon("images/icon.png", Files.FileType.Internal);
		config.resizable = false;
		config.x = 80;
		config.y = 0;
	}

}
