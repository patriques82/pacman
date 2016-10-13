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

		graph = makeGraph(matrix, (int) destX, (int) destY);
		Node startNode = graph[(int) startY][(int) startX];
		Node destNode = graph[(int) destY][(int) destX];

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

		return getPath(graph, startNode, destNode);
	}


	private static Node[][] makeGraph(int[][] matrix, int destX, int destY) {
		int heigth = matrix.length;
		int width = matrix[0].length;
		Node[][] manhattanDistances = new Node[heigth][width];
		for(int y=0; y<heigth; y++) {
			for(int x=0; x<width; x++) {
				if(matrix[y][x] == 0) { // empty tile possible to reach
					int distance = getDistance(x, y, destX, destY); 
					int id = (y * heigth) + x;
					manhattanDistances[y][x] = new Node(id, x, y, distance);
				} else { // block tile
					manhattanDistances[y][x] = null;
 				}
			}
		}
		return null;
	}

	private static int getDistance(int x, int y, int destX, int destY) {
		return Math.abs(destX - x) + Math.abs(destY - y);
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
