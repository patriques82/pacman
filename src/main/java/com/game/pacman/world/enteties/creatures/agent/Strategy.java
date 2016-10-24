package com.game.pacman.world.enteties.creatures.agent;

public abstract class Strategy {
	
	protected Agent agent;
	protected int[][] matrix; // inverted: 0 -> 1, 1 -> 0
	protected int width;
	protected int heigth;
	
	public Strategy(int[][] matrix) {
		assert(matrix.length > 1 && matrix[0].length > 1); // world must be larger than 1*1
		this.matrix = matrix;
		this.heigth = matrix.length;
		this.width = matrix[0].length;
	}

	protected void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	protected void updateMatrix(int currentX, int currentY, int playerX, int playerY) {
		assert(playerY >= 0 && playerY < heigth && playerX >= 0 && playerX < width);
		assert(currentY >= 0 && currentY < heigth && currentX >= 0 && currentX < width);
		matrix[playerY][playerX]++;
		matrix[currentY][currentX] = 1;
	}

	public abstract void findPath(int currentX, int currentY, int playerX, int playerY);

	public abstract int getYDir(int currentX, int currentY);

	public abstract int getXDir(int currentX, int currentY);


}
