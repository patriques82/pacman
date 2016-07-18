package com.game.pacman.tiles;

import com.game.pacman.gfx.Assets;

public class BlockTile extends Tile {

	public BlockTile(int id) {
		super(Assets.block, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
