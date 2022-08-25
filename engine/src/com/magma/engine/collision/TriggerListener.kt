package com.magma.engine.collision

import com.badlogic.gdx.scenes.scene2d.Actor

interface TriggerListener {
    fun onTriggered(actor: Actor) {}
}