package com.game.components;

import java.awt.Graphics;
import java.awt.Point;

public class Worm {
	
	private static final int MAXPOINTS = 40;

	private Point cells[];
	private int nPoints;
	private int tailPosn, headPosn;
	
	// Directions
//	private static final int NUM_DIRS = 8;
//	private static 

	public Worm(int pWIDTH, int pHEIGHT, Obstacles obs) {
		cells = new Point[40];
		nPoints = 0;
		headPosn = -1; 
		tailPosn = -1;
	}

	public boolean nearHead(int x, int y) {
		return false;
	}

	public boolean touchedAt(int x, int y) {
		return false;
	}

	public void move() {
		
	}

	public void draw(Graphics dbg) {
		
	}

}
