package com.game.pacman.enteties;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.pacman.levels.GameHandler;
import com.game.pacman.tiles.Tile;

public abstract class Entity {

	protected float x, y; // coordinates in tiles
	protected int width, height;
	protected Rectangle bounds;
	protected GameHandler handler;
	
	public Entity(GameHandler handler, int x, int y, int w, int h) {
		this.handler = handler;
		this.x = x * Tile.TILESIZE;
		this.y = y * Tile.TILESIZE;
		width = w * Tile.TILESIZE;
		height = h * Tile.TILESIZE;
		bounds = new Rectangle(0, 0, width, height); // default bounds is same size as entity
	}

	public abstract void tick();
	public abstract void render(Graphics g);

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
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x + bounds.x),(int)(y + bounds.y), width, height);
	}

}
