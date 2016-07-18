package com.game.pacman.levels;

import com.game.pacman.Game;
import com.game.pacman.tiles.Tile;

/**
 * Class for easier handling of states, encapsulating Level and Game
 * @author patriknygren
 *
 */
public class Handler {

	private World world;
	private Game game;
	
	public Handler(Game game) {
		this.game = game;
	}

	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public int getStartX() {
		return world.getStartX() * Tile.TILESIZE;
	}

	public int getStartY() {
		return world.getStartY() * Tile.TILESIZE;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public boolean collidesWithSolid(float x, float y) {
		return world.getTile((int)(x/Tile.TILESIZE), (int) (y/Tile.TILESIZE)).isSolid();
	}
}
