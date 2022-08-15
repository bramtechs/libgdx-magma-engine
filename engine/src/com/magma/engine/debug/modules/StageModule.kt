package com.magma.engine.debug.modules

import com.badlogic.gdx.utils.Timer
import com.magma.engine.debug.MagmaLogger.log
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.StageSwitchListener
import com.magma.engine.stages.StageSwitcher.addListener
import javax.swing.JLabel
import javax.swing.JScrollPane
import javax.swing.JTextArea

// TODO recreate these things on each stage
class StageModule : Module("Stage"), StageSwitchListener {
    private val curStage: JLabel = JLabel("Active stage:")
    private val count: JLabel = JLabel()
    private val root: JTextArea = JTextArea()
    private val rootUI: JTextArea
    private lateinit var activeStage: GameStage

    init {
        root.isEditable = false
        rootUI = JTextArea()
        rootUI.isEditable = false
        val scroll = JScrollPane(root)
        val scrollUI = JScrollPane(rootUI)
        addListener(this)
        add(curStage)
        add(count)
        add(scroll)
        add(scrollUI)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                // TODO: only run when visible
                if (activeStage != null) {
                    val children = activeStage!!.root.children
                    count.text = "Child count: " + children.size
                    root.text = "Root: " + activeStage!!.root.toString()
                    rootUI.text = "Root UI: " + activeStage!!.uiStage.root.toString()
                } else {
                    count.text = "(not available)"
                    root.text = "(not available)"
                }
            }
        }, 0f, 3f)
    }

    override fun update() {}
    override fun stageOpened(stage: GameStage) {
        activeStage = stage
        log("available")
        curStage.text = "Active stage: " + stage.javaClass.simpleName
    }

    override fun stageClosed(stage: GameStage) {
        curStage.text = "Active stage: (none)"
    }
}