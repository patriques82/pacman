package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.levels.GameHandler;
import com.game.pacman.levels.World;

public class GameState extends State {

	private World world;

	public GameState(GameHandler handler) {
		super(handler);
		world = new World(handler, "res/levels/level1.level");
		handler.setWorld(world);
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
