package com.game.pacman.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile {
	
	public static final int TILEWIDTH = 20, TILEHEIGHT = 20;

	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
	}
	
	public void tick() {
		
	}
	
	public abstract boolean isSolid();
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public int getId() {
		return id;
	}

}
