package com.game.pacman.enteties;

import java.awt.Graphics;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	
	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public abstract void setPosition();

	public abstract void tick();

	public abstract void render(Graphics g);
}
