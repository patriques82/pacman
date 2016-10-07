package com.game.pacman.world.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage[] playerDown   = new BufferedImage[2];
	public static BufferedImage[] playerUp     = new BufferedImage[2];
	public static BufferedImage[] playerLeft   = new BufferedImage[2];
	public static BufferedImage[] playerRight  = new BufferedImage[2];
	
	public static BufferedImage[] monsterDown   = new BufferedImage[2];
	public static BufferedImage[] monsterUp     = new BufferedImage[2];
	public static BufferedImage[] monsterLeft   = new BufferedImage[2];
	public static BufferedImage[] monsterRight  = new BufferedImage[2];

	public static BufferedImage empty, point, block; 

	public static void init() {
		SpriteSheet creatures = new SpriteSheet(ImageLoader.loadImage("/textures/spriteschar.png"));
		SpriteSheet world = new SpriteSheet(ImageLoader.loadImage("/textures/spritesworld.png"));

		playerRight[0] = creatures.crop(32*10, 0,    32, 32);
		playerRight[1] = creatures.crop(32*11, 0,    32, 32);
		playerDown[0]  = creatures.crop(32*10, 32,   32, 32);
		playerDown[1]  = creatures.crop(32*11, 32,   32, 32);
		playerLeft[0]  = creatures.crop(32*10, 32*2, 32, 32);
		playerLeft[1]  = creatures.crop(32*11, 32*2, 32, 32);
		playerUp[0]    = creatures.crop(32*10, 32*3, 32, 32);
		playerUp[1]    = creatures.crop(32*11, 32*3, 32, 32);

		monsterRight[0] = creatures.crop(0,    0,    32, 32);
		monsterRight[1] = creatures.crop(32,   0,    32, 32);
		monsterDown[0]  = creatures.crop(0,    32,   32, 32);
		monsterDown[1]  = creatures.crop(32,   32,   32, 32);
		monsterLeft[0]  = creatures.crop(0,    32*2, 32, 32);
		monsterLeft[1]  = creatures.crop(32,   32*2, 32, 32);
		monsterUp[0]    = creatures.crop(0,    32*3, 32, 32);
		monsterUp[1]    = creatures.crop(32,   32*3, 32, 32);

		block = world.crop(0, 0, 96, 96);
		empty = block.getSubimage(12, 12, 72, 72);
		point = world.crop(299, 75, 9, 9);

	}
}
