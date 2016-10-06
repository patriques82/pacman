package com.game.pacman.states;

public abstract class Observable {

	private Observer observer;
	
	public void registerObserver(Observer observer) {
		this.observer = observer;
	}
	
	public void notifyObserver() {
		observer.update();
	}
	
}
