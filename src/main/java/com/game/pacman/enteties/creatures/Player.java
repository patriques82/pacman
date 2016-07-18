package com.game.pacman.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.gfx.Animation;
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
	
	private Animation animation;
	
	public Player(Handler h, int x, int y) {
		super(h, x, y, Tile.TILESIZE, Tile.TILESIZE);
		animation = new Animation(500, Assets.playerDown); // open/close mouth every 500 ms

		// Override default settings of entity to not cover whole body
		float offset = Tile.TILESIZE * (1-boundsCover);
		bounds.x = (int)(offset/2);
		bounds.y = (int)(offset/2);
		bounds.width = (int) (Tile.TILESIZE * boundsCover);
		bounds.height = (int) (Tile.TILESIZE * boundsCover);
	}

	/**
	 * Get user input, update to right frames (direction) and move
	 */
	@Override
	public void tick() {
		getInput();
		if(getDir() == Direction.DOWN)
			animation.setFrames(Assets.playerDown);
		if(getDir() == Direction.UP)
			animation.setFrames(Assets.playerUp);
		if(getDir() == Direction.RIGHT)
			animation.setFrames(Assets.playerRight);
		if(getDir() == Direction.LEFT)
			animation.setFrames(Assets.playerLeft);
		animation.tick();
		move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animation.currentFrame(), (int) x, (int) y, Tile.TILESIZE, Tile.TILESIZE, null);
	}

	/**
	 * Reads in the input from user and stores the appropriate changes for next tick
	 */
	private void getInput() {
		setDx(0);
		setDy(0);
		if(KeyManager.pressUp())
			setDy(-speed);
		if(KeyManager.pressDown())
			setDy(speed);
		if(KeyManager.pressLeft())
			setDx(-speed);
		if(KeyManager.pressRight())
			setDx(speed);
	}

}
