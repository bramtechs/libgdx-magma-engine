package com.magma.engine.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.magma.engine.debug.Crasher;

public abstract class MagmaLauncher {
	
	public void launch(Lwjgl3ApplicationConfiguration config, String[] args) {
		
		boolean handleCrashes = true;
		
		for (String arg : args) {
			if (arg.contains("--nocrash")) {
				handleCrashes = false;
				System.out.println("Crash handler disabled.");
			}
		}
		
		if (handleCrashes) {
			try {
				new Lwjgl3Application(createNewGame(),config);
			} catch (Exception e) {
				Crasher.showCrashDialog(e);
			}
		}else {
			new Lwjgl3Application(createNewGame(),config);
		}
	}
	
	protected abstract Game createNewGame();
}
