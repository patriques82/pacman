package com.game.pacman.world.enteties.creatures.strategy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.game.pacman.world.enteties.creatures.agent.Agent;
import com.game.pacman.world.enteties.creatures.agent.FollowingStrategy;


public class AgentTest {

	Agent agent;
	int[][] tiles = {
		{1,0,1,0},
		{0,0,1,0},
		{0,0,0,1},
		{1,1,0,0}
	};
	
	@Before
	public void setUp() throws Exception {
		agent = new Agent(tiles, new FollowingStrategy());
	}

	@Test
	public void test() {
		assertFalse(agent.pressDown());
	}

}
