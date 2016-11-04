package com.game.pacman.states;

import java.awt.Graphics;

/**
 * Handles the current state (Main menu, Game, Settings)
 * @author patriknygren
 *
 */
public class StateManager {
	
	private static State currentState;
	
	public static void tick() {
		if(currentState != null) {
			currentState.tick();
//			if(currentState.hasEnded())
//				currentState = currentState.getNextState();
		}
	}

	public static void render(Graphics g) {
		if(currentState != null)
			currentState.render(g);
	}

	public static void setState(State state) {
		currentState = state;
	}
	
}
