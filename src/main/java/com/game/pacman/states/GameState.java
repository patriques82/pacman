package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.world.World;
import com.game.pacman.world.observer.Observer;

public class GameState extends State implements Observer {

	private World world;

	public GameState() {
		world = new World("res/levels/level1.level");
		world.registerObserver(this);
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

	// observer
	@Override
	public void update() {
		
	}


}
