package com.game.pacman.world.tiles;

import java.awt.Graphics;

import com.game.pacman.world.gfx.Assets;

public class PointTile extends Tile {

	public PointTile(int id) {
		super(Assets.empty, id);
	}

	@Override
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		g.drawImage(Assets.point, x+12, y+12, 9, 9, null); // draw point on top
	}
}
