package com.game.pacman.world.enteties.creatures.agent;

public class Agent {
	private Strategy strategy;
	
	public Agent(Strategy strategy) {
		setStrategy(strategy);
	}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
		this.strategy.setAgent(this);
	}
	
	public void computeDirection(float startX, float startY, float destX, float destY) {
		strategy.findPath(startX, startY, destX, destY);
	}
	
	public boolean pressUp(float x, float y) {
		return strategy.getYDir(x,y) == -1;
	}

	public boolean pressDown(float x, float y) {
		return strategy.getYDir(x,y) == 1;
	}

	public boolean pressLeft(float x, float y) {
		return strategy.getXDir(x,y) == -1;
	}

	public boolean pressRight(float x, float y) {
		return strategy.getXDir(x,y) == 1;
	}

}
