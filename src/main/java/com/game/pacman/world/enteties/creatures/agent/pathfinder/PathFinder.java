package com.game.pacman.world.enteties.creatures.agent.pathfinder;

import java.util.List;

public interface PathFinder {

	public int getWidth();

	public int getHeight();

	public List<Integer> calculatePath(int startX, int startY, int destX, int destY);

}
