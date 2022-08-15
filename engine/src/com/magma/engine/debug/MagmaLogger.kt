package com.magma.engine.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.magma.engine.utils.OsUtil
import com.magma.engine.utils.Time

object MagmaLogger {
    private var htmlLog: String = ""

    enum class Level {
        DEBUG, INFO, WARN, ERROR, BREAKPOINT
    }

    fun logException(e: Exception) {
        e.printStackTrace()
        log("❌ EXCEPTION ❌", e.toString(), Level.ERROR)
    }

    /**
     * Logs a collection
     */
    fun logl(sender: Any, vararg messages: Any) {
        var combo = ""
        for ((i, o) in messages.withIndex()) {
            if (i > 0) {
                combo += ", "
            }
            combo += o.toString()
        }
        log(sender, combo, Level.INFO)
    }

    fun log(sender: Any, message: Any, level: Level? = Level.INFO) {
        val suffix = if (sender is String) sender.toString() else sender.javaClass.simpleName
        val time = Time.time.toString()
        val prefix = "(" + time + "s) "
        val name = prefix + suffix

        // TODO: messes up after a long time due to floats
        val mes = message.toString()
        htmlLog += when (level) {
            Level.DEBUG -> "$name $mes<br/>"
            Level.ERROR -> "<strong style='color: red'>$name<br/>$mes</strong><br/>"
            Level.INFO -> "<strong>$name</strong> $mes<br/>"
            Level.WARN -> "<strong style='color: yellow'>$name</strong> $mes<br/>"
            Level.BREAKPOINT -> "<h2 style='color: purple'>$name -> $mes</h2><br/>"
            else -> throw IllegalArgumentException("Invalid log level")
        }
        Gdx.app.log(name, message.toString())
    }

    fun log(message: Any) {
        log("CODE", message, Level.DEBUG)
    }

    fun <T> printArray(sender: Any, array: Array<T>) {
        var text = " collection: " + sender.javaClass.simpleName + " "
        if (array.isEmpty()) {
            text += "(empty)"
        } else {
            for (item in array) {
                text += ", " + item.toString()
            }
        }
        log(sender, text)
    }

    fun printArray(sender: Any, array: Iterable<*>) {
        var text = " collection: " + sender.javaClass.simpleName + " "
        var i = 0
        for (item in array) {
            text += ", $item"
            i++
        }
        if (i == 0) {
            text += "(empty)"
        }
        log(sender, text)
    }

    fun breakPoint(sender: Any, text: String) {
        log(sender, text, Level.BREAKPOINT)
        throw IllegalStateException("")
    }

    fun breakPoint(sender: Any) {
        val message = "BREAKPOINT"
        log(sender, message, Level.BREAKPOINT)
        throw IllegalStateException("")
    }

    fun flush(crashed: Boolean = false): String {
        val file = OsUtil.getUserDataDirectory("SpaceGame")

        // append end text
        htmlLog += "<hr/>"
        log("", "State: " + if (crashed) "FAILED" else "SUCCEEDED")
        htmlLog += "</p>"

        // print to a file
        var handle = FileHandle("$file/logs/")
        handle.mkdirs()
        val name: String = if (crashed) {
            "crash_" + Time.startTime + ".html"
        } else {
            "latest.html"
        }
        handle = handle.child(name)
        val path = handle.file().absolutePath
        log("MagmaLogger", "Log file saved to: $path")
        handle.writeString(htmlLog, false)
        Gdx.app.log("MagmaLogger", "Log saved to $path")
        return path
    }
}