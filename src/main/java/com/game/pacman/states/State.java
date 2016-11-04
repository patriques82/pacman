package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.world.observer.Observable;

public abstract class State extends Observable {
	
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
