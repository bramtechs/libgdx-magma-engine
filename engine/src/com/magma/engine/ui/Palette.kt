package com.magma.engine.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array

class Palette(val bgColor: Color, val fgColor: Color) {
    val id: Int = latestID

    init {
        latestID++
    }

    companion object {
        private val palettes: Array<Palette> = Array()
        private var latestID = 0

        init {
            // add default palette
            val bg = Color(59 / 255f, 85 / 255f, 112 / 255f, 1f)
            val fg = Color(144 / 255f, 182 / 255f, 222 / 255f, 1f)
            val defaultPalette = Palette(bg, fg)
            registerPalette(defaultPalette)
        }


        /**
         * Adds a Palette instance to the engine. Palettes can be called back with
         * getPalette(int id)
         * MagmaEngine has a default palette with index 0.
         *
         * @param palette should be instantiated in static fields
         * @return the palette with the id that got registered
         */
        fun registerPalette(palette: Palette): Int {
            palettes.add(palette)
            return palette.id
        }

        fun getPalette(id: Int): Palette {
            require(id < latestID) { "No palette registered with id $id" }
            return palettes[id]
        }

        val default: Palette
            get() = palettes.first()
    }
}