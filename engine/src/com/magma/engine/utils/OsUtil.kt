package com.magma.engine.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import java.io.IOException
import java.util.*

//https://github.com/libgdx/libgdx/issues/6559
object OsUtil {
    private val OS = System.getProperty("os.name").lowercase(Locale.getDefault())
    var isAndroid = System.getProperty("java.runtime.name").contains("Android")
    var isMac = !isAndroid && OS.contains("mac")
    var isWindows = !isAndroid && OS.contains("windows")
    var isLinux = !isAndroid && OS.contains("linux")
    var isIos = !isAndroid && !(isWindows || isLinux || isMac) || OS.startsWith("ios")
    var isARM = (System.getProperty("os.arch").startsWith("arm")
            || System.getProperty("os.arch").startsWith("aarch64"))
    var is64Bit = (System.getProperty("os.arch").contains("64")
            || System.getProperty("os.arch").startsWith("armv8"))
    var isGwt = false

    init {
        try {
            Class.forName("com.google.gwt.core.client.GWT")
            isGwt = true
        } catch (ignored: Exception) {
            /* IGNORED */
        }
        val isMOEiOS = "iOS" == System.getProperty("moe.platform.name")
        if (isMOEiOS || !isAndroid && !isWindows && !isLinux && !isMac) {
            isIos = true
            isAndroid = false
            isWindows = false
            isLinux = false
            isMac = false
            is64Bit = false
        }
    }

    fun getUserConfigDirectory(applicationName: String): String {
        var CONFIG_HOME: String
        if (System.getenv("XDG_CONFIG_HOME").also { CONFIG_HOME = it } == null) {
            if (isLinux || isAndroid) {
                CONFIG_HOME = System.getProperty("user.home") + "/.config"
            } else if (isMac) {
                CONFIG_HOME = System.getProperty("user.home") + "/Library/Preferences"
            } else if (isIos) {
                CONFIG_HOME = System.getProperty("user.home") + "/Documents"
            } else if (isWindows) {
                if (System.getenv("APPDATA").also { CONFIG_HOME = it } == null) {
                    CONFIG_HOME = System.getProperty("user.home") + "/Local Settings"
                }
            }
        }
        return "$CONFIG_HOME/$applicationName"
    }

    fun getUserDataDirectory(applicationName: String): String {
        var DATA_HOME: String? = null
        if (System.getenv("XDG_DATA_HOME").also { DATA_HOME = it } == null) {
            if (isLinux || isAndroid) {
                DATA_HOME = System.getProperty("user.home") + "/.local/share"
            } else if (isMac) {
                DATA_HOME = System.getProperty("user.home") + "/Library/Application Support"
            } else if (isIos) {
                DATA_HOME = System.getProperty("user.home") + "/Documents"
            } else if (isWindows) {
                if (System.getenv("APPDATA").also { DATA_HOME = it } == null) {
                    DATA_HOME = System.getProperty("user.home") + "/Local Settings/Application Data"
                }
            }
        }
        return "$DATA_HOME/$applicationName"
    }

    fun openExplorer(handle: FileHandle) {
        if (!isWindows) {
            Gdx.app.error("OsUtil", "Can't open explorer, not on Windows")
            return
        }
        val path = handle.file().absolutePath
        val builder = ProcessBuilder(
            "explorer.exe", path
        )
        builder.redirectErrorStream(true)
        try {
            val p = builder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun printPaths() {
        Gdx.app.debug("Local", Gdx.files.localStoragePath)
        Gdx.app.debug("External", Gdx.files.externalStoragePath)
    }
}