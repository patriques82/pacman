package com.game.pacman;

import java.awt.Graphics;

public abstract class State {
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}

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
