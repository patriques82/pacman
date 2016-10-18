package com.game.pacman.world.enteties.creatures.agent.pathfinder;

public class Node {
	
	private int id, x, y, distance;
	private Node parent;
	private int movementCost;
	private int fValue;
	
//	[y, x, id, distance, parentId, movementCost, fValue]

	public Node(int y, int x) {
		this.setY(y);
		this.setX(x);
		this.setMovementCost(0);
		this.fValue = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
		setMovementCost(parent.getMovementCost() + 1);
		setFCost(getMovementCost() + getDistance());
	}

	public int getMovementCost() {
		return movementCost;
	}
	
	public void setMovementCost(int cost) {
		movementCost = cost;
	}

	public int getFCost() {
		return fValue;
	}
	
	public void setFCost(int fValue) {
		this.fValue = fValue;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + "," + this.id + "," + this.fValue + ")";
	}


}
