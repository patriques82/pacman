package com.game.pacman.world.enteties.creatures.agent;

public class BreadCrumbsStrategy extends Strategy {
	
	private enum Direction {NORTH, EAST, SOUTH, WEST};

	public BreadCrumbsStrategy(final int[][] matrix) {
		super(matrix);
	}

	@Override
	public void findPath(int currentX, int currentY, int playerX, int playerY) {
		updateMatrix(currentX, currentY, playerX, playerY);
	}

	@Override
	public int getYDir(int currentX, int currentY) {
		Direction d = getDirectionOfMaxCrumbs(currentX, currentY);
		switch(d) {
			case NORTH:
				return -1;
			case SOUTH:
				return 1;
			default:
				return 0; // x is max
		}
	}

	@Override
	public int getXDir(int currentX, int currentY) {
		Direction d = getDirectionOfMaxCrumbs(currentX, currentY);
		switch(d) {
			case EAST:
				return 1;
			case WEST:
				return -1;
			default:
				return 0; // y is max
		}
	}

	private Direction getDirectionOfMaxCrumbs(int currentX, int currentY) {
		int[] dirs = {0, 0, 0, 0}; //north, east, south, west;
		if(currentY-1 >= 0)
			dirs[0] = matrix[currentY-1][currentX];
		if(currentX+1 < width)
			dirs[1] = matrix[currentY][currentX+1];
		if(currentY+1 < heigth)
			dirs[2] = matrix[currentY+1][currentX];
		if(currentX-1 >= 0)
			dirs[3] = matrix[currentY][currentX-1];
		int maxIndex = maxIndex(dirs);
		if(dirs[maxIndex] == 1) {
			agent.setStrategy(new RandomStrategy(matrix));
		}
		return Direction.values()[maxIndex];
	}

	private int maxIndex(int[] arr) {
		int maxIndex = 0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] >= arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

}
