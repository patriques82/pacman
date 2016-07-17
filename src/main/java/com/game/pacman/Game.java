package com.game.pacman;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.pacman.display.Display;
import com.game.pacman.gfx.Assets;
import com.game.pacman.input.KeyManager;

/**
 * Main class of game
 * @author patriknygren
 *
 */
public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;

	private Thread thread; // animator thread
	private boolean running = false;
	
	// Graphics
	private BufferStrategy bs;
	private Graphics g;
	
	// States
	private State gameState, menuState, settingsState;
	
	// Key manager
	private KeyManager keyMngr;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyMngr = new KeyManager();
	}

	private void init() {
		display = new Display(title, width, height);
		display.addKeyListener(keyMngr);
		Assets.init();
		gameState = new GameState(this);
		menuState = new MenuState(this);
		StateManager.setState(gameState);
	}

	/**
	 * Game loop
	 */
	@Override
	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000_000_000/fps; // nanoseconds
		double delta = 0; // threshold for when to stop, rendering correct fps
		long now;
		long lastTime = System.nanoTime();
		int timer = 0; // count time
		int ticks = 0; // count ticks

		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick; // increase closer to threshold
			timer += now - lastTime; // increase time
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			// clear every second
			if(timer >= 1000_000_000) {
				System.out.println("Ticks and Frames: " + ticks);
				timer = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	/**
	 * Updates the game
	 */
	private void tick() {
		StateManager.tick();
		keyMngr.tick();
	}


	/**
	 * Renders the game
	 */
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return; // exit to avoid errors
		}
		g = bs.getDrawGraphics();
		// Clear screen
		g.clearRect(0, 0, width, height);

		// Draw screen
		StateManager.render(g);

		// Clean up
		bs.show();
		g.dispose();
	}
	
	/**
	 * Starts game loop
	 */
	public synchronized void start() {
		if(!running) {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Stops game loop
	 */
	public synchronized void stop() {
		if(running) {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {}
		}
	}
	
	public KeyManager getKeyManager() {
		return keyMngr;
	}

}