package com.game.pacman.world.enteties.creatures.agent;

public class Agent {
	private Strategy strategy;
	
	public Agent(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void computeDirection(float startX, float startY, float destX, float destY) {
		strategy.findPath(startX, startY, destX, destY);
	}
	
	public boolean pressUp(float x, float y) {
		return strategy.getYDir(y) == -1;
	}

	public boolean pressDown(float x, float y) {
		return strategy.getYDir(y) == 1;
	}

	public boolean pressLeft(float x, float y) {
		return strategy.getXDir(x) == -1;
	}

	public boolean pressRight(float x, float y) {
		return strategy.getXDir(x) == 1;
	}

}
