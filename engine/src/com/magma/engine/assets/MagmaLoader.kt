package com.magma.engine.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Array
import com.magma.engine.MagmaGame

object MagmaLoader : AssetManager() {

    val skin: Skin = Skin();

    init {
        MagmaGame.disposeOnExit(this)
    }

    fun <T> request(fileName: String, type: Class<T>, engine: Boolean): T {
        var name = fileName
        if (!engine) {
            name = MagmaGame.assetPrefix + fileName
        }
        if (isLoaded(name)) {
            load(name, type)
            finishLoadingAsset<Any>(name) // hang game until loaded
        }
        return get(name)
    }

    fun <T> request(fileName: String, type: Class<T>): T {
        return request(fileName, type, false)
    }

    fun loadAtlas(vararg atlasNames: String) {
        for (name in atlasNames) {
            var convertedName = name
            if (name.endsWith(".atlas")) {
                convertedName += ".atlas"
            }
            val atlas = request(convertedName, TextureAtlas::class.java)
            skin.addRegions(atlas)
        }
    }

    fun getNinePatch(name: String): NinePatch {
        return skin.getPatch(name)
    }

    fun getAtlasRegion(name: String): AtlasRegion? {
        val regions = getAtlasRegions(name)
        if (regions == null || regions.isEmpty) {
            Gdx.app.error("AssetLoader", "Atlas region of name $name not found! (Have you loaded the atlas?)")
            return null
        }
        return regions.first()
    }

    // TODO might be slow, implement caching
    fun getAtlasRegions(name: String): Array<AtlasRegion>? {
        val atlases = Array<TextureAtlas>()
        getAll(TextureAtlas::class.java, atlases)
        for (atlas in atlases.iterator()) {
            val regions = atlas.findRegions(name)
            if (regions != null) {
                require(!regions.isEmpty) { "No atlas region of name $name found!" }
                return regions
            }
        }
        var message = "Atlas regions of name $name not found! (Have you loaded the atlas?)"
        message += "\nChoose between: "
        for (region in atlasRegions) {
            message += region.name + " "
        }
        Gdx.app.error("AssetLoader", message)
        return null
    }

    @JvmStatic
    val atlasRegions: Array<AtlasRegion>
        get() {
            val atlases = Array<TextureAtlas>()
            val regions = Array<AtlasRegion>()
            getAll(TextureAtlas::class.java, atlases)
            for (atlas in atlases.iterator()) {
                regions.addAll(atlas.regions)
            }
            return regions
        }

    @JvmStatic
    val allMapNames: ArrayList<String>
        get() {
            val list = ArrayList<String>()
            val mapFolder = Gdx.files.internal(MagmaGame.assetPrefix + "/maps")
            if (!mapFolder.exists()) {
                Gdx.app.log("AssetLoader", "Could not find the map folder.")
                return list
            }
            val items = mapFolder.list()
            for (item in items) {
                if (item.extension().equals("tmx", ignoreCase = true)) {
                    list.add(item.nameWithoutExtension())
                }
            }
            if (items.isEmpty()) {
                Gdx.app.log("AssetLoader", "No maps found in map folder.")
            }
            return list
        }

    val placeholderTexture: Texture
        get() = request("404.png", Texture::class.java, true)
    val debugSkin: Skin
        get() = request("skins/default/skin/uiskin.json", Skin::class.java, true)
}