package com.game.pacman.world.enteties.creatures.agent.pathfinder;

import java.util.List;

public interface PathFinder {

	public void setMatrix(int[][] tiles);

	public List<Integer> calculatePath(int startX, int startY, int destX, int destY);

}
