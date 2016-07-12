package com.game.view;

import java.awt.Container;
import javax.swing.JFrame;

public class GameFrame1 extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 40; // frames per second

	private GamePanel gp;

	public GameFrame1(String name) {
		super(name);
		
		Container container = getContentPane();
		gp = new GamePanel(this, FPS);
		container.add(gp);
		
		setUndecorated(true); // no borders or title bar
		setIgnoreRepaint(true); // turn off paint events (active rendering)
		pack();
		setResizable(false);
		setVisible(true);
	}

	private void initFullScreen() {
		// TODO Auto-generated method stub
		
	}
	
}
