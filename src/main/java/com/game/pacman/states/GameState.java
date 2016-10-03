package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.levels.World;

public class GameState extends State {

	private World world;

	public GameState() {
		world = new World("res/levels/level1.level");
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

}
