package com.game.pacman.world.enteties;

import java.awt.Graphics;
import java.util.ArrayList;

import com.game.pacman.world.enteties.creatures.CreatureEntity;
import com.game.pacman.world.enteties.creatures.Player;

public class EntityManager {

	private ArrayList<Entity> enteties;
	private ArrayList<CreatureEntity> creatures;
	private Player player;

	public EntityManager() {
		enteties = new ArrayList<>();
		creatures = new ArrayList<>();

	}
	
	public void addCreature(CreatureEntity c) {
		creatures.add(c);
	}
	
	public void addEntity(Entity e) {
		enteties.add(e);
	}
	
	public void tick() {
		for(Entity e : enteties) {
			e.tick();
		}
		for(CreatureEntity c : creatures) {
			c.tick();
		}
	}

	public void render(Graphics g) {
		for(Entity e : enteties) {
			e.render(g);
		}
		for(CreatureEntity c : creatures) {
			c.render(g);
		}
	}


	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		creatures.add(player);
	}

	public ArrayList<Entity> getEnteties() {
		return enteties;
	}

	public void setEnteties(ArrayList<Entity> enteties) {
		this.enteties = enteties;
	}

	public ArrayList<CreatureEntity> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<CreatureEntity> creatures) {
		this.creatures = creatures;
	}
	
}
