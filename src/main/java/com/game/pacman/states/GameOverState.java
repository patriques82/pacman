package com.game.pacman.states;

import java.awt.Font;
import java.awt.Graphics;

import com.game.pacman.levels.GameHandler;

public class GameOverState extends State {

	private String message;

	public GameOverState(GameHandler handler, String msg) {
		super(handler);
		message = msg;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.setFont(new Font("Monospaced", Font.BOLD, 25));
		int textXOffset = g.getFontMetrics().stringWidth(message) / 2;
		g.drawString(message, handler.getWidth()/2 - textXOffset, handler.getHeight()/2);
	}

}
