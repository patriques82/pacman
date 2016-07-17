package com.game.pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage playerNorth, playerSouth, playerEast, playerWest,
								monster, point, black, fieldFull, fieldEmpty;

	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sprites.png"));
		fieldFull = sheet.crop(0, 0, 225, 248);
		fieldEmpty = sheet.crop(225, 0, 230, 248);
		point = sheet.crop(5, 5, 14, 14);
		black = sheet.crop(235, 5, 14, 14);
		playerEast = sheet.crop(455, 0, 15, 15);
		playerWest = sheet.crop(457, 16, 15, 15);
		playerNorth = sheet.crop(456, 33, 15, 15);
		playerSouth = sheet.crop(456, 47, 15, 14);

	}
}
