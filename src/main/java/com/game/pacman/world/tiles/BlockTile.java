package com.game.pacman.world.tiles;

import com.game.pacman.world.gfx.Assets;

public class BlockTile extends Tile {

	public BlockTile(int id) {
		super(Assets.block, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
