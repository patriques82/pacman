package com.game.pacman.world.gfx;

import java.awt.image.BufferedImage;

public class Frames {

	private BufferedImage[] down;
	private BufferedImage[] up;
	private BufferedImage[] left;
	private BufferedImage[] right;
	
	public Frames(BufferedImage[] down, BufferedImage[] up, BufferedImage[] left, BufferedImage[] right) {
		this.down = down;
		this.up = up;
		this.left = left;
		this.right = right;
	}

	public BufferedImage[] getDown() {
		return down;
	}

	public BufferedImage[] getUp() {
		return up;
	}

	public BufferedImage[] getRight() {
		return right;
	}

	public BufferedImage[] getLeft() {
		return left;
	}


}
