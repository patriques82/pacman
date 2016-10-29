package com.game.pacman.world.enteties.creatures.monster;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.Player;

public class MonsterFactory {

	private World world;
	private Player player;

	public MonsterFactory(World world, Player player) {
		this.world = world;
		this.player = player;
	}

	public Monster getMonster(Monster.Type type, int x, int y) {
		switch(type) {
			case RED:
				return new RedMonster(x, y, world, player);
			case YELLOW:
				return new YellowMonster(x, y, world, player);
			case AZURE:
				return new AzureMonster(x, y, world, player);
			case PURPLE:
				return new PurpleMonster(x, y, world, player);
			default:
				return null;
		}
	}

}
