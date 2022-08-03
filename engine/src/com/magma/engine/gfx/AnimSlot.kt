package com.magma.engine.gfx

// A convoluted way of working that uses indices instead of comparing strings = more performance
class AnimSlot(val name: String, val fps: Float) {
    val index: Int = nextIndex

    init {
        nextIndex++
    }

    companion object {
        private var nextIndex = 0
        fun of(name: String, fps: Float): AnimSlot {
            return AnimSlot(name, fps)
        }
        fun of(name: String): AnimSlot {
            return AnimSlot(name, 2f)
        }
    }
}