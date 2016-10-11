package com.game.pacman.world.enteties.creatures.strategy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ContextTest {

	Context ctx;
	
	@Before
	public void setUp() throws Exception {
		ctx = new Context(new FollowingStrategy());
	}

	@Test
	public void test() {
		assertFalse(ctx.pressDown());
	}

}
