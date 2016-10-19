package com.game.pacman.world.enteties.creatures.agent;

public abstract class Strategy {
	
	protected Agent agent;

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public abstract void findPath(int currentX, int currentY, int playerX, int playerY);

	public abstract int getYDir(int currentX, int currentY);

	public abstract int getXDir(int currentX, int currentY);


}
