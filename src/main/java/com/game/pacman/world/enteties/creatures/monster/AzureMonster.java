package com.game.pacman.world.enteties.creatures.monster;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.Player;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;

public class AzureMonster extends Monster {

	public AzureMonster(int x, int y, World w, Player p) {
		super(x, y, w, p);
		animation = new Animation(500, Assets.AzureMonsterUp);
	}

	@Override
	public void setAnimation(Direction dir) {
		if(dir == Direction.DOWN)
			animation.setFrames(Assets.AzureMonsterDown);
		if(dir == Direction.UP)
			animation.setFrames(Assets.AzureMonsterUp);
		if(dir == Direction.RIGHT)
			animation.setFrames(Assets.AzureMonsterRight);
		if(dir == Direction.LEFT)
			animation.setFrames(Assets.AzureMonsterLeft);
		animation.tick();
	}

}
