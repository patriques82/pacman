package com.game.pacman.tiles;


public class Wall extends Tile {

	public Wall(int id) {
		super(null, id);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
