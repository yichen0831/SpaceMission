package com.ychstudio.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ychstudio.SpaceRocket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(400, 600);
		config.setResizable(false);
//		config.width = 400;
//		config.height = 600;
		new Lwjgl3Application(new SpaceRocket(), config);
	}
}
