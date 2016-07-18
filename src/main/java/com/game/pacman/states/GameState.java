package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.enteties.creatures.Player;
import com.game.pacman.levels.Handler;
import com.game.pacman.levels.World;

public class GameState extends State {

	private Player player;
	private World world;

	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, "res/levels/level1.level");
		handler.setWorld(world);
		player = new Player(handler, handler.getStartX(), handler.getStartY());
	}
	
	@Override
	public void tick() {
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
	}

}
