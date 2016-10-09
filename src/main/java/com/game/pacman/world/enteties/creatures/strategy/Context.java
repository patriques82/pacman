package com.game.pacman.world.enteties.creatures.strategy;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void computeDirection(int[][] tiles, float startX, float startY, float destX, float destY) {
		// TODO Auto-generated method stub
	}
	
	public boolean pressUp() {
		return false;
	}

	public boolean pressDown() {
		return false;
	}

	public boolean pressLeft() {
		return true;
	}

	public boolean pressRight() {
		return false;
	}



}
