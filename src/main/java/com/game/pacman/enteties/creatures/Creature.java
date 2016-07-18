package com.game.pacman.enteties.creatures;

import com.game.pacman.enteties.Entity;
import com.game.pacman.levels.Handler;

public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALH = 10;
	public static final float DEFAULT_SPEED = 3.0f;

	protected int health;
	protected float speed;
	protected float dx, dy;

	public Creature(Handler h, int x, int y, int width, int height) {
		super(h, x, y, width, height);
		health = DEFAULT_HEALH;
		speed = DEFAULT_SPEED;
	}
	
	/**
	 * Moves creature if user has has sent signal
	 */
	public void move() {
		moveX();
		moveY();
	}
	
	/**
	 * Moves creature in x direction if no collision with solid tiles
	 */
	public void moveX() {
		float newX = x + dx + bounds.x;
		if(dx > 0) { // right
			// check upper right and lower right corners
			if(!handler.collidesWithSolid(newX + bounds.width, y + bounds.y) &&
			   !handler.collidesWithSolid(newX + bounds.width, y + bounds.y + bounds.height))
				x += dx;
		}
		else if(dx < 0) { // left
			// check upper left and lower left corners
			if(!handler.collidesWithSolid(newX, y + bounds.y) &&
			   !handler.collidesWithSolid(newX, y + bounds.y + bounds.height))
				x += dx;
		}
		else { // not moving in x
			
		}
	}
	
	/**
	 * Moves creature in y direction if no collision with solid tiles
	 */
	public void moveY() {
		float newY = y + dy + bounds.y;
		if(dy > 0) { // down
			// check lower left and lower right corners
			if(!handler.collidesWithSolid(x + bounds.x, newY + bounds.height) &&
			   !handler.collidesWithSolid(x + bounds.x + bounds.width, newY + bounds.height))
				y += dy;
		}
		else if(dy < 0) { // up
			// check upper left and upper right corners
			if(!handler.collidesWithSolid(x + bounds.x, newY) &&
			   !handler.collidesWithSolid(x + bounds.x + bounds.width, newY))
				y += dy;
		}
		else { // not moving in y
			
		}
	}

	// Getters, Setters

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}


}
