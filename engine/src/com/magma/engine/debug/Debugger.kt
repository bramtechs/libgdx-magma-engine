package com.magma.engine.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame
import com.magma.engine.debug.modules.Module
import java.awt.Dimension
import java.lang.IllegalStateException
import javax.swing.*

class Debugger private constructor(): JFrame("Debugger"), Disposable {

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

        pack()
    }

    override fun dispose() {
        super.dispose()
        MagmaLogger.log(this, "Debugger disposed")
    }

    companion object {
        private val modules: HashMap<Any,Module> = HashMap()
        private var instance: Debugger? = null

        var enabled: Boolean = false
            set(value) {
                field = value
                MagmaLogger.log(this,"Debug mode is "+if (field) "on" else "off")
            }

        fun addModule(owner: Any,module: Module) {
            if (!enabled) return

            if (instance == null){
                // lazy init the debugger
                instance = Debugger()
            }

            val prev = modules.put(owner,module)
            if (prev != null) throw IllegalStateException("Duplicate module types")

            instance!!.contentPane?.add(module)
            instance!!.pack()
        }

        fun removeOwnedModule(owner: Any){
            modules.remove(owner)
        }

        fun update() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
                if (enabled && instance == null){
                    instance = Debugger()
                }
                instance?.isVisible = !instance!!.isVisible
            }
            if (instance != null && instance!!.isVisible) {
                for (module in modules.values) {
                    module.update()
                }
            }
        }
    }
}
