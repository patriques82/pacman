package com.game.pacman.world.enteties.creatures.agent;

public interface Strategy {

	public void findPath(float startX, float startY, float destX, float destY);

	public int getYDir(float currentY);

	public int getXDir(float currentX);

}
