package com.game.pacman.world.enteties.creatures.agent;

public class RandomStrategy implements Strategy {
	
	private Agent agent;
	private int lastX, lastXDir;
	private int lastY, lastYDir;

	private final int[][] matrix;
	private int width;
	private int heigth;

	private long lastTime;
	private double threshold;
	private static float DPS = 2f; // decisions per second

	public RandomStrategy(final int[][] matrix) {
		lastXDir = 0;
		lastYDir = 0;
		this.matrix = matrix;
		heigth = matrix.length;
		width = matrix[0].length;
		lastTime = System.nanoTime();
		threshold = 1000_000_000L / DPS;
	}

	@Override
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public void findPath(int startX, int startY, int destX, int destY) {
		lastX = startX;
		lastY = startY;
	}

	@Override
	public int getYDir(int currentX, int currentY) {
		long now = System.nanoTime();
		boolean decisionTime = threshold < (now - lastTime); // increase closer to threshold
		if(decisionTime) {
//			if(currentX == lastX && currentY == lastY) { // is stuck?
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
//			if(currentX == lastX && currentY == lastY) { // is stuck?
			if(currentX == lastX) { // is stuck?
				lastXDir = computeXDir(currentX, currentY);
			}
			lastTime = now;
		}
		return lastXDir;
	}

	int computeYDir(int currentX, int currentY) {
		double rand = Math.random() * 10;
		int up = (currentY - 1 >= 0) ? matrix[currentY-1][currentX] : 1;
		int down = (currentY + 1 < heigth) ? matrix[currentY+1][currentX] : 1; 
		return randomAvailableDir(up, down, rand);
	}

	int computeXDir(int currentX, int currentY) {
		double rand = Math.random() * 10;
		int left = (currentX - 1 >= 0) ? matrix[currentY][currentX-1] : 1;
		int right = (currentX + 1 < width) ? matrix[currentY][currentX+1] : 1; 
		return randomAvailableDir(left, right, rand);
	}

	private int randomAvailableDir(int negDir, int posDir, double rand) {
		// 0 -> non available, 1 -> negDir available, 2 -> posDir available, 3 -> both
		int available = (3 - negDir) - ((posDir != negDir) ? 1 : (posDir + negDir)); 
		if(available == 0)
			return 0;
		else if(available == 1)
			return (rand < 5) ? 1 : 0;
		else if(available == 2)
			return (rand < 5) ? -1 : 0;
		else
			return (rand < 3.33) ? -1 : (rand > 6.66) ? 1 : 0;
	}

}
