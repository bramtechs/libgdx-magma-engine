package com.magma.engine.maps.triggers

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.collision.TriggerListener
import com.magma.engine.debug.MagmaLogger
import com.magma.engine.gfx.Drawable
import com.magma.engine.maps.MapStage
import com.magma.engine.utils.MagmaMath

abstract class MapTrigger(protected val rect: Rectangle, val original: MapObject) : Actor(), Drawable, TriggerListener {

    init {
        // import the originals properties
        MagmaMath.scaleRectangle(rect, 1f / MapStage.tileSize.x)
        setBounds(rect.x, rect.y, rect.width, rect.height)
        color = original.color
        isVisible = original.isVisible
        name = original.name
    }

    inline fun <reified T> parseProperty(name: String): T?{
       if (original.properties.containsKey(name)) {
          // try casting
           val item = original.properties.get(name)
           if (item is T){
              return item
           }
           return null
       }
        MagmaLogger.log("No key $name found in the properties of ${this.javaClass.simpleName}")
        return null
    }

    val mapStage: MapStage
        get() = stage as MapStage
}