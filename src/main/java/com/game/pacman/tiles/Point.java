package com.game.pacman.tiles;

import com.game.pacman.gfx.Assets;

public class Point extends Tile {

	public Point(int id) {
		super(Assets.point, id);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
