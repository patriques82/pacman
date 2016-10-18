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
	
	public void computeDirection(int startX, int startY, int destX, int destY) {
		strategy.findPath(startX, startY, destX, destY);
	}
	
	public boolean pressUp(int x, int y) {
		return strategy.getYDir(x,y) == -1;
	}

	public boolean pressDown(int x, int y) {
		return strategy.getYDir(x,y) == 1;
	}

	public boolean pressLeft(int x, int y) {
		return strategy.getXDir(x,y) == -1;
	}

	public boolean pressRight(int x, int y) {
		return strategy.getXDir(x, y) == 1;
	}

}
