package com.magma.engine.maps

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.magma.engine.assets.MapLoader
import com.magma.engine.collision.MapCollisions
import com.magma.engine.debug.MagmaLogger.log
import com.magma.engine.debug.modules.MapModuleListener
import com.magma.engine.maps.triggers.MapTriggerBuilder
import com.magma.engine.maps.triggers.MapTriggers
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.StageSwitchListener
import com.magma.engine.ui.dialog.Dialog
import java.lang.IllegalStateException

open class MapStage(batch: SpriteBatch,private val builder: MapTriggerBuilder) : GameStage(
   batch
), StageSwitchListener, MapModuleListener {
    lateinit var renderer: OrthogonalTiledMapRenderer
    lateinit var collisions: MapCollisions
    lateinit var triggers: MapTriggers
    lateinit var map: TiledMap
    lateinit var tmxName: String

    init {
        val dialog = Dialog(300, 120)
        uIStage.addActor(dialog)
    }
    override fun loadMap(tmxName: String){
        map = MapLoader.loadTilemap(tmxName)
        renderer = OrthogonalTiledMapRenderer(map, 1f / tileSize.x)

        // extract layers
        val layer = map.layers["Triggers"]
        if (layer != null) {
            collisions = MapCollisions(layer)
            triggers = MapTriggers(this, layer.objects, builder)
            addActor(triggers)
        } else {
            throw IllegalStateException("MapStage: No Triggers layer found!")
        }
    }

    override fun dispose() {
        unloadMap()
        super.dispose()
    }

    override fun unloadMap() {
        renderer.dispose()
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
}