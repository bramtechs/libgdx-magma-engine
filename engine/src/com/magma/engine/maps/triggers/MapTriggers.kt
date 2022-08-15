package com.magma.engine.maps.triggers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.magma.engine.maps.MapStage

class MapTriggers(private val stage: MapStage, objects: MapObjects, builder: MapTriggerBuilder) : Group() {
    init {
        for (obj in objects) {
            if (obj.properties.containsKey("type")) {
                // construct MapTrigger
                val trigger = builder.create(obj)
                addActor(trigger)
            }
        }
        Gdx.app.log("MapTriggers", toString())
    }

    override fun addActor(actor: Actor) {
        stage.registerActor(actor)
        super.addActor(actor)
    }

    override fun remove(): Boolean {
        for (a in children) {
            stage.unregisterActor(a)
        }
        return super.remove()
    }

    override fun toString(): String {
        var debug = ""
        for (a in children) {
            if (a is MapTrigger) {
                val entry = a
                debug += """
                    /==> trigger of type ${entry.javaClass.simpleName}
                    """.trimIndent()
                debug += entry.toString()
            }
        }
        return debug
    }

    val count: Int
        get() = children.size
}