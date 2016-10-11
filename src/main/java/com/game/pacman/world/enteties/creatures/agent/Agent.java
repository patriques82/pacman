package com.game.pacman.world.enteties.creatures.agent;

public class Agent {
	private Strategy strategy;
	
	public Agent(int[][] tiles, Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void computeDirection(float startX, float startY, float destX, float destY) {
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
