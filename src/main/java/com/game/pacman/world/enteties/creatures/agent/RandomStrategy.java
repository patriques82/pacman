package com.game.pacman.world.enteties.creatures.agent;

public class RandomStrategy implements Strategy {
	

	@Override
	public void findPath(float startX, float startY, float destX, float destY) {
		// NO-OP
	}

	@Override
	public int getYDir(float currentY) {
		double rand = Math.random() * 10;
		return (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
	}

	@Override
	public int getXDir(float currentX) {
		double rand = Math.random() * 10;
		return (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
	}

}
