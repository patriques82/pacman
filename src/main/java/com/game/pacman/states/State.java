package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.levels.GameHandler;

public abstract class State {
	
	protected GameHandler handler;
	
	public State(GameHandler handler) {
		this.handler = handler;
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
