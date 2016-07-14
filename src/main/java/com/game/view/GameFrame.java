package com.game.view;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;
import javax.swing.JFrame;

import com.game.components.Obstacles;
import com.game.components.Worm;

// Full-Screen Exclusive Mode (FSEM)
public class GameFrame extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 60; // frames per second
	private int PHEIGHT;
	private int PWIDTH;

	// number of frames with delay of 0 until animation thread yields to other
	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	private long period;
	private int timeSpentInGame = 0;
	
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
	private GraphicsDevice gd; // graphics card
	private Graphics gScr;
	protected static final int NUM_BUFFERS = 2;
	private BufferStrategy bufferStrategy;

	// static views on screen
	private Font font;
	private FontMetrics metrics;
	private Rectangle pauseArea, quitArea;
	private DecimalFormat df = new DecimalFormat("0.##");
	private DecimalFormat timedf = new DecimalFormat("0.####");
	
	// game components
	private Obstacles obs;
	private Worm fred;
	private long gameStartTime;
	private int boxesUsed = 0;

	// Statistics values
	private static final boolean SHOW_STATISTICS_GATHERING = true; // decide if you want print out statistics
	private static long MAX_STATS_INTERVAL = 1000_000_000L; // record statistics every second
	private long statsInterval = 0L;
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long statsCount = 0;

	private static int NUM_FPS = 10; // number of FPS stored to get average
	private long frameCount = 0;
	private double averageFPS = 0.0;
	private double[] fpsStore;

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double averageUPS = 0.0;
	private double[] upsStore;

	public GameFrame(String name) {
		super(name);
		initFullScreen();
		
		// create game components
		obs = new Obstacles(this);
		fred = new Worm(PWIDTH, PHEIGHT, obs);
		pauseArea = new Rectangle(PWIDTH-100, PHEIGHT-45, 70, 15);
		quitArea = new Rectangle(PWIDTH-200, PHEIGHT-45, 70, 15);

		// add interaction
		setFocusable(true);
		requestFocus();
		readyForTermination();
		addMouseMotionListener(new MouseMotionAdapter() { // handle hover over buttons
			public void mouseMoved(MouseEvent e) {
				testMove(e.getX(), e.getY());
			}
		});
		addMouseListener(new MouseAdapter() { // handle mouse clicks
			public void mousePressed(MouseEvent e) {
				testPress(e.getX(), e.getY());
			}
		});
		
		// set up message font
		font = new Font("SansSerif", Font.BOLD, 24);
		metrics = getFontMetrics(font);
		
		// initialize timing elements
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];
		for(int i=0; i<NUM_FPS; i++) {
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
		startGame();
		
	}

	private void initFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice(); // graphics card
		setUndecorated(true); // no menu bar, border etc
		setIgnoreRepaint(true); // turn of paint events, active rendering
		setResizable(false);
		if(!gd.isFullScreenSupported()) {
			// TODO: turn on AFS
			System.out.println("Full-screen exclusive mode not supported.");
			System.exit(0);
		}
		gd.setFullScreenWindow(this); // turn on FSEM
		showCurrentMode();
		// setDisplayMode(800, 600, 8); // 8 bits
		PWIDTH = getBounds().width;
		PHEIGHT = getBounds().height;
		setBufferStrategy();
	}
	
	/**
	 *  Strategy for updating screen. Creates a new strategy for multi-buffering on this component.
	 *  Multi-buffering is useful for rendering performance. This method attempts to create the
	 *  best strategy available with the number of buffers supplied. It will always create a
	 *  BufferStrategy with that number of buffers. A page-flipping strategy is attempted first,
	 *  then a blitting strategy using accelerated buffers. Finally, an unaccelerated blitting
	 *  strategy is used.
	 */
	private void setBufferStrategy() {
		try {
			// used for preventing deadlock between createBufferStrategy and event dispatcher
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					createBufferStrategy(NUM_BUFFERS);
				}
			});
		}
		catch(Exception e) {
			System.out.println("Error creating buffer strategy");
			System.exit(0);
		}
		try {
			Thread.sleep(500); // give time to buffer strategy to be done
		}
		catch(InterruptedException e) {}
		bufferStrategy = getBufferStrategy();
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
			if(!paused && !gameOver) {
				if(fred.nearHead(x,y)) {
					gameOver = true;
					score = (40 - timeSpentInGame) + 40 - obs.getNumObstacles();
				}
				else {
					if(!fred.touchedAt(x,y)) {
						obs.add(x,y);
					}
				}
			}
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

	public void setBoxes(int no) {
		boxesUsed = no;
	}

	/**
	 * Initializes and starts the animator thread
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
			printStats();
			restoreScreen();
			System.exit(0);
		}
	}
	
	private void restoreScreen() {
		Window w = gd.getFullScreenWindow();
		if(w != null) {
			w.dispose();
		}
		gd.setFullScreenWindow(null);
	}

	/**
	 * Game loop
	 */
	@Override
	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime; // measures for calculating sleep time
		period = 1000_000_000L/FPS; // nanoseconds
		long overSleepTime = 0L;
		long excess = 0L;
		int noDelays = 0;

		gameStartTime = System.nanoTime();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime; // before game loop

		running = true;
		while(running) {
			gameUpdate();
			screenUpdate(); // render one buffer

			// Statistics 
			afterTime = System.nanoTime(); // after loop
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime; // over sleep adjustment from before  
			if(sleepTime > 0) { // time left in cycle
				try {
					Thread.sleep(sleepTime/1000_000L);
				} catch(InterruptedException e) {}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime; // ideally 0
			}
			else { // frame took longer than the period (<= 0), no sleep
				overSleepTime = 0L;
				if(++noDelays >= NO_DELAYS_PER_YIELD) { // yield if it happened often
					Thread.yield();
					noDelays = 0;
				}
				excess -= sleepTime; // sleepTime < 0 so to calculate excess store negative value
			}
			beforeTime = System.nanoTime();

			int skips = 0;
			while((excess > period) && (skips < MAX_FRAME_SKIPS)) { // update state without rendering
				excess -= period;
				gameUpdate();
				skips++;
			}
			framesSkipped += skips;
			storeStats();
		}
		printStats();
		System.exit(0);
	}
	

	private void screenUpdate() {
		try {
			gScr = bufferStrategy.getDrawGraphics();
			gameRender(gScr);
			gScr.dispose();
			if(!bufferStrategy.contentsLost()) {
				bufferStrategy.show();
			}
			else {
				System.out.println("Contents lost");
			}
			Toolkit.getDefaultToolkit().sync();
		}
		catch(Exception e) {
			e.printStackTrace();
			running = false;
		}
	}


	/**
	 * Double buffering
	 * @param gScr 
	 */
	private void gameRender(Graphics gSrc) {
		gSrc.setColor(Color.WHITE);
		gSrc.fillRect(0, 0, PWIDTH, PHEIGHT);
		gSrc.setColor(Color.BLUE);
		gSrc.setFont(font);
		
		// report FPS and UPS if asked for at top left
		if(SHOW_STATISTICS_GATHERING) {
			gSrc.setColor(Color.BLUE);
			String stat = "Average FPS/UPS: " + df.format(averageFPS) + "/" + df.format(averageUPS);
			gSrc.drawString(stat, 20, 30);
		}

		// report time used and boxes used at bottom left
		gSrc.drawString("Time Spent: " + timeSpentInGame + " sec", 10, PHEIGHT-15);
		gSrc.drawString("Boxes Used: " + boxesUsed, 260, PHEIGHT-15);
		
		// draw control buttons
		drawButtons(gSrc);

		// draw elements
		gSrc.setColor(Color.BLACK);
		obs.draw(gSrc);
		fred.draw(gSrc);

		if(gameOver) {
			gameOverMessage(gSrc);
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

	private void gameUpdate() {
		if(!gameOver && !paused) {
			fred.move();
		}
	}
	
	///// Statistics 
	
	private void storeStats() {
		frameCount++; // storeStats is called after one frame
		statsInterval += period; // (ms -> ns) accumulate until threshold
		
		if(statsInterval >= MAX_STATS_INTERVAL) {
			long timeNow = System.nanoTime();
			timeSpentInGame = (int) ((timeNow - gameStartTime)/1000_000_000L); // seconds

			long realElapsedTime = timeNow - prevStatsTime; // time since last collection
			totalElapsedTime += realElapsedTime;
			double timingError = ((double) (realElapsedTime - statsInterval) / statsInterval) * 100.0;
			totalFramesSkipped += framesSkipped;

			double actualFPS = 0;
			double actualUPS = 0;
			if(totalElapsedTime > 0) {
				actualFPS = ((double) frameCount / totalElapsedTime) * 1000_000_000L;
				actualUPS = (((double) frameCount+totalFramesSkipped) / totalElapsedTime) * 1000_000_000L;
			}
			
			fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
			upsStore[(int) statsCount % NUM_FPS] = actualUPS;
			statsCount++;
			
			double totalFPS = 0.0;
			double totalUPS = 0.0;
			for(int i=0; i < NUM_FPS; i++) {
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}
			
			if(statsCount < NUM_FPS) {
				averageFPS = totalFPS/statsCount;
				averageUPS = totalUPS/statsCount;
			}
			else {
				averageFPS = totalFPS/NUM_FPS;
				averageUPS = totalUPS/NUM_FPS;
			}
			
			if(SHOW_STATISTICS_GATHERING) {
				System.out.println(
					timedf.format((double) statsInterval/1000_000_000L) + " " + 		// time since last output
					timedf.format((double) realElapsedTime/1000_000_000L) + "s " +		// real time since last
					df.format(timingError) + "% " +										// percentage error
					frameCount + "c " +
					framesSkipped + "/" + totalFramesSkipped + " skip; " +
					df.format(actualFPS) + " " + df.format(averageFPS) + " afps; " +
					df.format(actualUPS) + " " + df.format(averageUPS) + " aups");
			}

			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L;  // reset
		}
	}
	
	private void printStats() {
		System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " sec");
	}

}
