package com.magma.engine.maps

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.magma.engine.debug.MagmaLogger.log
import com.magma.engine.debug.modules.MapModuleListener
import com.magma.engine.maps.MapSession.Companion.tileSize
import com.magma.engine.maps.triggers.MapTriggerBuilder
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.StageSwitchListener
import com.magma.engine.stages.ViewportContext
import com.magma.engine.ui.dialog.Dialog

open class MapStage// TODO: add dialog Stack
    (viewports: ViewportContext, batch: SpriteBatch, private val triggerBuilder: MapTriggerBuilder) : GameStage(
    viewports,
    batch
), StageSwitchListener, MapModuleListener {
    private var session: MapSession? = null

    init {
        val dialog = Dialog(viewports.ui, 300, 120)
        ui.addActor(dialog)
    }

    fun openSession(session: MapSession) {
        // close session if already open
        if (this.session != null) closeSession()
        log(this, "Starting session...")
        session.spawnTriggers(triggerBuilder)
        session.toFront()
        addActor(session)
        this.session = session
    }

    fun closeSession() {
        log(this, "Closing session...")
        session!!.toBack()
        session!!.remove()
        session!!.dispose()
        session = null
    }

    override fun dispose() {
        if (session != null) {
            session!!.dispose()
            session = null
        }
        super.dispose()
    }

    override fun stageOpened(stage: GameStage) {}
    override fun stageClosed(stage: GameStage) {
        if (stage === this) {
            closeSession()
        }
    }

    override fun unloadMap() {
        if (session == null) {
            log("Map unloading not necessary")
            return
        }
        session!!.dispose()
        session = null
        log(this, "Unloading map...")
    }

    override fun setTriggersVisible(on: Boolean) {
        session!!.setDebug(on, true)
    }

    override fun requestMap(name: String) {
        // You should override this
    }

    override fun toString(): String {
        if (session != null) {
            var text = """
                tmx map: ${session!!.tmxName}
                
                """.trimIndent()
            text += """
                tileSize: $tileSize
                spawn: 
                """.trimIndent()
            return text
        }
        return " dormant"
    }

    override fun resize(width: Int, height: Int) {
        // TODO Auto-generated method stub
    }
}