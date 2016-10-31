package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;

import com.game.pacman.world.enteties.creatures.agent.pathfinder.PathFinder;
import com.game.pacman.world.tiles.Tile;

public class FollowingStrategy extends Strategy {
	
	protected PathFinder astar;


	protected List<Integer> path;
	protected int pathPosition;

	protected int targetCell;
	protected int targetX;
	protected int targetY;
	

	public FollowingStrategy(final int[][] matrix, PathFinder astar) {
		super(matrix);
		this.astar = astar;
		pathPosition = 0;
	}

	@Override
	public void findPath(float currentX, float currentY, float playerX, float playerY) {
		int logicalX = (int) currentX/Tile.TILESIZE;
		int logicalY = (int) currentY/Tile.TILESIZE;
		int logicalPlayerX = (int) playerX/Tile.TILESIZE;
		int logicalPlayerY = (int) playerY/Tile.TILESIZE;

		targetX = logicalX; // until path has been calculated
		targetY = logicalY;
		updateMatrix(logicalX, logicalY, logicalPlayerX, logicalPlayerY);

		int currentCell = logicalX + (logicalY * width) + 1;
		if(getPath() == null && !astar.isProcessing()) {
			process(logicalX, logicalY, logicalPlayerX, logicalPlayerY);
		} else {
			if(getPath() != null && pathPosition < getPath().size()) {
				targetCell = getPath().get(pathPosition);
				if(targetCell == currentCell) {  // get next target
					++pathPosition;
					if(pathPosition < getPath().size())
						targetCell = getPath().get(pathPosition);
				}
				targetX = getXCoord(targetCell);
				targetY = getYCoord(targetCell);
			} else {
				pathPosition = 0;
				if(getPath() != null) // reached end => Switch to bread crumb strategy
					agent.setStrategy(new BreadCrumbsStrategy(matrix));
			}
		}
	}
	
	
	protected void process(int currentX, int currentY, int playerX, int playerY) {
		setPath(astar.calculatePath(currentX, currentY, playerX, playerY));
	}

	protected void setPath(List<Integer> path) {
		this.path = path;
	}
	
	private List<Integer> getPath() {
		return path;
	}

	@Override
	public int getXDir(float currentX, float currentY) {
		int logicalX = (int) currentX/Tile.TILESIZE;
		return (targetX > logicalX) ? 1 : ((targetX < currentX) ? -1 : 0);   
	}

	int getXCoord(int cell) {
		return (cell-1) % width;
	}

	@Override
	public int getYDir(float currentX, float currentY) {
		int logicalY = (int) currentY/Tile.TILESIZE;
		return (targetY > logicalY) ? 1 : ((targetY < logicalY) ? -1 : 0);   
	}

	int getYCoord(int cell) {
		return (cell-1) / width;
	}

}
