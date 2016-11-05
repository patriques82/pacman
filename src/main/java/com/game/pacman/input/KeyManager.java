package com.game.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private static boolean[] keys = new boolean[256];
	private static boolean UP, DOWN, LEFT, RIGHT, ENTER;
	
	public static void tick() {
		UP = keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		ENTER = keys[KeyEvent.VK_ENTER];
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public static boolean pressUp() {
		return UP;
	}

	public static boolean pressDown() {
		return DOWN;
	}

	public static boolean pressLeft() {
		return LEFT;
	}

	public static boolean pressRight() {
		return RIGHT;
	}
	
	public static boolean pressEnter() {
		return ENTER;
	}

}
