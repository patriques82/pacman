package com.game.pacman.states;

import java.awt.Font;
import java.awt.Graphics;

public class GameOverState extends State {

	private int centerX;
	private int centerY;
	private Font font;
	private String message;

	public GameOverState(String message, int centerX, int centerY) {
		this.message = message;
		this.centerX = centerX;
		this.centerY = centerY;
		font = new Font("Monospaced", Font.BOLD, 25);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.setFont(font);
		int textXOffset = g.getFontMetrics().stringWidth(message) / 2;
		g.drawString(message, centerX - textXOffset, centerY);
	}

}
