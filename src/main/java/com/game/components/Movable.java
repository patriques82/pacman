package com.game.components;

import java.awt.Graphics;

import com.game.main.Pixels;

/**
 * All game actors in game implements this interface
 * @author patriknygren
 *
 */
public abstract class Movable {
	private Direction dir;
	private int fps;  // framesPerSecond
	private int x, y;

	/**
	 * Updates game objects current position 
	 * @param pixels
	 */
	public abstract void update(Pixels pixels);

	/**
	 * Renders the new object at current position
	 * @param g graphics object
	 */
	public abstract void render(Graphics g);

	/**
	 * Sets the position of object
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get x position
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get y position
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Changes the direction of game object
	 * @param d direction
	 */
	public void changeDir(Direction d) {
		dir = d;
	}

	/**
	 * Increases the speed of game object
	 * @param framesPerSec speed
	 */
	public void changeSpeed(int framesPerSec) {
		this.fps = framesPerSec;
	}
}
