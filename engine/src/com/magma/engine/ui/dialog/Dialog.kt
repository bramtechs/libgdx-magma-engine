package com.magma.engine.ui.dialog

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.scenes.scene2d.ui.TextArea
import com.badlogic.gdx.utils.Queue
import com.magma.engine.assets.MagmaLoader
import com.magma.engine.assets.Shapes
import com.magma.engine.debug.MagmaLogger
import com.magma.engine.stages.ViewportContext

class Dialog(width: Int, height: Int) : Stack() {
    private val text: TextArea = TextArea("_", MagmaLoader.debugSkin)

    init {
        x = (ViewportContext.width - width) * 0.5f
        y = -ViewportContext.width + 10
        setSize(width.toFloat(), height.toFloat())
        Gdx.app.log(
            "Dialog",
            "Dialog created at bounds: " + x + " , " + y + " (" + getWidth() + " x " + getHeight() + ")"
        )
        addActor(text)
    }

    override fun act(delta: Float) {
        if (messages.isEmpty) {
            return
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (!isLocked) {
                messages.removeFirst()
            } else {
                Gdx.app.log("Dialog", "Dialog is locked")
                return
            }
        }
        toFront()
        super.act(delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (messages.isEmpty) {
            return
        }
        val message = messages.first()
        val shapes = Shapes.instance
        if (message.style == DialogStyle.Basic) {
            shapes.setColor(Color.BLACK)
            shapes.filledRectangle(x, y, width, height)
            shapes.setColor(Color.WHITE)
            shapes.rectangle(x, y, width, height, 3f)
            text.text = message.message
        } else {
            throw IllegalArgumentException("No dialog style defined!")
        }
        super.draw(batch, parentAlpha)
    }

    companion object {
        private val messages: Queue<DialogMessage> = Queue()
        private var isLocked = false
        var locked: Boolean
            get() = isLocked
            set(value) {if (value) MagmaLogger.log(this,"Dialog locked") else MagmaLogger.log(this,"Dialog unlocked")field =
                isLocked = value
            }

        val isSpeaking: Boolean
            get() = messages.isEmpty

        fun speak(message: DialogMessage) {
            Gdx.app.log(">", message.message)
            messages.addLast(message)
        }

        fun speak(message: String) {
            speak(DialogMessage(message))
        }

        fun speak(key: Int) {
            speak(DialogMessage.PLACEHOLDER)
        }
    }
}