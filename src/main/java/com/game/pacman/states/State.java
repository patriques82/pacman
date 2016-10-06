package com.game.pacman.states;

import java.awt.Graphics;

public abstract class State extends Observable {
	
	private State nextState;
	
	/**
	 * Update state
	 */
	public abstract void tick();
	/**
	 * Draw on the graphics
	 * @param g
	 */
	public abstract void render(Graphics g);

	public abstract boolean hasEnded();

	public void setNextState(State state) {
		nextState = state;
	}
	
	public State getNextState() {
		return nextState;
	}


}
