package com.game.pacman.world;

public class Points {
	private boolean[][] points;
	private int goal; // points needed to win

	Points(int width, int height) {
		points = new boolean[width][height];
		goal = 0;
	}

	boolean isPoint(int x, int y) {
		return points[y][x];
	}
	
	void addPoint(int x, int y) {
		points[y][x] = true;
		goal++;
	}

	void removePoint(int x, int y) {
		points[y][x] = false;
	}
	
	int getGoal() {
		return goal;
	}

}
