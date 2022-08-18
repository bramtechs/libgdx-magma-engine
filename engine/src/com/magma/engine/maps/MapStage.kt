package com.magma.engine.maps

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.magma.engine.assets.MapLoader
import com.magma.engine.collision.MapCollisions
import com.magma.engine.debug.Debugger
import com.magma.engine.debug.MagmaLogger.log
import com.magma.engine.debug.modules.MapModule
import com.magma.engine.debug.modules.MapModuleListener
import com.magma.engine.maps.triggers.MapTriggerBuilder
import com.magma.engine.maps.triggers.MapTriggers
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.StageSwitchListener
import com.magma.engine.ui.dialog.Dialog
import java.lang.IllegalStateException

class MapStage(private val builder: MapTriggerBuilder, private val addActors: (stage: MapStage) -> Unit) : GameStage(), StageSwitchListener, MapModuleListener {
    lateinit var triggers: MapTriggers
    lateinit var map: TiledMap
    lateinit var tmxName: String

    private var renderer: OrthogonalTiledMapActor? = null
    private var collisions: MapCollisions? = null

    init {
        val dialog = Dialog(300, 120)
        uiStage.addActor(dialog)
        dialog.toFront()

        Debugger.addModule(this,MapModule(this))
    }

    override fun loadMap(name: String){
        this.tmxName = name
        map = MapLoader.loadTilemap(name)
        renderer = OrthogonalTiledMapActor((OrthogonalTiledMapRenderer(map, 1f / tileSize.x)))
        renderer!!.toBack()
        addActor(renderer!!)

        // extract layers
        val layer = map.layers["Triggers"]
        if (layer != null) {
            collisions = MapCollisions(layer)
            triggers = MapTriggers(this, layer.objects, builder)
            addActor(triggers)
        } else {
            throw IllegalStateException("MapStage: No Triggers layer found!")
        }
        addActors.invoke(this)
    }

    override fun dispose() {
        unloadMap()
        Debugger.removeOwnedModule(this)
        super.dispose()
    }

    override fun unloadMap() {
        renderer?.dispose()
        map.dispose()
        log(this, "Unloading map...")
    }
    override fun setTriggersVisible(on: Boolean) {
        isDebugAll = on
    }

    override fun toString(): String {
            var text = """
                tmx map: $tmxName
                """.trimIndent()
            text += """
                tileSize: $tileSize
                spawn: 
                """.trimIndent()
            return text
    }

    companion object {
        val tileSize: Vector2
            get() {
                // TODO get dynamically maybe
                return Vector2(16f, 16f)
            }
        val scaleFactor: Vector2
            get() {
                // TODO get dynamically maybe
                return Vector2(1f/ tileSize.x, 1f/ tileSize.y)
            }
    }
}