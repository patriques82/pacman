package com.game.pacman.levels;

import java.util.ArrayList;

import com.game.pacman.Game;
import com.game.pacman.enteties.creatures.CreatureEntity;
import com.game.pacman.tiles.Tile;

/**
 * Class for easier handling of states, encapsulating Level and Game
 * @author patriknygren
 *
 */
public class GameHandler {

	private World world;
	private Game game;
	
	public GameHandler(Game game) {
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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

	public ArrayList<CreatureEntity> getCreatures() {
		return world.getEntityManager().getCreatures();
	}
	
}
