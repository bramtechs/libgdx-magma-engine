package com.magma.engine.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.utils.Json
import com.magma.engine.saving.SaveFile

object GameInput {
    private val keys: HashMap<String, Int> = HashMap()

    private val jsonFile: FileHandle
        get() = SaveFile.getPersistentPath().child("keybindings.json")

    init {
        // get rid of the cursor, kinda complicated
        val nothing = Pixmap(4, 4, Pixmap.Format.RGBA8888)
        nothing.setColor(Color.WHITE)
        nothing.drawRectangle(0, 0, 4, 4)
        val cursor = Gdx.graphics.newCursor(nothing, 0, 0)
        Gdx.graphics.setCursor(cursor)
        nothing.dispose()
    }

    private fun loadKeys(): HashMap<String, Int> {
        // upload keys from config into hashmap
        if (jsonFile.exists()) {
            try {
                val data = Json().fromJson(HashMap::class.java, jsonFile) as HashMap<String, Int>
                keys.putAll(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Gdx.app.log("GameInput", "No keybindings saved yet...")
        }
        return keys
    }

    // TODO: not cross platform, write a GameInputDesktop class
    fun flush() {
        val json = Json().prettyPrint(keys)
        jsonFile.writeString(json, false)
        Gdx.app.log("GameInput", "Keyboard bindings updated")
    }

    @JvmStatic
    fun registerKey(name: String, defaultKey: Int): Int {
        if (keys.containsKey(name)) {
            Gdx.app.log("GameInput", "Registered key $defaultKey with name $name")
            keys[name] = defaultKey
            flush()
        }
        return keys[name]!!
    }

    fun reset() {
        Gdx.app.log("GameInput", "Wiped keybindings")
        jsonFile.delete()
    }
}