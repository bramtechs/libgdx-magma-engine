package com.magma.engine.maps.triggers

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.collision.TriggerListener
import com.magma.engine.gfx.Drawable
import com.magma.engine.maps.MapStage
import com.magma.engine.utils.MagmaMath

abstract class MapTrigger(protected val rect: Rectangle, original: MapObject) : Actor(), Drawable, TriggerListener {

    init {

        // import the originals properties
        MagmaMath.scaleRectangle(rect, 1f / MapStage.tileSize.x)
        setBounds(rect.x, rect.y, rect.width, rect.height)
        color = original.color
        isVisible = original.isVisible
        name = original.name
        unpack(original.properties)
    }

    val mapStage: MapStage
        get() = stage as MapStage

    protected abstract fun unpack(properties: MapProperties)
}