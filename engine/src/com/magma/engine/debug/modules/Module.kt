package com.magma.engine.debug.modules

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel

abstract class Module(title: String) : JPanel() {
    init {
        border = BorderFactory.createTitledBorder(title)
        this.layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        name = title
    }

    abstract fun update()

    protected fun format(name: String, value: Any): String {
        return "$name: $value"
    }
}