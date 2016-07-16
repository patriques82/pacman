package com.game.main;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;

public class WindowManager {
	
	private GameFrame gf;
	private GraphicsDevice gd; // graphics card

	protected static final int NUM_BUFFERS = 2;
	private BufferStrategy bufferStrategy;
	
	public WindowManager(GameFrame gameFrame) {
		gf = gameFrame;
		initFullScreen();
		setBufferStrategy(NUM_BUFFERS);
	}

	private void initFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice(); // graphics card
		gf.setUndecorated(true); // no menu bar, border etc
		gf.setIgnoreRepaint(true); // turn of paint events, active rendering
		gf.setResizable(false);
		if(!gd.isFullScreenSupported()) {
			// TODO: turn on AFS
			System.out.println("Full-screen exclusive mode not supported.");
			System.exit(0);
		}
		gd.setFullScreenWindow(gf); // turn on FSEM
		showCurrentMode();
		setDisplayMode(800, 600, 8); // 8 bits
//		setDisplayMode(1280, 1024, 32); // 32 bits
	}
	
	/**
	 *  Strategy for updating screen. Creates a new strategy for multi-buffering on this component.
	 *  Multi-buffering is useful for rendering performance. This method attempts to create the
	 *  best strategy available with the number of buffers supplied. It will always create a
	 *  BufferStrategy with that number of buffers. A page-flipping strategy is attempted first,
	 *  then a blitting strategy using accelerated buffers. Finally, an unaccelerated blitting
	 *  strategy is used.
	 */
	private void setBufferStrategy(int buffers) {
		try {
			// used for preventing deadlock between createBufferStrategy and event dispatcher
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					gf.createBufferStrategy(buffers);
				}
			});
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error creating buffer strategy");
			System.exit(0);
		}
		try {
			Thread.sleep(1000); // give time to buffer strategy to be done
		}
		catch(InterruptedException e) {}
		bufferStrategy = gf.getBufferStrategy();
		System.exit(0);
	}


	public int getWidth() {
		return gf.getBounds().width;
	}

	public int getHeight() {
		return gf.getBounds().height;
	}

	public void restoreScreen() {
		Window w = gd.getFullScreenWindow();
		if(w != null) {
			w.dispose();
		}
		gd.setFullScreenWindow(null);
	}


	private void setDisplayMode(int width, int height, int bitDepth) {
		if(!gd.isDisplayChangeSupported()) {
			System.out.println("Display mode changing not supported");
			System.exit(0);
		}
		if(!isDisplayModeAvailable(width, height, bitDepth)) {
			System.out.println("Display mode (" + width + ", " + height + ", " + bitDepth + ") " +
							   "not available");
			System.exit(0);
		}
		DisplayMode dm = new DisplayMode(width, height, bitDepth, DisplayMode.REFRESH_RATE_UNKNOWN);
		try {
			gd.setDisplayMode(dm);
			System.out.println("Display mode set to: (" + width + ", " + height + ", " + bitDepth + ") ");
		}
		catch(IllegalArgumentException e) {
			System.out.println("Error setting display mode to: (" + width + ", " + height + ", " + bitDepth + ") ");
			System.exit(0);
		}
		try { // sleep to give time for the display to be changed
			Thread.sleep(1000); 
		}
		catch(InterruptedException e) {}
	}

	private boolean isDisplayModeAvailable(int width, int height, int bitDepth) {
		DisplayMode[] modes = gd.getDisplayModes();
		for(int i=0; i<modes.length; i++) {
			if(width == modes[i].getWidth() && 
			   height == modes[i].getHeight() &&
			   bitDepth == modes[i].getBitDepth()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Display mode details for the graphics card
	 * (width, height (pixels), bitdepth (bits/pixel), refresh rate)
	 */
	private void showCurrentMode() {
		DisplayMode dm = gd.getDisplayMode();
		System.out.println("Current display mode: (" + dm.getWidth() + ", " + dm.getHeight() + 
						   ", " + dm.getBitDepth() + ", " + dm.getRefreshRate() + ") ");
	}

	public Graphics getGraphics() {
		return bufferStrategy.getDrawGraphics();
	}

	public void clean(Graphics gSrc) {
		gSrc.dispose();
		if(!bufferStrategy.contentsLost()) {
			bufferStrategy.show();
		}
		else {
			System.out.println("Contents lost");
		}
		Toolkit.getDefaultToolkit().sync();
	}

}
