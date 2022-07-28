package com.magma.engine.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.magma.engine.debug.MagmaLoggerDesktop;
import com.magma.engine.debug.MagmaLogger;
import com.magma.engine.desktop.MagmaLauncher;

public class TestLauncher extends MagmaLauncher {
    public static void main(String[] args){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowSizeLimits(640, 480, -1, -1);
        config.setWindowedMode((int) (640 * 1.5), (int) (480 * 1.5));
        config.setResizable(true);
        // config.setWindowIcon("assets/space/icon.png");
        config.setForegroundFPS(60);
        MagmaLogger.init(new MagmaLoggerDesktop("EngineTester"));
        new TestLauncher().launch(config, args);
    }

    @Override
    protected Game createNewGame(){
        return new TestGame();
    }
}
