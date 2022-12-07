package com.clement.utils.search.a_star;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class AStar {

	public static Pair<Integer, List<Node>> aStarSearch(Node start, Node goal) {
		// Set of nodes already evaluated
		Set<Node> closedSet = new HashSet<>();

		// Set of currently discovered nodes that are not evaluated yet.
		// Initially, only the start node is known.
		PriorityQueue<Node> openSet = new PriorityQueue<>();
		openSet.add(start);

		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually contain the
		// most efficient previous step.
		Map<Node, Node> cameFrom = new HashMap<>();

		// For each node, the cost of getting from the start node to that node.
		Map<Node, Integer> gScore = new HashMap<>();
		gScore.put(start, 0);

		// For each node, the total cost of getting from the start node to the goal
		// by passing by that node. That value is partly known, partly heuristic.
		start.withFScore(start.distanceTo(goal));

		while (!openSet.isEmpty()) {
			Node current = openSet.poll();

			if (current.equals(goal)) {
				return Pair.of(gScore.get(current), reconstructPath(cameFrom, current));
			}

			closedSet.add(current);

			for (Node neighbor : current.getNeighbors()) {
				if (closedSet.contains(neighbor)) {
					// Ignore the neighbor which is already evaluated.
					continue;
				}

				// The distance from start to a neighbor
				int tentativeGScore = gScore.get(current) + current.distanceTo(neighbor);

				if (tentativeGScore >= gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
					// This is not a better path.
					continue;
				}

				// This path is the best until now. Record it!
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentativeGScore);
				neighbor.withFScore(gScore.get(neighbor) + neighbor.distanceTo(goal));

				openSet.remove(neighbor);
				// Discover a new node
				openSet.add(neighbor);
			}
		}

		return Pair.of(-1, Collections.emptyList());
	}

	private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
		List<Node> path = new ArrayList<>();
		path.add(current);

		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			path.add(current);
		}

		Collections.reverse(path);
		return path;
	}
}