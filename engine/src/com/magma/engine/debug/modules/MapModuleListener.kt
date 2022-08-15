package com.magma.engine.debug.modules

interface MapModuleListener {
    fun unloadMap()
    fun loadMap(name: String)
    fun setTriggersVisible(on: Boolean)
}