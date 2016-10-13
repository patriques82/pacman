package com.game.pacman.world.enteties.creatures.agent;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.game.pacman.world.enteties.creatures.agent.Agent;
import com.game.pacman.world.enteties.creatures.agent.FollowingStrategy;

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
		agent = new Agent(new FollowingStrategy(tiles));
	}

	@Test
	public void test() {
		// step 1
		float startX = 0.0f;
		float startY = 1.0f;
		agent.computeDirection(startX, startY, 3.0f, 4.0f);
		assertFalse(agent.pressUp(startX, startY));
		assertFalse(agent.pressLeft(startY, startY));
		assertTrue(agent.pressRight(startY, startY));
		assertFalse(agent.pressDown(startY, startY));

		// step 2
		startX = 1.0f;
		startY = 3.3f;
		agent.computeDirection(startX, startY, 3.0f, 4.0f); // walk diagonal (north-east)
		assertTrue(agent.pressUp(startY, startY));
		assertFalse(agent.pressLeft(startY, startY));
		assertTrue(agent.pressRight(startY, startY));
		assertFalse(agent.pressDown(startY, startY));

	}

}
