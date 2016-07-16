package com.game.main;

import java.awt.Graphics;


/**
 * Singleton object that represents the state of the game
 * @author patriknygren
 *
 */
public class Pixels {
	private static int screenWidth, screenHeight;
	private static int pixelSize; // size of one square pixel 
	private static int width, height; // size in pixels of screen
	private static int[][] pixels; // state container (number in pixel tells what kind of object occupy)
	private static Pixels instance;
	
	public void setSize(int pSize, int sw, int sh) {
		pixelSize = pSize;
		screenWidth = sw;
		screenHeight = sh;
	}
	
	public static void setLayout(int [][] layout) {
		if(pixels == null) {
			pixels = layout;
		}
	}
	
	public static Pixels getInstance() {
		if(instance != null) {
			return instance;
		} 
		else {
			instance = new Pixels(pixelSize, screenWidth, screenHeight);
			return instance;
		}
	}

	private Pixels(int pSize, int sw, int sh) {
		pixelSize = pSize;
		width = sw / pixelSize + 1;
		height = sh / pixelSize + 1;
		screenWidth = sw;
		screenHeight = sh;
		pixels = new int[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Draws the pixels to be seen (used for debugging)
	 * @param g
	 */
	public void draw(Graphics g) {
		// columns
		for(int i = 0; i < width; i++) {
			int x = i*pixelSize;
			g.drawLine(x, 0, x, screenHeight);
		}
		// rows
		for (int j = 0; j < height; j++) {
			int y = j*pixelSize;
			g.drawLine(0, y, screenWidth, y);
		}
		
	}

}
