package com.game.pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage playerNOpen, playerSOpen, playerEOpen, playerWOpen,
								playerNClosed, playerSClosed, playerEClosed, playerWClosed,
								monsterRed, monsterYellow, monsterPink, monsterCyan, monsterPurple,
								empty, point, block; 

	public static void init() {
		SpriteSheet creatures = new SpriteSheet(ImageLoader.loadImage("/textures/spriteschar.png"));
		SpriteSheet world = new SpriteSheet(ImageLoader.loadImage("/textures/spritesworld.png"));

		monsterRed = creatures.crop(0, 0, 32, 32);
		monsterYellow = creatures.crop(32*2, 0, 32, 32);
		monsterPink = creatures.crop(32*4, 0, 32, 32);
		monsterCyan = creatures.crop(32*6, 0, 32, 32);
		monsterPurple = creatures.crop(32*8, 0, 32, 32);

		playerEClosed = creatures.crop(32*10, 0, 32, 32);
		playerEOpen = creatures.crop(32*11, 0, 32, 32);
		playerSClosed = creatures.crop(32*10, 32, 32, 32);
		playerSOpen = creatures.crop(32*11, 32, 32, 32);
		playerWClosed = creatures.crop(32*10, 32*2, 32, 32);
		playerWOpen = creatures.crop(32*11, 32*2, 32, 32);
		playerNClosed = creatures.crop(32*10, 32*3, 32, 32);
		playerNOpen = creatures.crop(32*11, 32*3, 32, 32);
		
		block = world.crop(0, 0, 96, 96);
		empty = block.getSubimage(12, 12, 72, 72);
		point = world.crop(299, 75, 9, 9);

	}
}
