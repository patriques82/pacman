package com.game.pacman.world.enteties.creatures.monster;

import com.game.pacman.world.World;
import com.game.pacman.world.enteties.creatures.Player;
import com.game.pacman.world.gfx.Animation;
import com.game.pacman.world.gfx.Assets;

public class YellowMonster extends Monster {

	public YellowMonster(int x, int y, World w, Player p) {
		super(x, y, w, p);
		animation = new Animation(500, Assets.YellowMonsterUp);
	}

	@Override
	public void setAnimation(Direction dir) {
		if(dir == Direction.DOWN)
			animation.setFrames(Assets.YellowMonsterDown);
		if(dir == Direction.UP)
			animation.setFrames(Assets.YellowMonsterUp);
		if(dir == Direction.RIGHT)
			animation.setFrames(Assets.YellowMonsterRight);
		if(dir == Direction.LEFT)
			animation.setFrames(Assets.YellowMonsterLeft);
		animation.tick();
	}

}
