package com.game.pacman;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.pacman.display.Display;
import com.game.pacman.input.KeyManager;
import com.game.pacman.states.*;
import com.game.pacman.world.gfx.Assets;

/**
 * Main class of game
 * @author patriknygren
 *
 */
public class Game implements Runnable {

	private Display display;
	private int width, height;
	private String title;

	private Thread thread; // animator thread
	private boolean running = false;
	
	// Graphics
	private BufferStrategy bs;
	private Graphics g;
	
	// States
	private State gameState; // TODO:, menuState, settingsState;
	
	// Key manager
	private KeyManager keyMngr;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyMngr = new KeyManager();
	}

	/**
	 * Initializes assets and creates states
	 */
	private void init() {
		display = new Display(title, width, height);
		display.addKeyListener(keyMngr);
		Assets.init();
		gameState = new GameState(this);
//		gameState.setNextState(new GameOverState("Game Over", width/2, height/2));
//		menuState = new MenuState(handler);
//		StateManager.setState(gameState);
	}
	
	public void setGameState(State gameState) {
		this.gameState = gameState;
	}

	/**
	 * Game loop
	 */
	@Override
	public void run() {
		init();

		int fps = 40;
		double timePerTick = 1000_000_000/fps; // nanoseconds
		double delta = 0; // threshold for when to stop, rendering correct fps
		long now;
		long lastTime = System.nanoTime();
		int timer = 0; // count time
		// int ticks = 0; // count ticks

		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick; // increase closer to threshold
			timer += now - lastTime; // increase time
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				// ticks++;
				delta--;
			}
			// clear every second
			if(timer >= 1000_000_000) {
				timer = 0;
				// ticks = 0;
			}
		}
		stop();
	}
	
	/**
	 * Updates the game
	 */
	private void tick() {
		gameState.tick();
		KeyManager.tick();
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
		gameState.render(g);

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
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
