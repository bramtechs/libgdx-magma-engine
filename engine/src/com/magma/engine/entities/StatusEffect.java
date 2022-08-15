package com.magma.engine.entities;

public class StatusEffect {	
	public int maxHealth;
	public int maxEnergy;
	public int maxFear;
	
	public int attack;
	public int defense;
	public int luck;
	public int speed;
	
	public StatusEffect add(StatusEffect with) {
		maxHealth += with.maxHealth;
		maxEnergy += with.maxEnergy;
		maxFear += with.maxFear;
		attack += with.attack;
		defense += with.defense;
		luck += with.luck;
		maxFear += with.maxFear;
		speed += with.speed;
		return this;
	}
	
	public StatusEffect subtract(StatusEffect with) {
		maxHealth -= with.maxHealth;
		maxEnergy -= with.maxEnergy;
		maxFear -= with.maxFear;
		attack -= with.attack;
		defense -= with.defense;
		luck -= with.luck;
		maxFear -= with.maxFear;
		speed -= with.speed;
		return this;
	}
	
	public static StatusEffect invincible() {
		final int MAX = 9999;
		StatusEffect stat = new StatusEffect();
		stat.maxHealth = MAX;
		stat.maxEnergy = MAX;
		stat.maxFear = MAX;
		stat.attack = MAX;
		stat.defense = MAX;
		stat.luck = MAX;
		stat.maxFear = MAX;
		stat.speed = 4;
		return stat;
	}

	@Override
	public String toString() {
		return "EN=" + maxHealth + ", CA=" + maxEnergy + ", NU=" + maxFear
				+ ", AT=" + attack + ", DEF=" + defense + ", LU=" + luck + ",SP="
				+ speed;
	}
}
