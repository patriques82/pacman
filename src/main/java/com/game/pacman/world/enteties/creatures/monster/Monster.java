package com.game.pacman.world.enteties.creatures.monster;

import java.awt.Graphics;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.CreatureEntity;
import com.game.pacman.world.enteties.creatures.Player;
import com.game.pacman.world.enteties.creatures.agent.Agent;
import com.game.pacman.world.enteties.creatures.agent.RandomStrategy;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.tiles.Tile;

public abstract class Monster extends CreatureEntity {

	public enum Type { RED, YELLOW, AZURE, PURPLE };

	protected Animation animation;
	protected Agent agent;

	private World world;
	private Player player;
	
	public Monster(int x, int y, World w, Player p) {
		super(x, y, 1, 1); // size: 1 * 1 tiles
		world = w;
		player = p;
		agent = new Agent(new RandomStrategy(w.getInvertedTiles()));
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
		agent.computeDirection(this, player.getX(), player.getY());
		if(agent.pressUp(this))
			setDy(-speed);
		if(agent.pressDown(this))
			setDy(speed);
		if(agent.pressLeft(this))
			setDx(-speed);
		if(agent.pressRight(this))
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
