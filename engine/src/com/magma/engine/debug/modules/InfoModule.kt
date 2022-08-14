package com.magma.engine.debug.modules

import com.badlogic.gdx.Gdx
import javax.swing.JLabel

class InfoModule : Module("Info") {
    private val fps: JLabel = JLabel()
    private val mem: JLabel = JLabel()

    init {
        add(fps)
        add(mem)
    }

    override fun update() {
        fps.text = format("FPS", Gdx.graphics.framesPerSecond)
        mem.text = format("Memory", memory)
    }

    private val memory: String
        get() {
            val mb = 1024 * 1024
            val mem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb
            val totalMem = Runtime.getRuntime().totalMemory() / mb
            val maxMem = Runtime.getRuntime().maxMemory() / mb
            return (mem.toString() + " | reserved " + totalMem.toString() + " | cap " + maxMem.toString()
                    + " mb")
        }
}