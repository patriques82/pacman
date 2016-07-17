package com.game.pacman.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	
	public static BufferedImage loadImage(String path) {
		BufferedImage bI = null;
		try {
			bI = ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			System.out.println("input == null");
			e.printStackTrace();
			System.exit(1); // no images, no game
		}
		return bI;
	}
}
