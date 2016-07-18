package com.game.pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage[] playerDown   = new BufferedImage[2];
	public static BufferedImage[] playerUp     = new BufferedImage[2];
	public static BufferedImage[] playerLeft   = new BufferedImage[2];
	public static BufferedImage[] playerRight  = new BufferedImage[2];

	public static BufferedImage monsterRed, monsterYellow, monsterPink, monsterCyan, monsterPurple,
								empty, point, block; 

	public static void init() {
		SpriteSheet creatures = new SpriteSheet(ImageLoader.loadImage("/textures/spriteschar.png"));
		SpriteSheet world = new SpriteSheet(ImageLoader.loadImage("/textures/spritesworld.png"));

		monsterRed     = creatures.crop(0,    0, 32, 32);
		monsterYellow  = creatures.crop(32*2, 0, 32, 32);
		monsterPink    = creatures.crop(32*4, 0, 32, 32);
		monsterCyan    = creatures.crop(32*6, 0, 32, 32);
		monsterPurple  = creatures.crop(32*8, 0, 32, 32);
		
		playerRight[0] = creatures.crop(32*10, 0,    32, 32);
		playerRight[1] = creatures.crop(32*11, 0,    32, 32);
		playerDown[0]  = creatures.crop(32*10, 32,   32, 32);
		playerDown[1]  = creatures.crop(32*11, 32,   32, 32);
		playerLeft[0]  = creatures.crop(32*10, 32*2, 32, 32);
		playerLeft[1]  = creatures.crop(32*11, 32*2, 32, 32);
		playerUp[0]    = creatures.crop(32*10, 32*3, 32, 32);
		playerUp[1]    = creatures.crop(32*11, 32*3, 32, 32);

		block = world.crop(0, 0, 96, 96);
		empty = block.getSubimage(12, 12, 72, 72);
		point = world.crop(299, 75, 9, 9);

	}
}
