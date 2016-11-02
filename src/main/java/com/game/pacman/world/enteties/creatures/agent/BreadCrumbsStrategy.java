package com.game.pacman.world.enteties.creatures.agent;

import com.game.pacman.world.enteties.creatures.CreatureEntity;
import com.game.pacman.world.tiles.Tile;

public class BreadCrumbsStrategy extends Strategy {
	
	private enum Direction {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
	private Direction maxDirection;

	public BreadCrumbsStrategy(final int[][] matrix) {
		super(matrix);
		maxDirection = Direction.NORTH;
	}

	@Override
	public void findPath(CreatureEntity creature, float playerX, float playerY) {
		updateMatrix(creature.getLogicalX(), creature.getLogicalY(), (int) playerX/Tile.TILESIZE, (int) playerY/Tile.TILESIZE);
		maxDirection = getDirectionOfMaxCrumbs(creature.getLogicalX(), creature.getLogicalY());
	}

	@Override
	public int getYDir(CreatureEntity creature) {
		switch(maxDirection) {
			case NORTH:
				return -1;
			case NORTHEAST:
				return -1;
			case NORTHWEST:
				return -1;
			case SOUTH:
				return 1;
			case SOUTHEAST:
				return 1;
			case SOUTHWEST:
				return 1;
			default:
				return 0; // x is max
		}
	}

	@Override
	public int getXDir(CreatureEntity creature) {
		switch(maxDirection) {
			case NORTHEAST:
				return 1;
			case EAST:
				return 1;
			case SOUTHEAST:
				return 1;
			case NORTHWEST:
				return -1;
			case WEST:
				return -1;
			case SOUTHWEST:
				return -1;
			default:
				return 0; // y is max
		}
	}

	private Direction getDirectionOfMaxCrumbs(int logicalX, int logicalY) {
		int[] dirs = {0, 0, 0, 0, 0, 0, 0, 0}; //north, northeast, east, southeast, south, southwest, west, northwest;
		if(logicalY-1 >= 0) {
			dirs[0] = matrix[logicalY-1][logicalX]; // north
			if(logicalX+1 < width)
				dirs[1] = matrix[logicalY-1][logicalX+1]; // northeast
			if(logicalX-1 >= 0)
				dirs[7] = matrix[logicalY-1][logicalX-1]; // northwest
		}
		if(logicalX+1 < width)
			dirs[2] = matrix[logicalY][logicalX+1]; // east
		if(logicalY+1 < heigth) {
			dirs[4] = matrix[logicalY+1][logicalX]; // south
			if(logicalX+1 < width)
				dirs[3] = matrix[logicalY+1][logicalX+1]; // southeast
			if(logicalX-1 >= 0)
				dirs[5] = matrix[logicalY+1][logicalX-1]; // southwest
		}
		if(logicalX-1 >= 0)
			dirs[6] = matrix[logicalY][logicalX-1]; // west

		int maxIndex = maxIndex(dirs);
		if(dirs[maxIndex] == 1) { // player not in sight
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
