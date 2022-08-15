package com.magma.engine.gfx

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.magma.engine.assets.MagmaLoader.getAtlasRegions
import com.magma.engine.assets.MagmaLoader.placeholderTexture
import com.magma.engine.utils.Time

open class AnimatedSprite(vararg slots: AnimSlot) : SpriteActor(Sprite(placeholderTexture)) {
    private val animations: HashMap<Int, Animation<AtlasRegion>> = HashMap()
    private var currentAnimation = 0
    private var isStopped = false

    init {
        for (slot in slots) {
            val regions = getAtlasRegions(slot.name)
            val anim = Animation(1f / slot.fps, regions)
            animations[slot.index] = anim
        }
    }

    fun setCurrentAnimation(slot: AnimSlot) {
        require(animations.containsKey(slot.index)) { "Cannot get animation of index " + slot.index + " (" + slot.name + ") Not registered!" }
        isStopped = false
        currentAnimation = slot.index
    }

    // TODO: Add more control
    fun setPlayMode(mode: PlayMode): AnimatedSprite {
        for (anim in animations.values) {
            anim.playMode = mode
        }
        return this
    }

    fun setPlayMode(mode: PlayMode, slot: AnimSlot): AnimatedSprite {
        require(animations.containsKey(slot.index)) { "Cannot get animation of index ${slot.index} (${slot.name})" }
        animations[slot.index]!!.playMode = mode
        return this
    }

    fun getPlayMode(slot: AnimSlot): PlayMode {
        require(animations.containsKey(slot.index)) { "Cannot get animation of index ${slot.index} (${slot.name})" }
        return animations[slot.index]!!.playMode
    }

    fun stop() {
        isStopped = true
    }

    override fun act(delta: Float) {
        var time = 0f
        if (!isStopped) {
            time = Time.time
        }
        val animation = animations[currentAnimation]
        val region = animation?.getKeyFrame(time)
        checkNotNull(animation) { "No animation set for ${javaClass.simpleName}" }

        // setRegion removes sprites flip, so we need to do this terribleness
        val origFlipX = isFlipX
        val origFlipY = isFlipY
        setSpriteRegion(region!!)
        setFlip(origFlipX, origFlipY)

        super.act(delta)
    }
}