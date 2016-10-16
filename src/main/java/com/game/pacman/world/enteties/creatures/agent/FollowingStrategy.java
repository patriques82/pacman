package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.game.pacman.world.enteties.creatures.agent.astar.Astar;
import com.game.pacman.world.tiles.Tile;

public class FollowingStrategy implements Strategy {
	
	private Astar astar;
	private List<Integer> path;
	private int pathPosition;
	private int width;

	private int nextCell;
	private int nextX;
	private int nextY;
	
	private final static int PoolSize = 4;
	private final static ExecutorService Pool = Executors.newFixedThreadPool(PoolSize);
	private final AtomicBoolean calculatingPath;

	
	public FollowingStrategy(Astar astar) {
		this.astar = astar;
		width = astar.getWidth();
		pathPosition = 0;
		calculatingPath = new AtomicBoolean(false);
	}

	@Override
	public void findPath(float currentX, float currentY, float playerX, float playerY) {
//		if(path == null || pathPosition == path.size()) {
//			path = astar.calculatePath(currentX, currentY, playerX, playerY);
//			pathPosition = 0;
//		}
//		nextCell = path.get(pathPosition);
//		nextX = getXCoord(nextCell);
//		nextY = getYCoord(nextCell);
//		if(nextCell == nextX + (nextY * width) + 1) 
//			pathPosition++;
		
		final float adjustCurrentX = currentX/Tile.TILESIZE;
		final float adjustCurrentY = currentY/Tile.TILESIZE;
		final float adjustPlayerX = playerX/Tile.TILESIZE;
		final float adjustPlayerY = playerY/Tile.TILESIZE;
		if((path == null || pathPosition == path.size()) && !calculatingPath.get()) {
			// run in separate thread
			calculatingPath.set(true);
			Pool.execute(new Runnable() {
				public void run() {
					path = astar.calculatePath(adjustCurrentX, adjustCurrentY, adjustPlayerX, adjustPlayerY);
					calculatingPath.set(false);
				}
			});
		}
		if(path != null) {
			nextCell = path.get(pathPosition);
			nextX = getXCoord(nextCell);
			nextY = getYCoord(nextCell);
			// is currentX and currentY inside cell?
			if(nextCell == adjustCurrentX + (adjustCurrentY * width) + 1 && !calculatingPath.get()) 
				pathPosition++;
		} else {
			nextX = (int) adjustCurrentX;
			nextY = (int) adjustCurrentY;
		}
	}

	@Override
	public int getXDir(float currentX) {
		currentX /= Tile.TILESIZE;
		return (nextX > (int) currentX) ? 1 : ((nextX < (int) currentX) ? -1 : 0);   
	}

	int getXCoord(int cell) {
		return (cell-1) % width;
	}

	@Override
	public int getYDir(float currentY) {
		currentY /= Tile.TILESIZE;
		return (nextY > (int) currentY) ? 1 : ((nextY < (int) currentY) ? -1 : 0);   
	}

	int getYCoord(int cell) {
		return (cell-1) / width;
	}

}
