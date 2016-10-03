package com.game.pacman.states;

import java.awt.Graphics;

public abstract class State {
	
	/**
	 * Update state
	 */
	public abstract void tick();
	/**
	 * Draw on the graphics
	 * @param g
	 */
	public abstract void render(Graphics g);

}
