package com.game.pacman.world.enteties.creatures.agent;

import com.game.pacman.world.enteties.creatures.agent.astar.Astar;

public class FollowingStrategy implements Strategy {
	
	private int[] path = {10, 11, 12, 16, 20};
	private int[][] tiles;
	private int pathPosition;
	
	public FollowingStrategy(int[][] tiles) {
		this.tiles = tiles;
		pathPosition = 0;
	}

	@Override
	public void findPath(float startX, float startY, float destX, float destY) {
		if(pathPosition == path.length-1) {
			path = Astar.calculatePath(tiles, startX, startY, destX, destY);
			pathPosition = 0;
		}
	}

	@Override
	public int getXDir(float currentX) {
		int cell = path[pathPosition];
		float destX = getXCoord(cell);
		return (destX > currentX) ? 1 : ((destX < currentX) ? -1 : 0);   
	}

	private float getXCoord(int cell) {
		return (cell % tiles[0].length) - 1;
	}

	@Override
	public int getYDir(float currentY) {
		int cell = path[pathPosition];
		float destY = getYCoord(cell);
		return (destY > currentY) ? 1 : ((destY < currentY) ? -1 : 0);   
	}

	private float getYCoord(int cell) {
		return cell / tiles[0].length;
	}

}
