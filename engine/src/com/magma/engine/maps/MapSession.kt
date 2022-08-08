package com.magma.engine.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.assets.MapLoader.loadTilemap
import com.magma.engine.collision.MapCollisions
import com.magma.engine.maps.triggers.MapTriggerBuilder
import com.magma.engine.maps.triggers.MapTriggers
import java.lang.IllegalStateException

open class MapSession(private val stage: MapStage, val tmxName: String, triggerBuilder: MapTriggerBuilder) : Group(), Disposable {
    val renderer: OrthogonalTiledMapRenderer
    val map: TiledMap
    val collisions: MapCollisions
    val triggers: MapTriggers

    init {
        current = this
        Gdx.app.log("MapStage", "Opening map...")
        map = loadTilemap(tmxName)
        renderer = OrthogonalTiledMapRenderer(map, 1f / tileSize.x)

        // extract layers
        val layer = map.layers["Triggers"]
        if (layer != null) {
            collisions = MapCollisions(layer)
            triggers = MapTriggers(stage, layer.objects, triggerBuilder)
            addActor(triggers)
        } else {
            throw IllegalStateException("MapStage: No triggers layer found!")
        }
    }

    fun spawnTriggers(triggerBuilder: MapTriggerBuilder) {
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        renderer.setView(stage.orthoCamera)
        renderer.render()
        super.draw(batch, parentAlpha)
    }

    override fun addActor(actor: Actor) {
        stage.registerActor(actor)
        super.addActor(actor)
    }

    override fun dispose() {
        triggers.remove()
        renderer.dispose()
    }

    override fun getStage(): MapStage {
        return stage
    }

    companion object {

		var current: MapSession? = null

        val mapSize: GridPoint2
            get() {
                val mapSize = GridPoint2()
                if (current == null) {
                    throw NullPointerException("No map session active!")
                }
                val props = current!!.map.properties
                if (props.containsKey("width")) {
                    mapSize.x = props["width"] as Int
                } else {
                    Gdx.app.log("MapStage", "Could not detect tile width from map, defaulting to 16")
                    mapSize.x = 16
                }
                if (props.containsKey("height")) {
                    mapSize.y = props["height"] as Int
                } else {
                    Gdx.app.log("MapStage", "Could not detect tile width from map, defaulting to 16")
                    mapSize.y = 16
                }
                return mapSize
            }

		val tileSize: GridPoint2
            get() {
                val tileSize = GridPoint2(16, 16)
                if (current == null) {
                    return tileSize
                }
                val props = current!!.map.properties
                if (props.containsKey("tilewidth")) {
                    tileSize.x = props["tilewidth"] as Int
                } else {
                    Gdx.app.log("MapStage", "Could not detect tile width from map, defaulting to 16")
                    tileSize.x = 16
                }
                if (props.containsKey("tileheight")) {
                    tileSize.y = props["tileheight"] as Int
                } else {
                    Gdx.app.log("MapStage", "Could not detect tile height from map, defaulting to 16")
                    tileSize.y = 16
                }
                return tileSize
            }
    }
}