package com.game.pacman.world.enteties.creatures.agent.astar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Astar {

	private Node[][] graph;

	private int heigth;
	private int width;
	
	public Astar(int[][] matrix) {
		assert(matrix.length > 1 && matrix[0].length > 1); // world must be larger than 1*1
		heigth = matrix.length;
		width = matrix[0].length;
		graph = makeNodeMatrix(matrix);
		printMatrix();
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return heigth;
	}

	/**
	 *  
	 * @param matrix The matrix to find the path in.
	 * @param startX
	 * @param startY
	 * @param destX
	 * @param destY
	 * @return path The indices of the cells (vectorized) in ordered form that represents the shortest path from start to destination
	 */
	public List<Integer> calculatePath(int startX, int startY, int destX, int destY) {
		calculateDistanceAndId(destY, destX);
		Node startNode = graph[startY][startX];
		Node destNode = graph[destY][destX];
		Node current = startNode;
		Set<Node> neighbors;
		List<Node> openList = new LinkedList<>();
		Set<Node> closedList = new HashSet<>();
		while(true) {
			closedList.add(current);
			neighbors = getNeighbors(current);
			neighbors.removeAll(closedList);
			if(neighbors.contains(destNode)) {
				destNode.setParent(current);
				break;
			} else {
				for(Node n : neighbors) {
					if(n.getParent() == null || current.getMovementCost() + 1 < n.getMovementCost())
						n.setParent(current);
				}
				openList.addAll(neighbors);
				current = removeCheapestNode(openList);
			}
		}
		return getPath(destNode);
	}

	Node removeCheapestNode(List<Node> openList) {
		if(!openList.isEmpty()) {
			int cheapest = 0;
			for(int i=0; i<openList.size(); i++) {
				if(openList.get(i).getFCost() < openList.get(cheapest).getFCost()) {
					cheapest = i;
				}
			}
			return openList.remove(cheapest);
		} else {
			return null;
		}
	}

	Node[][] makeNodeMatrix(int[][] matrix) {
		Node[][] nodeMatrix = new Node[heigth][width];
		for(int y=0; y<heigth; y++) {
			for(int x=0; x<width; x++) {
				if(matrix[y][x] == 0) { // empty tile possible to reach
					nodeMatrix[y][x] = new Node(y, x);
				} else { // block tile
					nodeMatrix[y][x] = null;
 				}
			}
		}
		return nodeMatrix;
	}
	
	
	void printMatrix() {
		System.out.println("");
		for(int y=0; y<heigth; y++) {
			for(int x=0; x<width; x++) {
				if(graph[y][x] == null) { // empty tile possible to reach
					System.out.print("1 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println("");
		}
	}
	
	void calculateDistanceAndId(int destY, int destX) {
		for(int y=0; y<heigth; y++) {
			for(int x=0; x<width; x++) {
				if(graph[y][x] != null) { // empty tile possible to reach
					Node n = graph[y][x];
					n.setDistance(Math.abs(destX - x) + Math.abs(destY - y));
					n.setId((y * width) + x + 1);
				}
			}
		}
	}

	Set<Node> getNeighbors(final Node node) {
		Set<Node> neighbours = new HashSet<>();
		if(node.getY() > 0 && graph[node.getY()-1][node.getX()] != null)
			neighbours.add(graph[node.getY()-1][node.getX()]);
		if(node.getX() > 0 && graph[node.getY()][node.getX()-1] != null)
			neighbours.add(graph[node.getY()][node.getX()-1]);
		if(node.getX() < width-1 && graph[node.getY()][node.getX()+1] != null)
			neighbours.add(graph[node.getY()][node.getX()+1]);
		if(node.getY() < heigth-1 && graph[node.getY()+1][node.getX()] != null)
			neighbours.add(graph[node.getY()+1][node.getX()]);
		return neighbours;
	}

	void setNodeToParent(Node parent, Collection<Node> neighbours) {
		for(Node n : neighbours) {
			n.setParent(parent);
		}
	}

	void calculateMovementCosts(int currentCost, Collection<Node> neighbours) {
		for(Node n : neighbours) {
			n.setMovementCost(currentCost + 1);
		}
	}

	List<Integer> getPath(Node destNode) {
		Stack<Integer> stack = new Stack<>();
		Node currentNode = destNode;
		while(currentNode != null) { // reached start
			stack.push(currentNode.getId());
			currentNode = currentNode.getParent();
		}
		List<Integer> path = new ArrayList<>(stack.size());
		int i = 0;
		while(!stack.isEmpty())
			path.add(i++, stack.pop());
		return path;
	}
	
	Node get(int y, int x) {
		return graph[y][x];
	}

}
