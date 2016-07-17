package com.game.pacman;

import java.awt.Graphics;

/**
 * Handles the current state (Main menu, Game, Settings)
 * @author patriknygren
 *
 */
public class StateManager {
	
	private static State currentState;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static void tick() {
		if(currentState != null)
			currentState.tick();
	}

	public static void render(Graphics g) {
		if(currentState != null)
			currentState.render(g);
	}

}
