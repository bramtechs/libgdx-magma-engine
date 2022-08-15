package com.magma.engine.maps

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.stages.ViewportContext

/**
 * Tries to implement Tiled Maps into Scene2D
 */
open class TiledMapActor<T : TiledMapRenderer>(val tileMap: T) : Actor() {
    override fun draw(batch: Batch, parentAlpha: Float) {
        tileMap.setView(ViewportContext.orthoCamera)
        tileMap.render()
    }
}
