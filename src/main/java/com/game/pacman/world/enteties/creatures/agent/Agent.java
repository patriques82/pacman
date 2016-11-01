package com.game.pacman.world.enteties.creatures.agent;

import com.game.pacman.world.enteties.creatures.CreatureEntity;

public class Agent {
	private Strategy strategy;
	
	public Agent(Strategy strategy) {
		setStrategy(strategy);
	}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
		this.strategy.setAgent(this);
	}
	
	public void computeDirection(CreatureEntity creature, float destX, float destY) {
		strategy.findPath(creature, destX, destY);
	}
	
	public boolean pressUp(CreatureEntity creature) {
		return strategy.getYDir(creature) == -1;
	}

	public boolean pressDown(CreatureEntity creature) {
		return strategy.getYDir(creature) == 1;
	}

	public boolean pressLeft(CreatureEntity creature) {
		return strategy.getXDir(creature) == -1;
	}

	public boolean pressRight(CreatureEntity creature) {
		return strategy.getXDir(creature) == 1;
	}

}
