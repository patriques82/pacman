package com.game.pacman.world.enteties.creatures.agent.astar;

import static org.junit.Assert.*;
import org.junit.Test;

public class AstarTest {

	int[][] matrix = {
		{0,0,0,0},
		{0,0,1,0}, // start in (0,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};

	@Test
	public void test() {
		int[] actualPath = Astar.calculatePath(matrix, 0.0f, 1.0f, 3.0f, 4.0f);
		int[] expectedPath = {9,10,11,12,16,18};
		assertArrayEquals(actualPath, expectedPath);
	}

}
