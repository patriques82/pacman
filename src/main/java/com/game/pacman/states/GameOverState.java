package com.game.pacman.states;

import java.awt.Font;
import java.awt.Graphics;

import com.game.pacman.Game;
import com.game.pacman.input.KeyManager;

public class GameOverState extends State {

	private Game game;
	private int centerX;
	private int centerY;
	private Font font, fontSmall;
	private String message, smallMessage;

	public GameOverState(Game game, String message, int centerX, int centerY) {
		this.game = game;
		this.message = message;
		smallMessage = "Press enter to play again"; 
		this.centerX = centerX;
		this.centerY = centerY;
		font = new Font("Monospaced", Font.BOLD, 25);
		fontSmall = new Font("Monospaced", Font.PLAIN, 15);
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
		int textHeight = g.getFontMetrics().getHeight();
		g.drawString(message, centerX - textXOffset, centerY);
		g.setFont(fontSmall);
		textXOffset = g.getFontMetrics().stringWidth(smallMessage) / 2;
		g.drawString(smallMessage, centerX - textXOffset, centerY + textHeight);
	}

}
