package com.game.pacman.world.enteties.creatures.agent;

public interface Strategy {
	
	public abstract void findPath(int currentX, int currentY, int playerX, int playerY);

	public abstract int getYDir(int currentX, int currentY);

	public abstract int getXDir(int currentX, int currentY);

	public abstract void setAgent(Agent agent);

}
