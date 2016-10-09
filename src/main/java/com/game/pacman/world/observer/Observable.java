package com.game.pacman.world.observer;

public abstract class Observable {

	private Observer observer;
	
	public void registerObserver(Observer observer) {
		this.observer = observer;
	}
	
	public void notifyObserver() {
		observer.update();
	}
	
}
