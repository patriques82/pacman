package com.game.pacman.world.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage[] playerDown   = new BufferedImage[2];
	public static BufferedImage[] playerUp     = new BufferedImage[2];
	public static BufferedImage[] playerLeft   = new BufferedImage[2];
	public static BufferedImage[] playerRight  = new BufferedImage[2];
	
	public static BufferedImage[] RedMonsterDown   = new BufferedImage[2];
	public static BufferedImage[] RedMonsterUp     = new BufferedImage[2];
	public static BufferedImage[] RedMonsterLeft   = new BufferedImage[2];
	public static BufferedImage[] RedMonsterRight  = new BufferedImage[2];

	public static BufferedImage[] YellowMonsterDown  = new BufferedImage[2];
	public static BufferedImage[] YellowMonsterUp    = new BufferedImage[2];
	public static BufferedImage[] YellowMonsterLeft  = new BufferedImage[2];
	public static BufferedImage[] YellowMonsterRight = new BufferedImage[2];

	public static BufferedImage[] AzureMonsterDown  = new BufferedImage[2];
	public static BufferedImage[] AzureMonsterUp    = new BufferedImage[2];
	public static BufferedImage[] AzureMonsterLeft  = new BufferedImage[2];
	public static BufferedImage[] AzureMonsterRight = new BufferedImage[2];

	public static BufferedImage[] PurpleMonsterDown  = new BufferedImage[2];
	public static BufferedImage[] PurpleMonsterUp    = new BufferedImage[2];
	public static BufferedImage[] PurpleMonsterLeft  = new BufferedImage[2];
	public static BufferedImage[] PurpleMonsterRight = new BufferedImage[2];

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

		RedMonsterRight[0] = creatures.crop(0,    0,    32, 32);
		RedMonsterRight[1] = creatures.crop(32,   0,    32, 32);
		RedMonsterDown[0]  = creatures.crop(0,    32,   32, 32);
		RedMonsterDown[1]  = creatures.crop(32,   32,   32, 32);
		RedMonsterLeft[0]  = creatures.crop(0,    32*2, 32, 32);
		RedMonsterLeft[1]  = creatures.crop(32,   32*2, 32, 32);
		RedMonsterUp[0]    = creatures.crop(0,    32*3, 32, 32);
		RedMonsterUp[1]    = creatures.crop(32,   32*3, 32, 32);
		
		YellowMonsterRight[0] = creatures.crop(32*2, 0,    32, 32);
		YellowMonsterRight[1] = creatures.crop(32*3, 0,    32, 32);
		YellowMonsterDown[0]  = creatures.crop(32*2, 32,   32, 32);
		YellowMonsterDown[1]  = creatures.crop(32*3, 32,   32, 32);
		YellowMonsterLeft[0]  = creatures.crop(32*2, 32*2, 32, 32);
		YellowMonsterLeft[1]  = creatures.crop(32*3, 32*2, 32, 32);
		YellowMonsterUp[0]    = creatures.crop(32*2, 32*3, 32, 32);
		YellowMonsterUp[1]    = creatures.crop(32*3, 32*3, 32, 32);
		
		AzureMonsterRight[0] = creatures.crop(32*6, 0,    32, 32);
		AzureMonsterRight[1] = creatures.crop(32*7, 0,    32, 32);
		AzureMonsterDown[0]  = creatures.crop(32*6, 32,   32, 32);
		AzureMonsterDown[1]  = creatures.crop(32*7, 32,   32, 32);
		AzureMonsterLeft[0]  = creatures.crop(32*6, 32*2, 32, 32);
		AzureMonsterLeft[1]  = creatures.crop(32*7, 32*2, 32, 32);
		AzureMonsterUp[0]    = creatures.crop(32*6, 32*3, 32, 32);
		AzureMonsterUp[1]    = creatures.crop(32*7, 32*3, 32, 32);

		PurpleMonsterRight[0] = creatures.crop(32*8, 0,    32, 32);
		PurpleMonsterRight[1] = creatures.crop(32*9, 0,    32, 32);
		PurpleMonsterDown[0]  = creatures.crop(32*8, 32,   32, 32);
		PurpleMonsterDown[1]  = creatures.crop(32*9, 32,   32, 32);
		PurpleMonsterLeft[0]  = creatures.crop(32*8, 32*2, 32, 32);
		PurpleMonsterLeft[1]  = creatures.crop(32*9, 32*2, 32, 32);
		PurpleMonsterUp[0]    = creatures.crop(32*8, 32*3, 32, 32);
		PurpleMonsterUp[1]    = creatures.crop(32*9, 32*3, 32, 32);

		block = world.crop(0, 0, 96, 96);
		empty = block.getSubimage(12, 12, 72, 72);
		point = world.crop(299, 75, 9, 9);

	}
}
