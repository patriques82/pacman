package com.game.pacman.world.enteties.creatures.agent;

public class BreadCrumbsStrategy extends Strategy {
	
	private int width;
	private int heigth;

	private enum Direction {NORTH, SOUTH, EAST, WEST};

	private int[][] breadCrumbs; // 0 -> 1, 1 -> 0 (0 unreachable)

	public BreadCrumbsStrategy(final int[][] matrix) {
		breadCrumbs = invert(matrix);
		heigth = matrix.length;
		width = matrix[0].length;
	}

	@Override
	public void findPath(int currentX, int currentY, int playerX, int playerY) {
		breadCrumbs[playerY][playerX]++;
		breadCrumbs[currentY][currentX] = Math.max(1, breadCrumbs[currentY][currentX]-1);
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
		int[] dirs = {0, 0, 0, 0}; //north, south, east, west;
		if(currentY-1 >= 0)
			dirs[0] = breadCrumbs[currentY-1][currentX];
		if(currentY+1 < heigth)
			dirs[1] = breadCrumbs[currentY+1][currentX];
		if(currentX+1 < width)
			dirs[2] = breadCrumbs[currentY][currentX+1];
		if(currentX-1 >= 0)
			dirs[3] = breadCrumbs[currentY][currentX-1];
		return Direction.values()[maxIndex(dirs)];
	}

	private int maxIndex(int[] arr) {
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if(arr[i] > arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	int[][] invert(int[][] matrix) {
		int[][] invertedMatrix = new int[matrix.length][matrix[0].length];
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				invertedMatrix[y][x] = (matrix[y][x] ^ 1); // invert
			}
		}
		return invertedMatrix;
	}

}
