package com.game.pacman.enteties.creatures;

import java.awt.Graphics;
import java.util.Random;

import com.game.pacman.gfx.Animation;
import com.game.pacman.gfx.Assets;
import com.game.pacman.levels.GameHandler;
import com.game.pacman.tiles.Tile;

public class Monster extends CreatureEntity {

	private Animation animation;
	private Random r;
	private int timer;
	private int lastX, lastY;
	private Direction lastDir;
	private long lastTime, now;
	private long decisionsPerSec = 1l;
	
	public Monster(GameHandler h, int x, int y) {
		super(h, x, y, 1, 1); // size: 1 * 1 tiles
		animation = new Animation(500, Assets.monsterUp);
		r = new Random();
		lastTime = System.currentTimeMillis();
		lastDir = getDir();
	}

	@Override
	public void tick() {
		// Random walk
		setRandomDir();
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
		move();
	}

	// Sets direction randomly (A.I)
	private void setRandomDir() {
		long now = System.currentTimeMillis();
		if(now-lastTime > 1000L/decisionsPerSec) {
			int randDir = r.nextInt(4);
			changeDir(randDir);
			timer++;
			lastTime = System.currentTimeMillis();
			lastDir = dir;
			lastX = (int) x / Tile.TILESIZE;
			lastY = (int) y / Tile.TILESIZE;
		}
		if(isStuck()) { // if stuck
			randomRotate();
		}
		if(timer > 10) {
			lookForPlayer();
			timer = 0; // reset
		}
	}
	
	private void changeDir(int randDir) {
		switch(randDir) {
			case 0: // Right
				stop();
				setDx(speed);
				break;
			case 1: // Left
				stop();
				setDx(-speed);
				break;
			case 2: // Down
				stop();
				setDy(speed);
				break;
			default: // Up
				stop();
				setDy(-speed);
				break;
		}
	}
	
	private void lookForPlayer() {
		float px = handler.getPlayer().getX();
		float py = handler.getPlayer().getY();
		if((px>(x + 50) || px>(x - 50)) && dir == Direction.LEFT) {
			stop();
			setDx(speed); // right
		}
		if((px<(x + 50) || px<(x - 50)) && dir == Direction.RIGHT) {
			stop();
			setDx(-speed); // left
		}
		if((py>(y + 50) || py>(y - 50)) && dir == Direction.UP) {
			stop();
			setDy(speed); // down
		}
		if((py<(y + 50) || py<(y - 50)) && dir == Direction.LEFT) {
			stop();
			setDy(-speed); // up
		}
	}
	
	private void randomRotate() {
		if(r.nextBoolean()) {
			rotateLeft();
		}
		else {
			rotateRight();
		}
	}
	
	private void rotateLeft() {
		switch(dir) {
			case DOWN:
				dir = Direction.RIGHT;
				break;
			case RIGHT:
				dir = Direction.UP;
				break;
			case UP:
				dir = Direction.LEFT;
				break;
			default:
				dir = Direction.DOWN;
				break;
		}
	}

	private void rotateRight() {
		switch(dir) {
			case DOWN:
				dir = Direction.LEFT;
				break;
			case LEFT:
				dir = Direction.UP;
				break;
			case UP:
				dir = Direction.RIGHT;
				break;
			default:
				dir = Direction.DOWN;
				break;
		}
		
	}
	
	private boolean isStuck() {
		return dir == lastDir && x == lastX && y == lastY;
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
