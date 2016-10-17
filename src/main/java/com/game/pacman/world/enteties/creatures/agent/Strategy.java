package com.game.pacman.world.enteties.creatures.agent;

public interface Strategy {

	public void findPath(int currentX, int currentY, int playerX, int playerY);

	public int getYDir(int currentY);

	public int getXDir(int currentX);


}
