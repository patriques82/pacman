package com.game.pacman.world.enteties.creatures.agent.pathfinder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.game.pacman.world.enteties.creatures.agent.pathfinder.Astar;
import com.game.pacman.world.enteties.creatures.agent.pathfinder.Node;

import static org.hamcrest.CoreMatchers.*;
			
public class AstarTest {

	final int[][] matrix = {
		{0,0,0,0},
		{0,0,1,0}, // start in (1,1)
		{0,0,0,0},
		{1,0,1,0},
		{0,0,1,0} // goal in (3,4)
	};
	Astar astar; 

	@Before
	public void setUp() throws Exception {
		astar = new Astar(matrix);
	}

	@After
	public void tearDown() throws Exception {
		astar = null;
	}

	@Test
	public void testCalculatePath_1() {
		// (1,1) -> (3,4)
		List<Integer> actualPath = astar.calculatePath(1, 1, 3, 4);
		List<Integer> expectedPath = Arrays.asList(6, 10, 11, 12, 16, 20);
		assertThat(actualPath, is(expectedPath));
	}

	public void testCalculatePath_2() {
		// (0,4) -> (0,2)
		List<Integer> actualPath = astar.calculatePath(0, 4, 0, 2);
		List<Integer> expectedPath = Arrays.asList(17, 18, 14, 10, 9);
		assertThat(actualPath, is(expectedPath));
	}

	public void testCalculatePath_3() {
		// (1,0) -> (1,4)
		List<Integer> actualPath = astar.calculatePath(1, 0, 1, 4);
		List<Integer> expectedPath = Arrays.asList(2, 6, 10, 14, 18);
		assertThat(actualPath, is(expectedPath));
	}

	public void testCalculatePath_4() {
		// (0,2) -> (3,2)
		List<Integer> actualPath = astar.calculatePath(0, 2, 3, 2);
		List<Integer> expectedPath = Arrays.asList(9, 10, 11, 12);
		assertThat(actualPath, is(expectedPath));
	}

	@Test
	public void testRemoveCheapestNode() {
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(new Node(0,0));
		list.add(new Node(0,0));
		list.add(new Node(0,0));
		list.add(new Node(0,0));
		list.get(0).setFCost(1);
		list.get(1).setFCost(3);
		list.get(2).setFCost(-2);
		list.get(3).setFCost(2);
		Node cheapest = astar.removeCheapestNode(list);
		assertEquals(cheapest.getFCost(), -2);
	}

	@Test
	public void testCalculateDistanceAndId() {
		astar.calculateDistanceAndId(4, 3);
		assertEquals(astar.get(0,0).getDistance(), 7);
		assertEquals(astar.get(0,0).getId(), 1);
		assertEquals(astar.get(1,1).getDistance(), 5);
		assertEquals(astar.get(1,1).getId(), 6);
		assertEquals(astar.get(2,1).getDistance(), 4);
		assertEquals(astar.get(2,1).getId(), 10);
		assertEquals(astar.get(2,2).getDistance(), 3);
		assertEquals(astar.get(2,2).getId(), 11);
		assertEquals(astar.get(2,3).getDistance(), 2);
		assertEquals(astar.get(2,3).getId(), 12);
		assertEquals(astar.get(3,3).getDistance(), 1);
		assertEquals(astar.get(3,3).getId(), 16);
		assertEquals(astar.get(4,3).getDistance(), 0);
		assertEquals(astar.get(4,3).getId(), 20);
	}


	@Test
	public void testGetNeighbors() {
		Node node = astar.get(2, 2);
		assertEquals(node.getX(),2);
		assertEquals(node.getY(),2);
		Set<Node> neighbors = astar.getNeighbors(node);
		ArrayList<Node> list = new ArrayList<>();
		list.add(astar.get(2, 1));
		list.add(astar.get(2, 3));
		assertEquals(neighbors.size(), 2);
		assertTrue(neighbors.containsAll(list));
	}

	@Test
	public void testSetNodeToParent() {
		ArrayList<Node> children = new ArrayList<Node>();
		children.add(new Node(0,0));
		children.add(new Node(0,0));
		children.add(new Node(0,0));
		children.add(new Node(0,0));
		Node parent = new Node(4,5);
		for(Node n : children) {
			assertEquals(n.getParent(), null);
		}
		astar.setNodeToParent(parent, children);
		for(Node n : children) {
			assertThat(n.getParent(), is(parent));
		}
	}

	@Test
	public void testCalculateMovementCosts() {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		neighbors.add(new Node(0,0));
		neighbors.add(new Node(0,0));
		neighbors.add(new Node(0,0));
		neighbors.add(new Node(0,0));
		Node current = new Node(4,5);
		current.setMovementCost(5);
		for(Node n : neighbors) {
			assertThat(n.getMovementCost(), is(0));
		}
		astar.calculateMovementCosts(current.getMovementCost(), neighbors);
		for(Node n : neighbors) {
			assertThat(n.getMovementCost(), is(6));
		}
	}

	@Test
	public void testGetPath() {
		Node node_1 = new Node(1,1);
		node_1.setId(6);
		Node node_2 = new Node(2,1);
		node_2.setId(10);
		node_2.setParent(node_1);
		Node node_3 = new Node(2,2);
		node_3.setId(11);
		node_3.setParent(node_2);
		Node node_4 = new Node(2,3);
		node_4.setId(12);
		node_4.setParent(node_3);
		Node node_5 = new Node(3,3);
		node_5.setId(16);
		node_5.setParent(node_4);
		Node node_6 = new Node(4,3);
		node_6.setId(20);
		node_6.setParent(node_5);

		List<Integer> actualPath = astar.getPath(node_6);
		List<Integer> expectedPath = Arrays.asList(6, 10, 11, 12, 16, 20);
		assertThat(actualPath, is(expectedPath));
	}

}
