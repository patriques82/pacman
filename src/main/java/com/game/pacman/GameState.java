package com.game.pacman;

import java.awt.Graphics;

import com.game.pacman.enteties.creatures.Player;
import com.game.pacman.gfx.Assets;

public class GameState extends State {

	private Player player;

	public GameState(Game game) {
		super(game);
		player = new Player(game, 100, 100);
	}
	
	@Override
	public void tick() {
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.fieldEmpty, 0, 0, null);
		player.render(g);
	}

}
