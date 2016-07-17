package com.game.pacman.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.Game;
import com.game.pacman.gfx.Assets;
import com.game.pacman.input.KeyManager;

public class Player extends Creature {
	
	private Game game;

	public Player(Game g, int x, int y) {
		super(x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
		game = g;
	}

	@Override
	public void setPosition() {
		
	}

	@Override
	public void tick() {
		getInput();
		move();
	}
	
	private void getInput() {
		dx = 0;
		dy = 0;
		if(KeyManager.pressUp())
			dy = -speed;
		if(KeyManager.pressDown())
			dy = speed;
		if(KeyManager.pressLeft())
			dx = -speed;
		if(KeyManager.pressRight())
			dx = speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.playerEast, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
	}

}
