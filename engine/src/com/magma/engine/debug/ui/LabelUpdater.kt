package com.magma.engine.debug.ui

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.ui.Label

/***
 * A helper Action for having labels automatically update
 */
abstract class LabelUpdater(private var updater: () -> String) : Action() {
    override fun act(delta: Float): Boolean {
        val label = getActor() as Label
        label.setText(updater.invoke())
        return false
    }
}