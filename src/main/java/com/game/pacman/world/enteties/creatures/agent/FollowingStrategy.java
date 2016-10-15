package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;

import com.game.pacman.world.enteties.creatures.agent.astar.Astar;

public class FollowingStrategy implements Strategy {
	
	private Astar astar;
	private List<Integer> path;
	private int[][] tiles;
	private int pathPosition;
	
	public FollowingStrategy(int[][] tiles) {
		assert(tiles.length > 1 && tiles[0].length > 1); // world must be larger than 1*1
		astar = new Astar(tiles);
		this.tiles = tiles;
		pathPosition = 0;
	}

	@Override
	public void findPath(float currentX, float currentY, float playerX, float playerY) {
		pathPosition = pathPosition % path.size();
		if(pathPosition == 0) {
			path = astar.calculatePath(currentX, currentY, playerX, playerY);
		}
	}

	@Override
	public int getXDir(float currentX) {
		int cell = path.get(pathPosition);
		float destX = getXCoord(cell);
		return (destX > currentX) ? 1 : ((destX < currentX) ? -1 : 0);   
	}

	private float getXCoord(int cell) {
		return (cell % tiles[0].length) - 1;
	}

	@Override
	public int getYDir(float currentY) {
		int cell = path.get(pathPosition);
		float destY = getYCoord(cell);
		return (destY > currentY) ? 1 : ((destY < currentY) ? -1 : 0);   
	}

	private float getYCoord(int cell) {
		return cell / tiles[0].length;
	}

}
