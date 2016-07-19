package com.game.pacman.enteties;

import java.awt.Graphics;
import java.util.ArrayList;

import com.game.pacman.enteties.creatures.CreatureEntity;
import com.game.pacman.enteties.creatures.Monster;
import com.game.pacman.enteties.creatures.Player;
import com.game.pacman.levels.GameHandler;

public class EntityManager {

	private GameHandler handler;
	private Player player;
	private ArrayList<Entity> enteties;
	private ArrayList<CreatureEntity> creatures;

	public EntityManager(GameHandler gh, Player p) {
		handler = gh;
		player = p;
		enteties = new ArrayList<>();
		creatures = new ArrayList<>();
		addCreature(player);

		// hard coded test
		Monster monster = new Monster(gh, 13, 18);
		addCreature(monster);
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
