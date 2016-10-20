package com.game.pacman.world.enteties.creatures.agent;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BreadCrumbsStrategyTest {

	int[][] tiles = {
		{0,0,0,0},
		{0,0,1,0}, // start in (1,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};
	BreadCrumbsStrategy strategy;

	@Before
	public void setUp() throws Exception {
		strategy = new BreadCrumbsStrategy(tiles);
	}

	@After
	public void tearDown() throws Exception {
		strategy = null;
	}

	@Test
	public final void testGetXDir() {
		strategy.findPath(0,0,3,4); // from 3,4
		strategy.findPath(0,0,3,3); // to 3,3
		strategy.findPath(0,0,3,2); // to 2,2
		assertThat(strategy.getXDir(2, 2), is(1)); // should move right at this position
		assertThat(strategy.getXDir(3, 1), is(0)); // should move to down, not right or left
	}

	@Test
	public final void testGetYDir() {
		strategy.findPath(0,0,3,4); // from 3,4
		strategy.findPath(0,0,3,3); // to 3,3
		assertThat(strategy.getYDir(3, 2), is(1)); // should move down at this position

		strategy.findPath(0,0,2,2); // to 2,2
		assertThat(strategy.getYDir(1, 2), is(0)); // should move to right, not up or down

		strategy.findPath(0,0,1,2); // to 1,2
		strategy.findPath(0,0,0,2); // to 0,2
		assertThat(strategy.getYDir(1, 3), is(-1)); // should move to right, not up or down
	}

	@Test
	public final void testGetXYDir() {
		strategy.findPath(0,4,1,3);
		assertThat(strategy.getXDir(0,4), is(1));
		assertThat(strategy.getYDir(0,4), is(0));

		strategy.findPath(1,4,1,2);
		assertThat(strategy.getXDir(1,4), is(0));
		assertThat(strategy.getYDir(1,4), is(-1));

		strategy.findPath(1,3,1,1);
		assertThat(strategy.getXDir(1,3), is(0));
		assertThat(strategy.getYDir(1,3), is(-1));

		strategy.findPath(1,2,1,0);
		assertThat(strategy.getXDir(1,2), is(0));
		assertThat(strategy.getYDir(1,2), is(-1));

	}

	@Test
	public final void testInvert() {
		int[][] matrix = {{1,0,0},{0,1,0},{0,0,1}};
		int[][] inverted = strategy.invert(matrix);
		int[] arr0 = {0,1,1};
		assertThat(inverted[0], is(arr0));
		int[] arr1 = {1,0,1};
		assertThat(inverted[1], is(arr1));
		int[] arr2 = {1,1,0};
		assertThat(inverted[2], is(arr2));
	}

}
