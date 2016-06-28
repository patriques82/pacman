package com.game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import com.game.components.Obstacles;
import com.game.components.Worm;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private int PHEIGHT;
	private int PWIDTH;
	private long FRAMES_PER_SECOND;

	// number of frames with delay of 0 until animation thread yields to other
	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	private long period;

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

	private int timeSpentInGame = 0;
	private DecimalFormat df = new DecimalFormat("0.##");
	private DecimalFormat timedf = new DecimalFormat("0.####");
	
	// animator thread
	private Thread animator = null;

	// game controls
	private volatile boolean running = false;
	private volatile boolean paused = false;
	private volatile boolean gameOver = false;
	private int score = 0;
	
	// graphics
	private Graphics dbg;
	private Image dbImage = null;
	private GameFrame gfTop;

	// message font
	private Font font;
	private FontMetrics metrics;
	
	// game components
	private Obstacles obs;
	private Worm fred;
	private long gameStartTime;
	private int boxesUsed = 0;
	
	public GamePanel(GameFrame gameFrame, int fps, int width, int height) {
		gfTop = gameFrame;
		FRAMES_PER_SECOND = fps;
		PWIDTH = width;
		PHEIGHT = height;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		
		// create game components
		obs = new Obstacles(this);
		fred = new Worm(PWIDTH, PHEIGHT, obs);

		// add interaction
		setFocusable(true);
		requestFocus();
		readyForTermination();
		addMouseListener(new MouseAdapter() {
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
	}

	private void testPress(int x, int y) {
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
	 * Wait for GamePanel to be added to Parent (JFrame) before starting game loop
	 */
	public void addNotify() {
		super.addNotify();
		startGame();
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
	}
	
	/**
	 * Game loop
	 */
	@Override
	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime; // measures for calculating sleep time
		period = 1000_000_000L/FRAMES_PER_SECOND; // nanoseconds
		long overSleepTime = 0L;
		long excess = 0L;
		int noDelays = 0;

		gameStartTime = System.nanoTime();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;

		running = true;
		while(running) {
			gameUpdate();
			gameRender();
			paintScreen();
			afterTime = System.nanoTime();

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
	


	/**
	 * Actively render the buffer image to screen
	 */
	private void paintScreen() {
		Graphics g;
		try {
			g = getGraphics(); // get panels graphic context
			if((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync(); // sync display on some systems (Linux does not flush display)
			g.dispose();
		} catch(Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}

	/**
	 * Double buffering
	 */
	private void gameRender() {
		if(dbImage == null) {
			dbImage = createImage(PWIDTH, PHEIGHT);
			if(dbImage == null) {
				System.out.println("dbImage == null");
				return;
			}
			else {
				dbg = dbImage.getGraphics();
			}
		}
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		
		if(SHOW_STATISTICS_GATHERING) {
			dbg.setColor(Color.BLUE);
			String stat = "Average FPS/UPS: " + df.format(averageFPS) + "/" + df.format(averageUPS);
			dbg.drawString(stat, 20, 30);
		}

		dbg.drawString("Time Spent: " + timeSpentInGame + " sec", 10, PHEIGHT-15);
		dbg.drawString("Boxes Used: " + boxesUsed, 260, PHEIGHT-15);

		dbg.setColor(Color.BLACK);

		// draw elements
		obs.draw(dbg);
		fred.draw(dbg);

		if(gameOver) {
			gameOverMessage(dbg);
		}
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
