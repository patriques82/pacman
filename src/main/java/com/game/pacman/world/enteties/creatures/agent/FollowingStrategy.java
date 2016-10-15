package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;

import com.game.pacman.world.enteties.creatures.agent.astar.Astar;

public class FollowingStrategy implements Strategy {
	
	private Astar astar;
	private List<Integer> path;
	private int pathPosition;
	private int width;

	private Integer nextCell;
	private int nextX;
	private int nextY;
	
	public FollowingStrategy(Astar astar) {
		this.astar = astar;
		width = astar.getWidth();
		pathPosition = 0;
	}

	@Override
	public void findPath(float currentX, float currentY, float playerX, float playerY) {
		if(pathPosition == 0) {
			path = astar.calculatePath(currentX, currentY, playerX, playerY);
		}
		pathPosition = pathPosition % path.size();
		nextCell = path.get(pathPosition);
		nextX = getXCoord(nextCell);
		nextY = getYCoord(nextCell);
		// is currentX and currentY inside cell?
		if(nextCell == nextX + (nextY * width) + 1) 
			pathPosition++;
	}

	@Override
	public int getXDir(float currentX) {
		return (nextX > (int) currentX) ? 1 : ((nextX < (int) currentX) ? -1 : 0);   
	}

	int getXCoord(int cell) {
		return (cell-1) % width;
	}

	@Override
	public int getYDir(float currentY) {
		return (nextY > (int) currentY) ? 1 : ((nextY < (int) currentY) ? -1 : 0);   
	}

	int getYCoord(int cell) {
		return (cell-1) / width;
	}

}
