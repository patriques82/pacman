package com.game.pacman.states;

import java.awt.Graphics;

import com.game.pacman.Game;
import com.game.pacman.world.World;
import com.game.pacman.world.observer.Observer;

public class GameState extends State implements Observer {

	private World world;
	private Game game;

	public GameState(Game game) {
		world = new World("res/levels/level1.level");
		world.registerObserver(this);
		this.game = game;
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
		State gameState;
		int centerX = (int) world.getWidth()/2;
		int centerY = (int) world.getHeigth()/2;
		if(world.getEntityManager().getPlayer().hasWon()) {
			gameState = new GameOverState(game, "Congratulations!", centerX, centerY);
		} else {
			gameState = new GameOverState(game, "Game Over", centerX, centerY);
		}
		game.setGameState(gameState);
	}


}
