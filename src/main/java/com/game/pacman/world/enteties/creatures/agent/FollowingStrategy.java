package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.game.pacman.world.enteties.creatures.agent.astar.Astar;

public class FollowingStrategy implements Strategy {
	
	private Astar astar;
	private List<Integer> path;
	private int pathPosition;
	private int width;

	private int targetCell;
	private int targetX;
	private int targetY;
	
	private final static int PoolSize = 4;
	private final static ExecutorService Pool = Executors.newFixedThreadPool(PoolSize);
	private boolean processing;

	
	public FollowingStrategy(Astar astar) {
		this.astar = astar;
		width = astar.getWidth();
		pathPosition = 0;
		processing = false;
	}

	@Override
	public void findPath(final int currentX, final int currentY, final int playerX, final int playerY) {
		targetX = currentX; // until path has been calculated
		targetY = currentY;
		int currentCell = currentX + (currentY * width) + 1;
		if(path == null) {
			path = astar.calculatePath(currentX, currentY, playerX, playerY);
			System.out.println(path);
//		if(path == null || !processing) {
//			Pool.execute(new Runnable() {
//				public void run() {
//					processing = true;
//					path = astar.calculatePath(currentX, currentY, playerX, playerY);
//					processing = false;
//				}
//			});
		} else {
//			System.out.println("Path: " + path);
//			System.out.println("Current: " + currentCell);
//			System.out.println("CurrentX, CurrentY: " + currentX + ", " + currentY);
//			System.out.println("Target: " + targetCell);
//			System.out.println("TargetX, TargetY " + targetX + ", " + targetY);
//			System.out.println("-------------------------------------------\n");

			targetCell = path.get(pathPosition);
			targetX = getXCoord(targetCell);
			targetY = getYCoord(targetCell);
			if(targetCell == currentCell) 
				pathPosition++;
			if(pathPosition == path.size()) // reached end
				path = null;
		}
	}

	@Override
	public int getXDir(int currentX) {
		return (targetX > (int) currentX) ? 1 : ((targetX < (int) currentX) ? -1 : 0);   
	}

	int getXCoord(int cell) {
		return (cell-1) % width;
	}

	@Override
	public int getYDir(int currentY) {
		return (targetY > (int) currentY) ? 1 : ((targetY < (int) currentY) ? -1 : 0);   
	}

	int getYCoord(int cell) {
		return (cell-1) / width;
	}

}
