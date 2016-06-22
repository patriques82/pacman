package com.game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private final int PHEIGHT;
	private final int PWIDTH;
	
	private Thread animator = null;

	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	
	private Graphics dbg;
	private Image dbImage = null;
	
	public GamePanel() {
		this(500, 400);
	}
	
	public GamePanel(int height, int width) {
		System.out.println("GamePanel");

		PHEIGHT = height;
		PWIDTH = width;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(PHEIGHT, PWIDTH));

		// add interaction
		setFocusable(true);
		requestFocus();
		readyForTermination();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				testPress(e.getX(), e.getY());
			}
		});
	}

	private void testPress(int x, int y) {
		if(!gameOver) {
			System.out.println("X: " + x + ", Y: " + y);
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

	/**
	 * Wait for GamePanel to be added to Parent (JFrame) before starting game loop
	 */
	public void addNotify() {
		super.addNotify();
		System.out.println("AddNotify");
		startGame();
	}

	/**
	 * Initializes and starts the animator thread
	 */
	private void startGame() {
		System.out.println("StartGame");
		if(animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
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
		long beforeTime, timeDiff, sleepTime; // measures for calculating sleep time
		beforeTime = System.currentTimeMillis();
		long period = 1000/100L;
		System.out.println("Run");
		running = true;
		while(running) {
			gameUpdate();
			gameRender();
			paintScreen();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleepTime = period - timeDiff;
			try {
				Thread.sleep(20);
			} catch(InterruptedException e) {}
		}
		System.exit(0);
	}

	/**
	 * Actively render the buffer image to screen
	 */
	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics(); // get panels graphic context
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
		}
		else {
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		
		// draw elements

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
		if(!gameOver) {
			// update
		}
	}

}
