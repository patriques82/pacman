package com.game.pacman.enteties.creatures;

import com.game.pacman.enteties.Entity;

public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALH = 10;
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_WIDTH = 64,
							DEFAULT_HEIGHT = 64;
	protected int health;
	protected float speed;
	protected float dx, dy;

	public Creature(int x, int y, int width, int height) {
		super(x, y, width, height);
		health = DEFAULT_HEALH;
		speed = DEFAULT_SPEED;
	}
	
	public void move() {
		x += dx;
		y += dy;
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
