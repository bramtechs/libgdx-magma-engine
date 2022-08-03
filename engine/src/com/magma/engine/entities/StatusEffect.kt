package com.magma.engine.entities

class StatusEffect {
    var maxHealth = 0
    var maxEnergy = 0
    var maxFear = 0
    var attack = 0
    var defense = 0
    var luck = 0
    var speed = 0

    fun add(with: StatusEffect): StatusEffect {
        maxHealth += with.maxHealth
        maxEnergy += with.maxEnergy
        maxFear += with.maxFear
        attack += with.attack
        defense += with.defense
        luck += with.luck
        maxFear += with.maxFear
        speed += with.speed
        return this
    }

    fun subtract(with: StatusEffect): StatusEffect {
        maxHealth -= with.maxHealth
        maxEnergy -= with.maxEnergy
        maxFear -= with.maxFear
        attack -= with.attack
        defense -= with.defense
        luck -= with.luck
        maxFear -= with.maxFear
        speed -= with.speed
        return this
    }

    override fun toString(): String {
        return ("EN=" + maxHealth + ", CA=" + maxEnergy + ", NU=" + maxFear
                + ", AT=" + attack + ", DEF=" + defense + ", LU=" + luck + ",SP="
                + speed)
    }

    companion object {
        val invincible: StatusEffect
            get() {
                val max = 9999
                val stat = StatusEffect()
                stat.maxHealth = max
                stat.maxEnergy = max
                stat.maxFear = max
                stat.attack = max
                stat.defense = max
                stat.luck = max
                stat.maxFear = max
                stat.speed = 4
                return stat
            }
    }
}