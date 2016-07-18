package com.game.pacman.gfx;

import java.awt.image.BufferedImage;

/**
 * Animation for game entities
 * @author patriknygren
 *
 */
public class Animation {

	private int frameIndex, frameSpeed;
	private BufferedImage[] frames; // frames to rotate through
	private long lastTime, timer;


	public Animation(int speed, BufferedImage[] frames) {
		frameSpeed = speed;
		this.frames = frames;
		lastTime = System.currentTimeMillis();
		frameIndex = 0;
		timer = 0;
	}

	public void tick() {
		long now = System.currentTimeMillis();
		timer += now - lastTime;
		lastTime = now;
		// time to switch frame?
		if(timer > frameSpeed) {
			frameIndex = (frameIndex+1) % frames.length; // increment and wrap around frames
			timer = 0;
		}
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}
	
	public BufferedImage currentFrame() {
		return frames[frameIndex];
	}

}
