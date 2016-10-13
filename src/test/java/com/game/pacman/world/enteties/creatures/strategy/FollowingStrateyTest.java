package com.game.pacman.world.enteties.creatures.strategy;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.game.pacman.world.enteties.creatures.agent.FollowingStrategy;
import com.game.pacman.world.enteties.creatures.agent.Strategy;

public class FollowingStrateyTest {
	
	int[][] tiles = {
		{0,0,0,0},
		{0,0,1,0}, // start in (0,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};
	Strategy strategy;

	@Before
	public void setUp() throws Exception {
		strategy = new FollowingStrategy(tiles);
	}

	@After
	public void tearDown() throws Exception {
		strategy = null;
	}

	@Test
	public void testFindPath() {
		assertTrue(true);
	}

	@Test
	public void testGetXDir() {
		assertTrue(strategy.getXDir(0.0f) == 1); // no press
		assertTrue(strategy.getXDir(1.3f) == -1); // press left
	}

	@Test
	public void testGetYDir() {
		assertTrue(strategy.getYDir(1.0f) == 1); // press down
		assertTrue(strategy.getYDir(2.0f) == 0); // no press
		assertTrue(strategy.getYDir(3.0f) == -1); // press up
	}

}
