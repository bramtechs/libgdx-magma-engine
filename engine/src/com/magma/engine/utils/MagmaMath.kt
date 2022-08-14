package com.magma.engine.utils

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.maps.triggers.CustomTrigger

object MagmaMath {
    fun scaleRectangle(rect: Rectangle, scale: Float): Rectangle {
        rect.x *= scale
        rect.y *= scale
        rect.width *= scale
        rect.height *= scale
        return rect
    }

	fun padString(input: String, padSymbol: Char, totalLength: Int, align: PadAlign?): String {
        // check if text to short
        if (input.length >= totalLength) {
            return input
        }
        var result: String
        when (align) {
            PadAlign.CENTER -> throw IllegalArgumentException("Alignment not implemented")
            PadAlign.RIGHT -> {
                result = input
                var i = 0
                while (i < totalLength - input.length) {
                    result += padSymbol
                    i++
                }
            }

            PadAlign.LEFT -> {
                result = ""
                var i = 0
                while (i < totalLength - input.length) {
                    result += padSymbol
                    i++
                }
                result += input
            }

            else -> throw IllegalArgumentException("Alignment not implemented")
        }
        return result
    }

    fun getDistanceToCircle(x: Float, y: Float, circle: Circle): Float {
        return (circle.x - x) * (circle.x - x) + (circle.y - y) * (circle.y - y)
    }

    fun shapesOverlap(a: Shape2D?, b: Shape2D?): Boolean { // this is probably dumb
        if (a is Circle) {
            val circle = a
            if (b is Circle) {
                return circle.overlaps(b as Circle?)
            }
            if (b is Rectangle) {
                return Intersector.overlaps(circle, b as Rectangle?)
            }
        } else if (a is Rectangle) {
            val rect = a
            if (b is Rectangle) {
                return Intersector.overlaps(rect, b as Rectangle?)
            }
            if (b is Circle) {
                return Intersector.overlaps(b as Circle?, rect)
                //return rectOverlapsCircle(rect, (Circle) b);
            }
        }
        throw IllegalArgumentException("Invalid shapes")
    }

    fun extractShape(actor: Actor): Shape2D {
        val shape: Shape2D = if (actor is CustomTrigger) {
            (actor as CustomTrigger).shape
        } else {
            Rectangle(actor.x, actor.y, actor.width, actor.height) // TODO pool?
        }
        return shape
    }

    enum class PadAlign {
        LEFT, CENTER, RIGHT
    }
}