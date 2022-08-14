package com.magma.engine.debug

import com.magma.engine.debug.MagmaLogger.logException
import java.awt.Dimension
import java.io.IOException
import javax.swing.JEditorPane
import javax.swing.JFrame
import javax.swing.JLabel

class Crasher private constructor(htmlPath: String) : JFrame("Whoops, something went horribly wrong!") {
    init {
        isResizable = true
        defaultCloseOperation = EXIT_ON_CLOSE
        val size = Dimension(600, 600)
        preferredSize = size
        minimumSize = size
        try {
            val editor = JEditorPane("file://$htmlPath")
            editor.isEditable = false
            contentPane.add(editor)
            pack()
        } catch (ec: IOException) {
            val label = JLabel("Could not display content.")
            contentPane.add(label)
            ec.printStackTrace()
        }
        isVisible = true
    }

    companion object {
        fun showCrashDialog(e: Exception): Crasher {
            logException(e)
            val path = MagmaLogger.flush(true)
            return Crasher(path)
        }
    }
}