package com.game.pacman;

public class Launcher {

	public static void main(String[] args) {
		Game pacman = new Game("Pacman", 800, 800);
		pacman.start();
	}
}
