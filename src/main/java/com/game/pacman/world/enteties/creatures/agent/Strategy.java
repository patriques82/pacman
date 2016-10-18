package com.game.pacman.world.enteties.creatures.agent;

public abstract class Strategy {
	
	protected int[][] matrix;
	protected int width;
	protected int heigth;

	public void setMatrix(final int[][] matrix) {
		if(this.matrix == null) {
			assert(matrix.length > 1 && matrix[0].length > 1); // world must be larger than 1*1
			this.matrix = matrix;
			this.heigth = matrix.length;
			this.width = matrix[0].length;
		}
	}

	public abstract void findPath(int currentX, int currentY, int playerX, int playerY);

	public abstract int getYDir(int currentX, int currentY);

	public abstract int getXDir(int currentX, int currentY);

	public abstract void setAgent(Agent agent);


}
