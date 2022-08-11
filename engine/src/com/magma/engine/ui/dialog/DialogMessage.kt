package com.magma.engine.ui.dialog

class DialogMessage {
    val style: DialogStyle
    val message: String

    constructor(key: Int, style: DialogStyle) {
        this.style = style
        message = PLACEHOLDER
    }

    constructor(key: Int) {
        message = PLACEHOLDER
        style = DialogStyle.Basic
    }

    constructor(message: String, style: DialogStyle) {
        this.message = message
        this.style = style
    }

    constructor(message: String) {
        this.message = message
        style = DialogStyle.Basic
    }

    companion object {
        const val PLACEHOLDER = "No dialog found! Whoops..."
    }
}