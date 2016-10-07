package com.game.pacman.world.enteties.creatures.intelligence;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void move() {
		strategy.findPath();
	}

}
