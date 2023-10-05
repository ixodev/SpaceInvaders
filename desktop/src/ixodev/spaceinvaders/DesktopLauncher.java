package ixodev.spaceinvaders;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static ixodev.spaceinvaders.Constants.*;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
		config.setWindowIcon(WINDOW_ICON);
		config.setTitle(WINDOW_TITLE);
		config.setResizable(false);
		new Lwjgl3Application(new Game(), config);
	}
}
