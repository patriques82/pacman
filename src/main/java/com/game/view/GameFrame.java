package com.game.view;

import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 50;

	private static int pWidth;
	private static int pHeight;
	
	private GamePanel gp;


	public GameFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setResizable(false);
		calcSizes();
		setResizable(true);
		
		Container c = getContentPane();
		gp = new GamePanel(this, FPS, pWidth, pHeight);
		c.add(gp);
		pack();
		
		addWindowListener(this);
		addComponentListener(new ComponentAdapter() { // make screen jump back when moved from position.:w
			public void componentMoved(ComponentEvent e) {
				setLocation(0,0);
			}
		});

		setResizable(false);
		setVisible(true);
	}
	
	private void calcSizes() {
		GraphicsConfiguration gc = this.getGraphicsConfiguration();
		Rectangle screenRect = gc.getBounds();
		Toolkit tk = Toolkit.getDefaultToolkit();

		Insets desktopInsets = tk.getScreenInsets(gc);
		Insets frameInsets = getInsets();

		pWidth = screenRect.width
				- (desktopInsets.left + desktopInsets.right)
				- (frameInsets.left + frameInsets.right);
		pHeight = screenRect.height
				- (desktopInsets.top + desktopInsets.bottom)
				- (frameInsets.top + frameInsets.bottom);
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
