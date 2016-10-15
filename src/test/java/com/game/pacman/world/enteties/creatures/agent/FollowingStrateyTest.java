package com.game.pacman.world.enteties.creatures.agent;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import org.mockito.Mockito;

import com.game.pacman.world.enteties.creatures.agent.FollowingStrategy;
import com.game.pacman.world.enteties.creatures.agent.astar.Astar;

public class FollowingStrateyTest {
	
	int[][] tiles = {
		{0,0,0,0},
		{0,0,1,0}, // start in (1,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};
	FollowingStrategy strategy;
	private final static float STARTX = 1.0f;
	private final static float STARTY = 1.0f;
	private final static float DESTX = 3.0f;
	private final static float DESTY = 4.0f;

	@Before
	public void setUp() throws Exception {
		Astar mockAstar = Mockito.mock(Astar.class);
		Stack<Integer> path = new Stack<>();
		path.add(6);
		path.add(10);
		path.add(11);
		path.add(12);
		path.add(16);
		path.add(20);
		Mockito.when(mockAstar.calculatePath(STARTX, STARTY, DESTX, DESTY)).thenReturn(path);
		Mockito.when(mockAstar.getWidth()).thenReturn(4);
		strategy = new FollowingStrategy(mockAstar);
	}

	@After
	public void tearDown() throws Exception {
		strategy = null;
	}

	@Test
	public void testFindPath() {
		strategy.findPath(STARTX, STARTY, DESTX, DESTY);
		// no movement since monster is in first cell already (1,1)
		assertThat(strategy.getXDir(STARTX), is(0));
		assertThat(strategy.getYDir(STARTY), is(0));

		strategy.findPath(STARTX, STARTY, DESTX, DESTY);
		// move down since next step is now below monster (1,2)
		assertThat(strategy.getXDir(STARTX), is(0));
		assertThat(strategy.getYDir(STARTY), is(1));

		strategy.findPath(STARTX, STARTY+1.0f, DESTX, DESTY);
		// move right (2,2)
		assertThat(strategy.getXDir(STARTX), is(1));
		assertThat(strategy.getYDir(STARTY+1.0f), is(0));

		strategy.findPath(STARTX+1.0f, STARTY+1.0f, DESTX, DESTY);
		// move right (3,2)
		assertThat(strategy.getXDir(STARTX+1.0f), is(1));
		assertThat(strategy.getYDir(STARTY+1.0f), is(0));

		strategy.findPath(STARTX+2.0f, STARTY+1.0f, DESTX, DESTY);
		// move down (3,3)
		assertThat(strategy.getXDir(STARTX+2.0f), is(0));
		assertThat(strategy.getYDir(STARTY+1.0f), is(1));

		strategy.findPath(STARTX+2.0f, STARTY+2.0f, DESTX, DESTY);
		// move down (3,4)
		assertThat(strategy.getXDir(STARTX+2.0f), is(0));
		assertThat(strategy.getYDir(STARTY+2.0f), is(1));
	}

	@Test
	public void testGetXDir() {
		assertThat(strategy.getXDir(1.0f), is(-1)); // press left
		assertThat(strategy.getXDir(-1.3f), is(1)); // press right
		assertThat(strategy.getXDir(0.3f), is(0)); // no press
	}
	
	@Test
	public void testGetXCoord() {
		assertThat(strategy.getXCoord(12), is(3));
	}

	@Test
	public void testGetYDir() {
		assertThat(strategy.getYDir(1.0f), is(-1)); // press left
		assertThat(strategy.getYDir(-1.3f), is(1)); // press right
		assertThat(strategy.getYDir(0.3f), is(0)); // no press
	}

	@Test
	public void testGetYCoord() {
		assertThat(strategy.getYCoord(12), is(2));
	}
}
