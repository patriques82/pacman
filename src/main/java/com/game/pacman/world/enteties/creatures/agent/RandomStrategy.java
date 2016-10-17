package com.game.pacman.world.enteties.creatures.agent;

public class RandomStrategy implements Strategy {
	

	@Override
	public void findPath(int startX, int startY, int destX, int destY) {
		// NO-OP
	}

	@Override
	public int getYDir(int currentY) {
		double rand = Math.random() * 10;
		return (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
	}

	@Override
	public int getXDir(int currentX) {
		double rand = Math.random() * 10;
		return (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
	}

}
