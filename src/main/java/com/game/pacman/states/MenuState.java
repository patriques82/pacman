package com.game.pacman.states;

import java.awt.Font;
import java.awt.Graphics;

import com.game.pacman.Game;
import com.game.pacman.input.KeyManager;

public class MenuState extends State {
	
	private Game game;
	private int centerX;
	private int centerY;
	private Font font;
	private String message;

	public MenuState(Game game, KeyManager keyMngr, int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.game = game;
		font = new Font("Monospaced", Font.BOLD, 25);
		message = new String("Press Enter to start");
	}

	@Override
	public void tick() {
		if(KeyManager.pressEnter()) {
			game.setGameState(new GameState(game));
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(font);
		int textXOffset = g.getFontMetrics().stringWidth(message) / 2;
		g.drawString(message, centerX - textXOffset, centerY);
	}

}
