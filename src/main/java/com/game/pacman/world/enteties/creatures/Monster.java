package com.game.pacman.world.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.intelligence.Context;
import com.game.pacman.world.enteties.creatures.intelligence.FollowingStrategy;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;
import com.game.pacman.world.tiles.Tile;

public class Monster extends CreatureEntity {

	private Animation animation;
	private World world;
	private Context ctx;
	
	public Monster(int x, int y, World w) {
		super(x, y, 1, 1); // size: 1 * 1 tiles
		ctx = new Context(new FollowingStrategy());
		world = w;
		animation = new Animation(500, Assets.monsterUp);
	}

	@Override
	public void tick() {
		ctx.move(); // TODO: should be intelligent
		// Set correct animation
		if(getDir() == Direction.DOWN)
			animation.setFrames(Assets.monsterDown);
		if(getDir() == Direction.UP)
			animation.setFrames(Assets.monsterUp);
		if(getDir() == Direction.RIGHT)
			animation.setFrames(Assets.monsterRight);
		if(getDir() == Direction.LEFT)
			animation.setFrames(Assets.monsterLeft);
		animation.tick();
		move(world);
	}

// ************************************************************************************
// ************************ Sets direction (AI) ***************************************
// ************************************************************************************


	@Override
	public void render(Graphics g) {
		g.drawImage(animation.currentFrame(), (int) x, (int) y, Tile.TILESIZE, Tile.TILESIZE, null);
//		g.fillRect((int) (bounds.x + x),(int) (bounds.y + y), bounds.width, bounds.height);
	}

	@Override
	public void enemyCollision(int x, int y, int dx, int dy) {
		// TODO: bounce
	}

}
