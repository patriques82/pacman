package com.game.pacman.world.enteties.creatures.agent.astar;

public class Astar {

	private static int[][] computedValues;
	private static int[] openList;
	private static int[] closedList;

	public static int[] calculatePath(int[][] matrix, float startX, float startY, float destX, float destY) {
		// 1. compute all Manhattan distances
		// 2. initialize open list, closed list
		// 3. expand until destination found
		//	3.1 for all neighbours compute heuristics
		//	3.2 for all neighbours compute g cost 
		// 	3.3 expand
		return null;
	}

}
