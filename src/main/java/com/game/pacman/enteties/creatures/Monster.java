package com.game.pacman.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.gfx.Animation;
import com.game.pacman.gfx.Assets;
import com.game.pacman.levels.GameHandler;
import com.game.pacman.tiles.Tile;

public class Monster extends CreatureEntity {

	private Animation animation;
	
	public Monster(GameHandler h, int x, int y) {
		super(h, x, y, 1, 1); // size: 1 * 1 tiles
		animation = new Animation(500, Assets.monsterUp);
	}

	@Override
	public void tick() {
		// TODO: Random walk
		if(getDir() == Direction.DOWN)
			animation.setFrames(Assets.monsterDown);
		if(getDir() == Direction.UP)
			animation.setFrames(Assets.monsterUp);
		if(getDir() == Direction.RIGHT)
			animation.setFrames(Assets.monsterRight);
		if(getDir() == Direction.LEFT)
			animation.setFrames(Assets.monsterLeft);
		animation.tick();
		move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animation.currentFrame(), (int) x, (int) y, Tile.TILESIZE, Tile.TILESIZE, null);
	}

	@Override
	public void enemyCollision(int x, int y, int dx, int dy) {
		// TODO: bounce
	}

}
