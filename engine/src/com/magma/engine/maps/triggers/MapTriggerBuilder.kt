package com.magma.engine.maps.triggers

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.math.Rectangle
import com.magma.engine.maps.triggers.stock.KillzoneTrigger
import com.magma.engine.maps.triggers.stock.SolidTrigger

interface MapTriggerBuilder {
    fun create(obj: MapObject): MapTrigger {
        // construct MapTrigger with correct shape
        if (obj is RectangleMapObject) {
            val rect = obj.rectangle

            // when exception: no Type method found of object?
            return when (val type = obj.getProperties()["type"].toString()) {
                "Solid" -> SolidTrigger(rect, obj)
                "Killzone" -> KillzoneTrigger(rect, obj)
                else -> createCustom(rect, type, obj)
            }
        }
        throw IllegalStateException("MapLoader only supports RectangleMapObjects! This is a " + obj.javaClass.simpleName)
    }

    /**
     * Define custom MapTrigger types here!
     */
    fun createCustom(bounds: Rectangle, type: String, `object`: MapObject): MapTrigger
}