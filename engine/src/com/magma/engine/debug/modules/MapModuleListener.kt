package com.magma.engine.debug.modules

interface MapModuleListener {
    fun unloadMap()
    fun requestMap(name: String)
    fun setTriggersVisible(on: Boolean)
}