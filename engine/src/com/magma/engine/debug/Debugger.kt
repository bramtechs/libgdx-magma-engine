package com.magma.engine.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame
import com.magma.engine.debug.MagmaLogger.log
import com.magma.engine.debug.modules.InfoModule
import com.magma.engine.debug.modules.MapModule
import com.magma.engine.debug.modules.Module
import com.magma.engine.debug.modules.StageModule
import java.awt.Dimension
import javax.swing.*

object Debugger : JFrame("Debugger"), Disposable {
    private val modules: Array<Module> = Array()

    init {
        // prevents the game from hanging when closing, due to the frame still being
        // available
        MagmaGame.disposeOnExit(this)

        // Construct the window
        defaultCloseOperation = DISPOSE_ON_CLOSE
        isResizable = true
        layout = BoxLayout(contentPane, BoxLayout.PAGE_AXIS)
        setLocationRelativeTo(null)
        val size = Dimension(400, 700)
        minimumSize = size
        preferredSize = size

        // add menubar
        val menuBar = JMenuBar()
        val menuFile = JMenu("File")
        val menuItemExit = JMenuItem("Exit")
        menuItemExit.addActionListener { isVisible = false }
        menuFile.add(menuItemExit)
        menuBar.add(menuFile)

        // adds menu bar to the frame
        jMenuBar = menuBar

        // generate modules
        addModule(InfoModule())
        addModule(MapModule())
        addModule(StageModule())
        pack()
    }

    override fun dispose() {
        super.dispose()
        log(this, "Debugger disposed")
    }

        fun addModule(module: Module) {
            modules.add(module)
            contentPane.add(module)
            pack()
        }

        fun update() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
                isVisible = !isVisible
            }
            if (isVisible) {
                for (module in modules) {
                    module.update()
                }
            }
        }

        fun <T : Module> getModule(type: Class<T>): Module {
            for (mod in modules) {
                log(type)
                if (mod.javaClass == type) {
                    return mod
                }
            }
            throw NullPointerException("Cannot find module $type")
        }
}