package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

import com.game.components.Obstacles;
import com.game.components.Pacman;

// Full-Screen Exclusive Mode (FSEM)
public class GameFrame extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 60; // frames per second
	private static int SCREEN_HEIGHT;
	private static int SCREEN_WIDTH;
	private static final int NO_DELAYS_PER_YIELD = 16; // number of frames with delay of 0 until animation thread yields to other
	private static final int MAX_FRAME_SKIPS = 5;
	private static long FRAME_PERIOD = 1000_000_000L/FPS; // nanoseconds
	
	// animator thread
	private Thread animator = null;

	// game controls
	private volatile boolean running = false;
	private volatile boolean paused = false;
	private volatile boolean gameOver = false;
	private boolean finishedOff = false;
	private boolean isOverPauseBtn = false;
	private boolean isOverQuitBtn = false;
	private int score = 0;
	
	// graphics
	private WindowManager windowMngr;
	private Graphics gSrc;

	// static views on screen
	private Font font;
	private Rectangle pauseArea, quitArea;
	
	// game components
	private static int PIXEL_SIZE = 20;
	private Pixels pixels;
	private Obstacles obs;
	private Pacman pacman;
	
	// game statistics
	private Statistics stats;
	private static final boolean SHOW_STATISTICS_GATHERING = true; // decide if you want print out statistics

	public GameFrame(String name) {
		super(name);
		// Initialize FSEM (Full-Screen Exclusive Mode)
		windowMngr = new WindowManager(this);
		SCREEN_WIDTH = windowMngr.getWidth();
		SCREEN_HEIGHT = windowMngr.getHeight();
		System.out.println("hello");

		font = new Font("SansSerif", Font.BOLD, 24);
		
		// create game components
		// TODO: LOGIC
		pixels = Pixels.getInstance();
		pixels.setSize(PIXEL_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
//		pacman = new Pacman();

//		obs = new Obstacles(this);
//		fred = new Worm(SCREEN_WIDTH, SCREEN_HEIGHT, obs);

		pauseArea = new Rectangle(SCREEN_WIDTH-100, SCREEN_HEIGHT-45, 70, 15);
		quitArea = new Rectangle(SCREEN_WIDTH-200, SCREEN_HEIGHT-45, 70, 15);

		// add interaction
		setFocusable(true);
		requestFocus();
		readyForTermination();
		System.out.print("Hello");
//		addMouseMotionListener(new MouseMotionAdapter() { // handle hover over buttons
//			public void mouseMoved(MouseEvent e) {
//				testMove(e.getX(), e.getY()); // TODO: LOGIC
//			}
//		});
//		addMouseListener(new MouseAdapter() { // handle mouse clicks
//			public void mousePressed(MouseEvent e) {
//				testPress(e.getX(), e.getY()); // TODO: LOGIC
//			}
//		});
		
		startGame();
	}

	/**
	 * Initializes and starts the animator thread that runs the game loop
	 */
	private void startGame() {
		if(animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	/**
	 * Tells gameUpdate method that game state does not need to be updated
	 */
	public void pauseGame() {
		paused = true;
	}
	
	/**
	 * Resumes game from paused state
	 */
	public void resumeGame() {
		paused = false;
	}
	
	/**
	 * Stops the animator thread by setting running flag to false
	 */
	public void stopGame() {
		running = false;
		finish(); 
	}
	
	private void finish() {
		if(!finishedOff) {
			finishedOff = true;
			stats.printStats();
			windowMngr.restoreScreen();
			System.exit(0);
		}
	}
	
	/**
	 * Game loop
	 */
	@Override
	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime; // measures for calculating sleep time
		long overSleepTime = 0L;
		long excess = 0L;
		int noDelays = 0;

		long gameStartTime = System.nanoTime();
		beforeTime = gameStartTime;
		stats = new Statistics(gameStartTime, FRAME_PERIOD); // statistics

		running = true;
		while(running) {
			// frame
			gameUpdate();
			screenUpdate();

			// Time adjustments after frame
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			if(afterTime - gameStartTime > 2000_000_000L) // TODO: remove
				running = false;
			sleepTime = (FRAME_PERIOD - timeDiff) - overSleepTime; // over sleep adjustment from before  
			if(sleepTime > 0) { // time left in cycle
				try {
					Thread.sleep(sleepTime/1000_000L);
				} catch(InterruptedException e) {}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime; // ideally 0
			}
			else { // frame took longer than the period (<= 0), no sleep
				overSleepTime = 0L;
				if(++noDelays >= NO_DELAYS_PER_YIELD) { // yield if it happened too often
					Thread.yield();
					noDelays = 0;
				}
				excess -= sleepTime; // sleepTime < 0 so to calculate excess store negative value
			}
			beforeTime = System.nanoTime();

			int skips = 0;
			while((excess > FRAME_PERIOD) && (skips < MAX_FRAME_SKIPS)) { // update state without rendering
				excess -= FRAME_PERIOD;
				gameUpdate();
				skips++;
			}
			stats.addSkippedFrames(skips);
			stats.storeStats(SHOW_STATISTICS_GATHERING);
		}
		System.exit(0);
	}
	
	private void gameUpdate() {
		//// TODO: LOGIC
		if(!gameOver && !paused) {
//			fred.move();
		}
	}

	private void screenUpdate() {
		try {
			gSrc = windowMngr.getGraphics();
			gameRender(gSrc);
			windowMngr.clean(gSrc);
		}
		catch(Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	/**
	 * Render the game state
	 * @param gScr 
	 */
	private void gameRender(Graphics gSrc) {
		gSrc.setColor(Color.WHITE);
		gSrc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		gSrc.setColor(Color.BLUE);
		gSrc.setFont(font);
		
		// report FPS and UPS at top left
		if(SHOW_STATISTICS_GATHERING) {
			stats.displayCurrentStats(gSrc, 20, 30);
		}
		
		///// TODO: LOGIC
		// draw elements

		drawButtons(gSrc);
		pixels.draw(gSrc);

//		gSrc.setColor(Color.BLACK);
//		obs.draw(gSrc);
//		fred.draw(gSrc);

		if(gameOver) {
			gameOverMessage(gSrc);
		}
	}

	/**
	 * User quits by keyboard
	 */
	private void readyForTermination() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if((keyCode == KeyEvent.VK_ESCAPE) ||
				   (keyCode == KeyEvent.VK_Q) ||
				   (keyCode == KeyEvent.VK_END) ||
				   (keyCode == KeyEvent.VK_C) && e.isControlDown() ) {
					running = false;
				}
			}
		});
	}
	
	/////// TODO: LOGIC

	// is mouse over pause or quit
	private void testMove(int x, int y) {
		if(running) {
			isOverPauseBtn = pauseArea.contains(x,y);
			isOverQuitBtn = quitArea.contains(x,y);
		}
	}

	private void testPress(int x, int y) {
		if(isOverPauseBtn) {
			paused = !paused; // toggle
		}
		else if(isOverQuitBtn) {
			running = false;
		}
		else {
//			if(!paused && !gameOver) {
//				if(fred.nearHead(x,y)) {
//					gameOver = true;
//				}
//				else {
//					if(!fred.touchedAt(x,y)) {
//						obs.add(x,y);
//					}
//				}
//			}
		}
	}

	private void drawButtons(Graphics g) {
		g.setColor(Color.BLACK);
		if(isOverPauseBtn)
			g.setColor(Color.ORANGE);
		g.drawOval(pauseArea.x, pauseArea.y, pauseArea.width, pauseArea.height);
		if(paused)
			g.drawString("Paused", pauseArea.x+5, pauseArea.y+10);
		else
			g.drawString("Pause", pauseArea.x+5, pauseArea.y+10);
		if(isOverPauseBtn)
			g.setColor(Color.BLACK);
		if(isOverQuitBtn)
			g.setColor(Color.GREEN);
		g.drawOval(quitArea.x, quitArea.y, quitArea.width, quitArea.height);
		g.drawString("Quit", quitArea.x+15, quitArea.y+10);
		if(isOverQuitBtn)
			g.setColor(Color.BLACK);
	}
	
	private void gameOverMessage(Graphics g) {
		String msg = "Game Over";
		int x = 0;
		int y = 0;
		g.drawString(msg, x, y);
	}

}
