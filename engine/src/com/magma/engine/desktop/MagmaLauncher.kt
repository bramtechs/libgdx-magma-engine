package com.magma.engine.desktop

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.magma.engine.debug.Crasher

abstract class MagmaLauncher {
    fun launch(config: Lwjgl3ApplicationConfiguration?, args: Array<String>) {
        var handleCrashes = true
        for (arg in args) {
            if (arg.contains("--nocrash")) {
                handleCrashes = false
                println("Crash handler disabled.")
            }
        }
        if (handleCrashes) {
            try {
                Lwjgl3Application(createNewGame(), config)
            } catch (e: Exception) {
                Crasher.showCrashDialog(e)
            }
        } else {
            Lwjgl3Application(createNewGame(), config)
        }
    }

    protected abstract fun createNewGame(): Game
}