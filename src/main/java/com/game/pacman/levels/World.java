package com.game.pacman.levels;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.game.pacman.tiles.BlockTile;
import com.game.pacman.tiles.EmptyTile;
import com.game.pacman.tiles.Tile;

/**
 * Represents the world and the static tiles
 * @author patriknygren
 *
 */
public class World {
	
	private int[][] tiles; 
	private int width, height; // width and height of level in tiles
	private int startX, startY; // start position for player
	private Handler handler;

	public static Tile[] TILE_TYPES = new Tile[100]; // 100 different types
	private static Tile EMPTY = new EmptyTile(0);
	private static Tile BLOCK = new BlockTile(1);
	private static Tile POINT = new EmptyTile(2);

	public World(Handler handler, String path) {
		this.handler = handler;
		addTile(EMPTY);
		addTile(BLOCK);
		addTile(POINT);
		loadLevel(path);
	}

	/**
	 * Renders the world
	 * @param g
	 */
	public void render(Graphics g) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(g, x*Tile.TILESIZE, y*Tile.TILESIZE);
			}
		}
	}
	
	/**
	 * Getter for start x position for player
	 * @return startX start x position
	 */
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Getter for start y position for player
	 * @return startY start y position
	 */
	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Adds new type of tile to available tiles in the world
	 * @param t Tile to add
	 */
	public void addTile(Tile t) {
		if(TILE_TYPES[t.getId()] == null)
			TILE_TYPES[t.getId()] = t;
	}
	
	/**
	 * Takes tile coordinates and return the tile at that position
	 * @param x 
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			Tile tile = TILE_TYPES[tiles[x][y]];
			if(tile == null)
				return EMPTY;
			return tile;
		}
		else { // if player is outside of scope
			return EMPTY;
		}
	}

	private void loadLevel(String path) {
		String file = readStringFromFile(path);
		String[] tokens = file.split("\\s+");
		width = parseInt(tokens[0]);
		height = parseInt(tokens[1]);
		startX = parseInt(tokens[2]);
		startY = parseInt(tokens[3]);
		tiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = parseInt(tokens[(x + (y * width)) + 4]);
			}
		}
	}
	
	private static String readStringFromFile(String path) {
		StringBuilder builder = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	private static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
