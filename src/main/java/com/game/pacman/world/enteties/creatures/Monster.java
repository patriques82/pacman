package com.game.pacman.world.enteties.creatures;

import java.awt.Graphics;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.agent.Agent;
import com.game.pacman.world.enteties.creatures.agent.FollowingStrategy;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;
import com.game.pacman.world.tiles.Tile;

public class Monster extends CreatureEntity {

	private Animation animation;
	private World world;
	private Agent agent;
	private Player player;
	
	public Monster(int x, int y, World w, Player p) {
		super(x, y, 1, 1); // size: 1 * 1 tiles
		world = w;
		player = p;
		agent = new Agent(new FollowingStrategy(w.getTiles()));
		animation = new Animation(500, Assets.monsterUp);
	}
	
	public void setAnimation(Direction dir) {
		
	}

	// TODO: make to template method but keep animation class in Monster and Player
	@Override
	public void tick() {
		getInput();

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


	private void getInput() {
		agent.computeDirection(getX(), getY(), player.getX(), player.getY()); // TODO: should be intelligent
		if(agent.pressUp(getX(), getY()))
			setDy(-speed);
		if(agent.pressDown(getX(), getY()))
			setDy(speed);
		if(agent.pressLeft(getX(), getY()))
			setDx(-speed);
		if(agent.pressRight(getX(), getY()))
			setDx(speed);
	}



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
