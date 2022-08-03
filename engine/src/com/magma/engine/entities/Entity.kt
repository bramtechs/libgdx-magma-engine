package com.magma.engine.entities

import com.badlogic.gdx.Gdx

open class Entity {
    var health = 0
    var energy = 0
    var fear = 0
    val stats: StatusEffect

    // makes an immortal entity
    constructor() {
        stats = StatusEffect()
    }

    constructor(stats: StatusEffect) {
        this.stats = StatusEffect()
        addStatusEffect(stats)
        replenish()
        if (stats.speed == 0) {
            Gdx.app.log("Entity", "NOTE: Entities " + javaClass.simpleName + " speed's is zero, so it will not move")
        }
    }

    fun addStatusEffect(stat: StatusEffect) {
        stats.add(stat)
    }

    fun removeStatusEffect(stat: StatusEffect) {
        stats.subtract(stat)
        //TODO: add death
    }

    fun damage(amount: Int) {
        health -= amount
    }

    fun replenish() {
        health = stats.maxHealth
        energy = stats.maxEnergy
        fear = 0
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName} | HP: $health | EN: $energy | FE: $fear | $stats"
    }

    companion object {
        val immortal : Entity
            get() {
                val e = Entity()
                e.addStatusEffect(StatusEffect.invincible)
                e.replenish()
                return e
            }

        fun createAdvanced(effect: StatusEffect): Entity {
            val e = Entity()
            e.addStatusEffect(effect)
            e.replenish()
            return e
        }

        // spacegame specific stuff, don't worry about this
        fun createSpace(health: Int, energy: Int, defense: Int): Entity {
            val effect = StatusEffect()
            effect.defense = defense
            effect.maxHealth = health
            effect.maxEnergy = energy
            effect.maxFear = 100
            val e = Entity()
            e.addStatusEffect(effect)
            e.replenish()
            return e
        }
    }
}