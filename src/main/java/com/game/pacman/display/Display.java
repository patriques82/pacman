package com.game.pacman.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * The game display, manages JFrame and Canvas
 * @author patriknygren
 *
 */
public class Display {
	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;
	
	public Display(String title, int w, int h) {
		this.title = title;
		width = w;
		height = h;
		initializeDisplay();
	}
	
	private void initializeDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // center on screen
		frame.setVisible(true);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false); // only frame can have focus

		frame.add(canvas);
		frame.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void addKeyListener(KeyListener l) {
		frame.addKeyListener(l);
	}
}
