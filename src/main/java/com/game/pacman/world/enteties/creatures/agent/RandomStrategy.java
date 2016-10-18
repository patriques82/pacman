package com.game.pacman.world.enteties.creatures.agent;

public class RandomStrategy extends Strategy {
	
	private Agent agent;
	private int lastX, lastXDir;
	private int lastY, lastYDir;

	private long lastTime;
	private double threshold;
	private static float DPS = 2f; // decisions per second

	public RandomStrategy() {
		lastXDir = 0;
		lastYDir = 0;
		lastTime = System.nanoTime();
		threshold = 1000_000_000L / DPS;
	}

	@Override
	public void findPath(int startX, int startY, int destX, int destY) {
		lastX = startX;
		lastY = startY;
	}

	@Override
	public int getYDir(int currentX, int currentY) {
		long now = System.nanoTime();
		boolean isTime = threshold < (now - lastTime); // increase closer to threshold

		if(isTime) {
			if(currentX == lastX && currentY == lastY) {
				double rand = Math.random() * 10;
				int currentYDir = (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
				if(currentYDir != lastYDir) {
					lastYDir = currentYDir;
				}
			}
			lastTime = now;
		}
		return lastYDir;
	}

	@Override
	public int getXDir(int currentX, int currentY) {
		long now = System.nanoTime();
		boolean isTime = threshold < (now - lastTime); // increase closer to threshold

		if(isTime) {
			if(currentX == lastX && currentY == lastY) {
				double rand = Math.random() * 10;
				int currentXDir = (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
				if(currentXDir != lastXDir) {
					lastXDir = currentXDir;
				}
			}
			lastTime = now;
		}
		return lastXDir;
	}

	@Override
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
