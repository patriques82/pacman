package com.game.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Worm {
	private int pWIDTH;
	private int pHEIGHT;
	
	private static final int MAXPOINTS = 40;
	private Point cells[];
	private int nPoints;
	private int tailPosn, headPosn;
	private Obstacles obs;

	private static final int DOTSIZE = 12;
	private static final int RADIUS = DOTSIZE/2;
	
	// Directions
	private static final int NUM_DIRS = 8;
	private static final int N = 0;
	private static final int NE = 1;
	private static final int E = 2;
	private static final int SE = 3;
	private static final int S = 4;
	private static final int SW = 5;
	private static final int W = 6;
	private static final int NW = 7;
	
	private int currCompass; // current direction
	
	// Stores the increments in each of the compass dirs.
	// An increment is added to the old head position to get the
	// new position.
	Point2D.Double incrs[];
	private int[] probsForOffset;
	private static final int NUM_PROBS = 9;

	public Worm(int pWidth, int pHeight, Obstacles obs) {
		pWIDTH = pWidth;
		pHEIGHT = pHeight;
		this.obs = obs;
		cells = new Point[40];
		nPoints = 0;
		headPosn = -1; 
		tailPosn = -1;
		
	    // increments for each compass dir
		incrs = new Point2D.Double[NUM_DIRS];
		incrs[N] = new Point2D.Double(0.0, -1.0);
		incrs[NE] = new Point2D.Double(0.7, -0.7);
		incrs[E] = new Point2D.Double(1.0, 0.0);
		incrs[SE] = new Point2D.Double(0.7, 0.7);
		incrs[S] = new Point2D.Double(0.0, 1.0);
		incrs[SW] = new Point2D.Double(-0.7, 0.7);
		incrs[W] = new Point2D.Double(-1.0, 0.0);
		incrs[NW] = new Point2D.Double(-0.7, -0.7);
		
		// probability info for selecting a compass dir.
	    //    0 = no change, -1 means 1 step anti-clockwise,
	    //    1 means 1 step clockwise, etc.
		probsForOffset = new int[NUM_PROBS];
        probsForOffset[0] = 0;  probsForOffset[1] = 0;
        probsForOffset[2] = 0;  probsForOffset[3] = 1;
        probsForOffset[4] = 1;  probsForOffset[5] = 2;
        probsForOffset[6] = -1;  probsForOffset[7] = -1;
        probsForOffset[8] = -2;

	}

	public boolean nearHead(int x, int y) {
		if(nPoints > 0) {
			if( (Math.abs(cells[headPosn].x + RADIUS - x) <= DOTSIZE) &&
				(Math.abs(cells[headPosn].y + RADIUS - y) <= DOTSIZE) )
				return true;
		}
		return false;
	}

	public boolean touchedAt(int x, int y) {
		int i = tailPosn;
		while(i != headPosn) {
			if( (Math.abs(cells[headPosn].x + RADIUS - x) <= RADIUS) &&
				(Math.abs(cells[headPosn].y + RADIUS - y) <= RADIUS) )
				return true;
			i = (i+1) % MAXPOINTS;
		}
		return false;
	}

	public void move() {
		int prevPosn = headPosn;
		headPosn = (headPosn + 1) % MAXPOINTS; // wrap around
		if(nPoints == 0) { // empty array at start
			tailPosn = headPosn;
			currCompass = (int)(Math.random()*NUM_DIRS);
			cells[headPosn] = new Point(pWIDTH/2, pHEIGHT/2); // center pt
			nPoints++;
		}
		else if(nPoints == MAXPOINTS) {
			tailPosn = (tailPosn + 1) % MAXPOINTS;
			newHead(prevPosn);
		}
		else {
			newHead(prevPosn);
			nPoints++;
		}
		
	}

	public void draw(Graphics dbg) {
		if(nPoints > 0) {
			dbg.setColor(Color.BLACK);
			int i = tailPosn;
			while(i != headPosn) {
				dbg.fillOval(cells[i].x, cells[i].y, DOTSIZE, DOTSIZE);
				i = (i + 1) % MAXPOINTS;
			}
			dbg.setColor(Color.RED);
			dbg.fillOval(cells[headPosn].x, cells[headPosn].y, DOTSIZE, DOTSIZE);
		}
	}
	
	
	private void newHead(int prevPosn) {
		int fixedOffs[] = {-2, 2, -4};
		int newBearing = varyBearing();
		Point newPt = nextPoint(prevPosn, newBearing);
		
		// if head collides with obstacle move round
		if(obs.hits(newPt, DOTSIZE)) {
			for (int i = 0; i < fixedOffs.length; i++) {
				newBearing = calcBearing(fixedOffs[i]);
				newPt = nextPoint(prevPosn, newBearing);
				if(!obs.hits(newPt, DOTSIZE)) {
					break;
				}
			}
		}
		
		cells[headPosn] = newPt; // new head position
		currCompass = newBearing; // new compass direction
	}

	private Point nextPoint(int prevPosn, int bearing) {
		Point2D.Double incr = incrs[bearing];
		int newX = cells[prevPosn].x + (int)(DOTSIZE * incr.x);
		int newY = cells[prevPosn].y + (int)(DOTSIZE * incr.y);
		
		if(newX+DOTSIZE < 0)
			newX = newX + pWIDTH;
		else if(newX > pWIDTH)
			newX = newX - pWIDTH;
		if(newY+DOTSIZE < 0)
			newY = newY + pHEIGHT;
		else if(newY > pHEIGHT)
			newY = newY - pHEIGHT;

		return new Point(newX, newY);
	}

	private int varyBearing() {
		int newOffset = probsForOffset[(int)(Math.random()*NUM_PROBS)];
		return calcBearing(newOffset);
	}

	/**
	 * Calculates the new compass bearing
	 * @param offset
	 * @return bearing direction
	 */
	private int calcBearing(int offset) {
		int turn = currCompass + offset;
		if(turn >= NUM_DIRS)
			turn = turn - NUM_DIRS;
		else if(turn < 0)
			turn = NUM_DIRS + turn;
		return turn;
	}
	

}
