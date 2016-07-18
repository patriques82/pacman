package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.levels.Handler;

public abstract class State {
	
	protected Handler handler;
	
	public State(Handler handler) {
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
