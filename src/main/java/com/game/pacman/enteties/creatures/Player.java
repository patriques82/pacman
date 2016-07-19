package com.game.pacman.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.gfx.Animation;
import com.game.pacman.gfx.Assets;
import com.game.pacman.input.KeyManager;
import com.game.pacman.levels.GameHandler;
import com.game.pacman.tiles.Tile;

/**
 * The hero class of the game!
 * @author patriknygren
 *
 */
public class Player extends CreatureEntity {

	private Animation animation;
	
	public Player(GameHandler h, int x, int y) {
		super(h, x, y, 1, 1); // size: 1 * 1 tiles
		animation = new Animation(500, Assets.playerDown); // open/close mouth every 500 ms
	}

	/**
	 * Get user input, update to right frames (direction) and move
	 */
	@Override
	public void tick() {
		if(this.health == 0)
			handler.getGame().gameOver("Game Over");
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
		g.drawImage(animation.currentFrame(), (int) x, (int) y, width, height, null);
//		g.fillRect((int) (bounds.x + x),(int) (bounds.y + y), bounds.width, bounds.height);
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

	@Override
	public void enemyCollision(int x, int y, int dx, int dy) {
		health = 0;
	}

}
