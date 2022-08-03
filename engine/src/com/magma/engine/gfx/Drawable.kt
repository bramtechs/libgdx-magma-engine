package com.magma.engine.gfx

import com.badlogic.gdx.graphics.g2d.Batch

interface Drawable {
    fun act(delta: Float)
    fun draw(batch: Batch, parentAlpha: Float)
}