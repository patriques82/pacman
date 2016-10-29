package com.game.pacman.world.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.agent.Agent;
import com.game.pacman.world.enteties.creatures.agent.RandomStrategy;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.tiles.Tile;

public abstract class Monster extends CreatureEntity {

	protected Animation animation;
	private World world;
	private Agent agent;
	private Player player;
	
	public Monster(int x, int y, World w, Player p) {
		super(x, y, 1, 1); // size: 1 * 1 tiles
		world = w;
		player = p;
		int[][] invertedTiles = w.getInvertedTiles();
//		agent = new Agent(new FollowingAsyncStrategy(invertedTiles, new AstarOpt(invertedTiles)));
		agent = new Agent(new RandomStrategy(invertedTiles));
	}
	
	public abstract void setAnimation(Direction dir);

	@Override
	public void tick() {
		getInput();
		setAnimation(getDir());
		animation.tick();
		move(world);
	}

// ************************************************************************************
// ************************ Sets direction (AI) ***************************************
// ************************************************************************************

	private void getInput() {
		int x = (int) (getX()/Tile.TILESIZE);
		int y = (int) (getY()/Tile.TILESIZE);
		int px = (int) player.getX()/Tile.TILESIZE;
		int py = (int) player.getY()/Tile.TILESIZE;
		agent.computeDirection(x, y, px, py);
		if(agent.pressUp(x,y))
			setDy(-speed);
		if(agent.pressDown(x,y))
			setDy(speed);
		if(agent.pressLeft(x,y))
			setDx(-speed);
		if(agent.pressRight(x,y))
			setDx(speed);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animation.currentFrame(), (int) getX(), (int) getY(), Tile.TILESIZE, Tile.TILESIZE, null);
//		g.fillRect((int) (bounds.x + x),(int) (bounds.y + y), bounds.width, bounds.height);
	}

	@Override
	public void enemyCollision(int x, int y, int dx, int dy) { }

}
