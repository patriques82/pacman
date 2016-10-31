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
	
	protected void updateMatrix(int logicalX, int logicalY, int logicalPlayerX, int logicalPlayerY) {
		assert(logicalPlayerY >= 0 && logicalPlayerY < heigth && logicalPlayerX >= 0 && logicalPlayerX < width);
		assert(logicalY >= 0 && logicalY < heigth && logicalX >= 0 && logicalX < width);
		matrix[logicalPlayerY][logicalPlayerX]++;
		matrix[logicalY][logicalX] = 1;
	}

	public abstract void findPath(float startX, float startY, float destX, float destY);

	public abstract int getYDir(float x, float y);

	public abstract int getXDir(float x, float y);


}
