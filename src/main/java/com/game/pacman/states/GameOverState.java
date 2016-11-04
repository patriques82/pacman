package com.game.pacman.states;

import java.awt.Font;
import java.awt.Graphics;

public class GameOverState extends State {

	private String message;
	private int centerX;
	private int centerY;

	public GameOverState(String message, int centerX, int centerY) {
		this.message = message;
		this.centerX = centerX;
		this.centerY = centerY;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.setFont(new Font("Monospaced", Font.BOLD, 25));
		int textXOffset = g.getFontMetrics().stringWidth(message) / 2;
		g.drawString(message, centerX - textXOffset, centerY);
	}

}
