package com.game.main;

import javax.swing.JFrame;

import com.game.view.GamePanel;

public class Main {

	public static void main(String[] args) {
		GamePanel gp = new GamePanel();
		JFrame frame = new JFrame();
		frame.add(gp);
	}

}
