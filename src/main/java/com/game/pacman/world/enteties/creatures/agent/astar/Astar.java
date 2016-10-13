package com.game.pacman.world.enteties.creatures.agent.astar;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Astar {

//	private static int[][] manhattanDistances;  // h value
//	private static int[][] movementCosts; // g value
//	private static int[][] fCosts; // movement cost + manhattanDistance

	private static Node[][] graph;
	private static List<Node> openList;
	private static List<Node> closedList;

	/**
	 *  
	 * @param matrix The matrix to find the path in.
	 * @param startX
	 * @param startY
	 * @param destX
	 * @param destY
	 * @return path The indices of the cells (vectorized) in ordered form that represents the shortest path from start to destination
	 */
	public static int[] calculatePath(int[][] matrix, float startX, float startY, float destX, float destY) {
		int heigth = matrix.length;
		int width = matrix[0].length;
		Node startNode = new Node(startX, startY, 0); 
		Node destNode = new Node(destX, destY, -1);

		graph = makeGraph(matrix, heigth, width);
		openList = new LinkedList<>();
		closedList = new LinkedList<>();
		closedList.add(startNode);
		Iterator<Node> closedIterator = closedList.iterator();

		Collection<Node> neighbours;
		while(true) {
			Node next = closedIterator.next();
			neighbours = neighbours(next);

			if(!neighbours.contains(destNode)) {
				openList.addAll(neighbours);
				Node cheapest = popCheapestNode(openList);
				closedList.add(cheapest);
			} else {
				break;
			}
		}
		
//		int startCellId = ((int) startY * width) + (int) startX;
//		int destCellId = ((int) destY * width) + (int) destX;

		return getPath(graph, startNode, destNode);
	}


	private static Node[][] makeGraph(int[][] matrix, int heigth, int width) {
//	private static int[][] computeManhattanDistances(int[][] matrix, int heigth, int width, float destX, float destY) {
//		int[][] manhattanDistances = new int[heigth][width];
//		for(int y=0; y<heigth; y++) {
//			for(int x=0; x<width; x++) {
//				if(matrix[y][x] == 0) { // empty tile possible to reach
//					manhattanDistances[y][x] = Math.abs(((int) destX) - x) + Math.abs(((int) destY) - y);
//				} else {
//					manhattanDistances[y][x] = -1;
// 				}
//			}
//		}
//		return manhattanDistances;
//	}
		return null;
	}

	private static Collection<Node> neighbours(Node next) {
		return null;
	}

	private static Node popCheapestNode(List<Node> openList) {
		return null;
	}

	private static int[] getPath(Node[][] graph, Node startNode, Node destNode) {
		return null;
	}


}
