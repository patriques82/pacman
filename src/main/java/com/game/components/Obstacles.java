package com.game.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.game.view.GamePanel;


public class Obstacles {
	
	private GamePanel gpTop;
	private static final int BOX_LENGTH = 3;
	private List<Rectangle> boxes;


	public Obstacles(GamePanel gp) {
		boxes = new ArrayList<>();
		gpTop = gp;
	}

	public int getNumObstacles() {
		return boxes.size();
	}

	synchronized public void add(int x, int y) {
		boxes.add(new Rectangle(x, y, BOX_LENGTH, BOX_LENGTH));
		gpTop.setBoxes(boxes.size());
	}

	synchronized public void draw(Graphics dbg) {
		Rectangle box;
		dbg.setColor(Color.BLUE);
		for (int i = 0; i < boxes.size(); i++) {
			box = boxes.get(i);
			dbg.fillRect(box.x, box.y, box.width, box.height);
		}
	}

	public boolean hits(Point pt, int dotsize) {
		Rectangle r = new Rectangle(pt.x, pt.y, dotsize, dotsize);
		Rectangle box;
		for (int i = 0; i < boxes.size(); i++) {
			box = boxes.get(i);
			if(box.intersects(r)) {
				return true;
			}
		}
		return false;
	}


}
