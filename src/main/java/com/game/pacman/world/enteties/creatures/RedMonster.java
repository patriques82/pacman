package com.game.pacman.world.enteties.creatures;

import com.game.pacman.world.World;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;

public class RedMonster extends Monster {

	public RedMonster(int x, int y, World w, Player p) {
		super(x, y, w, p);
		animation = new Animation(500, Assets.RedMonsterUp);
	}

	@Override
	public void setAnimation(Direction dir) {
		if(dir == Direction.DOWN)
			animation.setFrames(Assets.RedMonsterDown);
		if(dir == Direction.UP)
			animation.setFrames(Assets.RedMonsterUp);
		if(dir == Direction.RIGHT)
			animation.setFrames(Assets.RedMonsterRight);
		if(dir == Direction.LEFT)
			animation.setFrames(Assets.RedMonsterLeft);
	}

}
