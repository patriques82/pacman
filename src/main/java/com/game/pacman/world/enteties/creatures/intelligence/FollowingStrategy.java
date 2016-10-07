package com.game.pacman.world.enteties.creatures.intelligence;

public class FollowingStrategy implements Strategy {
	private enum Movement { FORWARD, STOP, REVERSE };

	@Override
	public void findPath() {
		// TODO Auto-generated method stub
	}

	private void setDirection() {
		/*
		float px = handler.getPlayer().getX();
		float py = handler.getPlayer().getY();
		Movement xMove = getMovement(px, getX());
		Movement yMove = getMovement(py, getY());
		if(xMove == Movement.FORWARD)
			setDx(speed);
		if(xMove == Movement.REVERSE)
			setDx(-speed);
		if(yMove == Movement.FORWARD)
			setDy(speed);
		if(yMove == Movement.REVERSE)
			setDy(-speed);
		*/
	}
	
	private Movement getMovement(float playerPos, float entityPos) {
		float d = playerPos - entityPos;
		if(d > 0.1)
			return Movement.FORWARD;
		else if(d < 0.1)
			return Movement.REVERSE;
		else
			return Movement.STOP;
	}


}
