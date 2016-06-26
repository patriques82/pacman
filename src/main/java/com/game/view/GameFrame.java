package com.game.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class GameFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField jtfBox;
	private JTextField jtfTime;
	private GamePanel gp;
	private GameControl gc;

	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 430));
		setLayout(new BorderLayout());

		setComponentsToPane(getContentPane());
		pack();
		setVisible(true);
	}
	
	private void setComponentsToPane(Container container) {
		gp = new GamePanel(this, 500, 400);
        gc = new GameControl(35, 400);
		jtfBox = new JTextField();
		jtfTime = new JTextField();
		jtfBox.setVisible(true);
		jtfTime.setVisible(true);
        gc.add(jtfBox);
        gc.add(jtfTime);
        container.add(gp, BorderLayout.CENTER);
		container.add(gc, BorderLayout.SOUTH);
	}

	public void setBoxNumber(int no) {
		jtfBox.setText("Boxes used: " + no);
	}

	public void setTimeSpent(long t) {
		jtfTime.setText("Time spent: " + t + " secs");
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		gp.stopGame();
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {
		gp.pauseGame();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		gp.resumeGame();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		gp.resumeGame();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		gp.pauseGame();
	}

}
