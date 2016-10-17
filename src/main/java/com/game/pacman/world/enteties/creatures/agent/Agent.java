package com.game.pacman.world.enteties.creatures.agent;

public class Agent {
	private Strategy strategy;
	
	public Agent(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void computeDirection(int startX, int startY, int destX, int destY) {
		strategy.findPath(startX, startY, destX, destY);
	}
	
	public boolean pressUp(int x, int y) {
		return strategy.getYDir(y) == -1;
	}

	public boolean pressDown(int x, int y) {
		return strategy.getYDir(y) == 1;
	}

	public boolean pressLeft(int x, int y) {
		return strategy.getXDir(x) == -1;
	}

	public boolean pressRight(int x, int y) {
		return strategy.getXDir(x) == 1;
	}

}
