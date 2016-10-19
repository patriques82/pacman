package com.game.pacman.world.enteties.creatures.agent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.game.pacman.world.enteties.creatures.agent.pathfinder.PathFinder;

public class FollowingAsyncStrategy extends FollowingStrategy {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

	public FollowingAsyncStrategy(int[][] matrix, PathFinder astar) {
		super(matrix, astar);
	}

	@Override
	protected void process(final int currentX, final int currentY, final int playerX, final int playerY) {
		threadPool.execute(new Runnable() {
			public void run() {
				setPath(astar.calculatePath(currentX, currentY, playerX, playerY));
			}
		});
	}

}
