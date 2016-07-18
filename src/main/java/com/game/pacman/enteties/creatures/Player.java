package com.game.pacman.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.gfx.Assets;
import com.game.pacman.input.KeyManager;
import com.game.pacman.levels.Handler;
import com.game.pacman.tiles.Tile;

/**
 * The hero class of the game!
 * @author patriknygren
 *
 */
public class Player extends Creature {
	private float boundsCover = 0.90f; // proportion of how much bounds cover body
	
	public Player(Handler h, int x, int y) {
		super(h, x, y, Tile.TILESIZE, Tile.TILESIZE);

		// Override default settings of entity to not cover whole body
		float offset = Tile.TILESIZE * (1-boundsCover);
		bounds.x = (int)(offset/2);
		bounds.y = (int)(offset/2);
		bounds.width = (int) (Tile.TILESIZE * boundsCover);
		bounds.height = (int) (Tile.TILESIZE * boundsCover);

	}

	@Override
	public void tick() {
		getInput();
		move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.playerEClosed, (int) x, (int) y, Tile.TILESIZE, Tile.TILESIZE, null);
	}

	/**
	 * Reads in the input from user and stores the appropriate changes for next tick
	 */
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

}
