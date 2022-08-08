package com.magma.engine.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.Array
import com.magma.engine.MagmaGame
import com.magma.engine.maps.MapStage

object MapLoader {
    private val maps: Array<MapStage> = Array()

	fun loadTilemap(mapName: String): TiledMap {
        // autofill mapPath handle
        val fullPath = MagmaGame.assetFolder + "maps/" + mapName + ".tmx"
        Gdx.app.log("MapStage", "Loading map $fullPath")

        // check if file exists
        require(Gdx.files.internal(fullPath).exists()) { "Map $fullPath does not exist!" }
        return TmxMapLoader().load(fullPath)
    }

    fun getRegisteredMap(id: Int): MapStage {
        require(!(id > maps.size || id < 0)) { "No registered map with id " + id + " (total maps " + maps.size + ")" }
        return maps[id - 1]
    }
}