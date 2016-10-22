package com.game.pacman.world.enteties.creatures.agent;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RandomStrategyTest {

	int[][] tiles = {
		{1,1,1,1},
		{1,1,0,1}, // start in (1,1)
		{1,1,1,1},
		{0,1,0,1},
		{1,1,0,1} // goal in (3,4)
	};
	RandomStrategy strategy;

	@Before
	public void setUp() throws Exception {
		strategy = new RandomStrategy(tiles);
	}

	@After
	public void tearDown() throws Exception {
		strategy = null;
	}

	@Test
	public void testComputeXDir() {
		assertThat(strategy.computeXDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeXDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeXDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeXDir(3,4), is(0));
		assertThat(strategy.computeXDir(3,4), is(0));
		assertThat(strategy.computeXDir(3,4), is(0));
		assertThat(strategy.computeXDir(3,2), anyOf(is(0), is(-1)));
		assertThat(strategy.computeXDir(3,2), anyOf(is(0), is(-1)));
		assertThat(strategy.computeXDir(3,2), anyOf(is(0), is(-1)));
	}

	@Test
	public void testComputeYDir() {
		assertThat(strategy.computeYDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeYDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeYDir(0,0), anyOf(is(0), is(1)));
		assertThat(strategy.computeYDir(2,2), is(0));
		assertThat(strategy.computeYDir(2,2), is(0));
		assertThat(strategy.computeYDir(2,2), is(0));
		assertThat(strategy.computeYDir(3,4), anyOf(is(0), is(-1)));
		assertThat(strategy.computeYDir(3,4), anyOf(is(0), is(-1)));
		assertThat(strategy.computeYDir(3,4), anyOf(is(0), is(-1)));
	}

}
