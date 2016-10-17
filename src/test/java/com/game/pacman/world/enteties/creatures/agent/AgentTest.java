package com.game.pacman.world.enteties.creatures.agent;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.game.pacman.world.enteties.creatures.agent.Agent;

public class AgentTest {

	Agent agent;
	int[][] tiles = {
		{0,0,0,0},
		{0,0,1,0}, // start in (0,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};
	
	@Before
	public void setUp() throws Exception {
		agent = null; //new Agent(new FollowingStrategy(tiles));
	}

	@Test
	public void test() {
		// step 1
		int startX = 0;
		int startY = 1;
		agent.computeDirection(startX, startY, 3, 4);
		assertFalse(agent.pressUp(startX, startY));
		assertFalse(agent.pressLeft(startY, startY));
		assertTrue(agent.pressRight(startY, startY));
		assertFalse(agent.pressDown(startY, startY));

		// step 2
		startX = 1;
		startY = 3;
		agent.computeDirection(startX, startY, 3, 4); // walk diagonal (north-east)
		assertTrue(agent.pressUp(startY, startY));
		assertFalse(agent.pressLeft(startY, startY));
		assertTrue(agent.pressRight(startY, startY));
		assertFalse(agent.pressDown(startY, startY));
	}

}
