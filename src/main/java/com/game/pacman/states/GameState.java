package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.world.Observer;
import com.game.pacman.world.World;

public class GameState extends State implements Observer {

	private World world;
	private boolean hasEnded;

	public GameState() {
		world = new World("res/levels/level1.level");
		world.registerObserver(this);
		hasEnded = false;
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

	@Override
	public void update() {
		hasEnded = true;
		System.out.println("hasEnded: " + hasEnded);
	}

	@Override
	public boolean hasEnded() {
		return hasEnded;
	}



}
