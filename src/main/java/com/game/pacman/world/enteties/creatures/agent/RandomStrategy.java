package com.game.pacman.world.enteties.creatures.agent;

public class RandomStrategy extends Strategy {
	
	private int lastX, lastXDir;
	private int lastY, lastYDir;

	private long lastTime;
	private double threshold;
	private static float DPS = 2f; // decisions per second

	public RandomStrategy(final int[][] matrix) {
		super(matrix);
		lastXDir = 0;
		lastYDir = 0;
		lastTime = System.nanoTime();
		threshold = 1000_000_000L / DPS;
	}

	@Override
	public void findPath(int currentX, int currentY, int playerX, int playerY) {
		lastX = currentX;
		lastY = currentY;
		updateMatrix(currentX, currentY, playerX, playerY);
		if(maxNeighbor(currentX, currentY) > 1)
			agent.setStrategy(new BreadCrumbsStrategy(matrix));
	}

	private int maxNeighbor(int currentX, int currentY) {
		int max = 0;
		max = Math.max(max, matrix[currentY-1][currentX]); // up
		max = Math.max(max, matrix[currentY][currentX+1]); // right
		max = Math.max(max, matrix[currentY+1][currentX]); // down
		max = Math.max(max, matrix[currentY][currentX-1]); // left
		return max;
	}

	@Override
	public int getYDir(int currentX, int currentY) {
		long now = System.nanoTime();
		boolean decisionTime = threshold < (now - lastTime); // increase closer to threshold
		if(decisionTime) {
			if(currentY == lastY) { // is stuck?
				lastYDir = computeYDir(currentX, currentY);
			}
			lastTime = now;
		}
		return lastYDir;
	}

	@Override
	public int getXDir(int currentX, int currentY) {
		long now = System.nanoTime();
		boolean decisionTime = threshold < (now - lastTime); // increase closer to threshold
		if(decisionTime) {
			if(currentX == lastX) { // is stuck?
				lastXDir = computeXDir(currentX, currentY);
			}
			lastTime = now;
		}
		return lastXDir;
	}

	int computeYDir(int currentX, int currentY) {
		double rand = Math.random() * 10;
		int up = (currentY - 1 >= 0) ? matrix[currentY-1][currentX] : 0;
		int down = (currentY + 1 < heigth) ? matrix[currentY+1][currentX] : 0; 
		if(up == 0 && down > 0) // down
			return 1;
		else if(up > 0 && down == 0) // up
			return -1;
		else // both
			return (rand <= 5.0) ? -1 : 1;
	}

	int computeXDir(int currentX, int currentY) {
		double rand = Math.random() * 10;
		int left = (currentX - 1 >= 0) ? matrix[currentY][currentX-1] : 0;
		int right = (currentX + 1 < width) ? matrix[currentY][currentX+1] : 0; 
		if(left == 0 && right > 0) // right
			return 1;
		else if(left > 0 && right == 0) // left
			return -1;
		else // both
			return (rand <= 5.0) ? -1 : 1;
	}

}
