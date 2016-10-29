package com.game.pacman.world.enteties.creatures.monster;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.CreatureEntity;
import com.game.pacman.world.enteties.creatures.Player;
import com.game.pacman.world.enteties.creatures.CreatureEntity.Direction;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;

public class PurpleMonster extends Monster {

	public PurpleMonster(int x, int y, World w, Player p) {
		super(x, y, w, p);
		animation = new Animation(500, Assets.PurpleMonsterUp);
	}

	@Override
	public void setAnimation(Direction dir) {
		if(dir == Direction.DOWN)
			animation.setFrames(Assets.PurpleMonsterDown);
		if(dir == Direction.UP)
			animation.setFrames(Assets.PurpleMonsterUp);
		if(dir == Direction.RIGHT)
			animation.setFrames(Assets.PurpleMonsterRight);
		if(dir == Direction.LEFT)
			animation.setFrames(Assets.PurpleMonsterLeft);
	}

}
