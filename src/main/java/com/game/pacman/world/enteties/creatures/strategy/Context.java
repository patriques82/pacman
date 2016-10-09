package com.game.pacman.world.enteties.creatures.strategy;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void computeDirection(int[][] tiles, float startX, float startY, float destX, float destY) {
		// TODO Auto-generated method stub

	}
	
	public boolean directionUp() {
		return false;
	}

	public boolean directionDown() {
		return false;
	}

	public boolean directionLeft() {
		return true;
	}

	public boolean directionRight() {
		return false;
	}



}
