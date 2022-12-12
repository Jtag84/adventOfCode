package com.clement.utils.search.a_star;

import java.util.HashSet;
import java.util.Set;

import com.clement.utils.inputs.Array2D;

public class NeighborsCalculators {
	public static Set<Array2dNode> defaultCalculateNeighbors(Array2dNode node, Array2D map) {
		int maxXmap = map.getMaxX();
		int maxYmap = map.getMaxY();

		Set<Array2dNode> neighbors = new HashSet<>();

		if (node.getX() > 0) {
			neighbors.add(Array2dNode.get(node.getX() - 1, node.getY(), node));
		}
		if (node.getX() < maxXmap) {
			neighbors.add(Array2dNode.get(node.getX() + 1, node.getY(), node));
		}
		if (node.getY() > 0) {
			neighbors.add(Array2dNode.get(node.getX(), node.getY() - 1, node));
		}
		if (node.getY() < maxYmap) {
			neighbors.add(Array2dNode.get(node.getX(), node.getY() + 1, node));
		}

		return neighbors;
	}
}
