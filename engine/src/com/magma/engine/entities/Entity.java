package com.magma.engine.entities;

import com.badlogic.gdx.Gdx;

public class Entity {
	
	private int health;
	private int energy;
	private int fear;
	
	public final StatusEffect stats;
	
	// makes an immortal entity
	public Entity() {
		this.stats = new StatusEffect();
	}

    public static Entity createImmortal(){
        Entity e = new Entity();
		e.addStatusEffect(StatusEffect.invincible());
		e.replenish();
        return e;
    }

    public static Entity createAdvanced(StatusEffect effect){
        Entity e = new Entity();
		e.addStatusEffect(effect);
		e.replenish();
        return e;
    }

    // spacegame specific stuff, don't worry about this
    public static Entity createSpace(int health, int energy, int defense){
        StatusEffect effect = new StatusEffect();
        effect.defense = defense;
        effect.maxHealth = health;
        effect.maxEnergy= energy;
        effect.maxFear = 100;

        Entity e = new Entity();
		e.addStatusEffect(effect);
		e.replenish();
        return e;
    }
	
	public Entity(StatusEffect stats) {
		this.stats = new StatusEffect();
		
		addStatusEffect(stats);
		replenish();
		
		if (stats.speed == 0) {
			Gdx.app.log("Entity", "NOTE: Entities " + getClass().getSimpleName() + " speed's is zero, so it will not move");
		}
	}
	
	public void addStatusEffect(StatusEffect stat) {
		stats.add(stat);
	}
	
	public void removeStatusEffect(StatusEffect stat) {
		stats.subtract(stat);
        //TODO: add death
	}
	
    public void damage(int amount){
        health -= amount;
    }

	public void replenish() {
		this.health = stats.maxHealth;
		this.energy = stats.maxEnergy;
		this.fear = 0;
	}
	
	public StatusEffect getStats() {
		return stats;
	}
	
	public String toString() {
		return "HP: " + health + "\nEN: " + energy + "\nFE: " + fear + "\n" + getStats().toString();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getFear() {
		return fear;
	}

	public void setFear(int fear) {
		this.fear = fear;
	}
}
