package com.magma.engine.maps.triggers

import com.badlogic.gdx.maps.MapObject

interface MapTriggerBuilder {
    fun create(obj: MapObject): MapTrigger
}