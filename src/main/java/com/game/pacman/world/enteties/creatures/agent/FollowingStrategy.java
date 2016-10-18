package com.game.pacman.world.enteties.creatures.agent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.game.pacman.world.enteties.creatures.agent.pathfinder.PathFinder;

public class FollowingStrategy extends Strategy {
	
	private PathFinder astar;
	private Agent agent;

	private List<Integer> path;
	private int pathPosition;

	private int targetCell;
	private int targetX;
	private int targetY;
	
	private AtomicBoolean processing;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

	public FollowingStrategy(final int[][] matrix, PathFinder astar) {
		super.setMatrix(matrix);
		this.astar = astar;
		this.astar.setMatrix(matrix);
		pathPosition = 0;
		processing = new AtomicBoolean(false);
	}
	
	@Override
	public void findPath(int currentX, int currentY, int playerX, int playerY) {
		targetX = currentX; // until path has been calculated
		targetY = currentY;
		int currentCell = currentX + (currentY * super.width) + 1;
		if(getPath() == null && !processing.get()) {
			processParallel(currentX, currentY, playerX, playerY);
		} else {
			if(getPath() != null && pathPosition < getPath().size()) {
				targetCell = getPath().get(pathPosition);
				targetX = getXCoord(targetCell);
				targetY = getYCoord(targetCell);
				if(targetCell == currentCell) 
					pathPosition++;
			} else {
				pathPosition = 0;
				if(getPath() != null) // reached end
					agent.setStrategy(new RandomStrategy());
//					agent.setStrategy(new BreadCrumbsStrategy(matrix));
			}
		}
	}
	
	private void processParallel(final int currentX, final int currentY, final int playerX, final int playerY) {
		threadPool.execute(new Runnable() {
			public void run() {
				processing.set(true);
				setPath(astar.calculatePath(currentX, currentY, playerX, playerY));
				processing.set(false);
			}
		});
	}
	
//	private void processSequential(int currentX, int currentY, int playerX, int playerY) {
//		processing.set(true);
//		setPath(astar.calculatePath(currentX, currentY, playerX, playerY));
//		processing.set(false);
//	}

	private void setPath(List<Integer> path) {
		this.path = path;
	}
	
	private List<Integer> getPath() {
		return path;
	}

	@Override
	public int getXDir(int currentX, int currentY) {
		return (targetX > (int) currentX) ? 1 : ((targetX < (int) currentX) ? -1 : 0);   
	}

	int getXCoord(int cell) {
		return (cell-1) % super.width;
	}

	@Override
	public int getYDir(int currentX, int currentY) {
		return (targetY > (int) currentY) ? 1 : ((targetY < (int) currentY) ? -1 : 0);   
	}

	int getYCoord(int cell) {
		return (cell-1) / super.width;
	}

	@Override
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
