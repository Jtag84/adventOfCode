package com.clement.utils.search.a_star;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;

import com.clement.utils.inputs.Array2D;

public class Array2dNode extends Node {

	private final Array2D map;

	private final int x;
	private final int y;

	private Set<Array2dNode> neighbors;

	private final Function<Array2dNode, Set<Array2dNode>> calculateNeighbors;
	private final BiFunction<Array2dNode, Array2dNode, Integer> heuristicDistance;
	private final BiFunction<Array2dNode, Array2dNode, Integer> cost;

	public Array2dNode(int x, int y, Array2D map,
					   Function<Array2dNode, Set<Array2dNode>> calculateNeighbors,
					   BiFunction<Array2dNode, Array2dNode, Integer> heuristicDistance,
					   BiFunction<Array2dNode, Array2dNode, Integer> cost) {
		Assertions.assertNotNull(map);
		Assertions.assertTrue(x >= 0);
		Assertions.assertTrue(y >= 0);
		Assertions.assertTrue(x <= map.getMaxX());
		Assertions.assertTrue(y <= map.getMaxY());
		this.map = map;
		this.x = x;
		this.y = y;

		this.calculateNeighbors = calculateNeighbors;
		this.heuristicDistance = heuristicDistance;
		this.cost = cost;
	}

	public Array2dNode(int x, int y, Array2D map) {
		this(x, y, map,
				Array2dNode::defaultCalculateNeighbors,
				Array2dNode::defaultHeuristicDistance,
				Array2dNode::defaultCost);
	}

	public static Array2dNode get(int x, int y, Array2dNode withNode) {
		return new Array2dNode(x, y, withNode.map, withNode.calculateNeighbors, withNode.heuristicDistance, withNode.cost);
	}

	@Override
	public Collection<? extends Node> getNeighbors() {
		Optional.ofNullable(this.neighbors)
				.ifPresentOrElse(
						a -> {
						},
						() -> this.neighbors = this.calculateNeighbors.apply(this));

		return this.neighbors;
	}

	public static Set<Array2dNode> defaultCalculateNeighbors(Array2dNode node) {
		int maxXmap = node.map.getMaxX();
		int maxYmap = node.map.getMaxY();

		Set<Array2dNode> neighbors = new HashSet<>();

		if (node.x > 0) {
			neighbors.add(get(node.x - 1, node.y, node));
		}
		if (node.x < maxXmap) {
			neighbors.add(get(node.x + 1, node.y, node));
		}
		if (node.y > 0) {
			neighbors.add(get(node.x, node.y - 1, node));
		}
		if (node.y < maxYmap) {
			neighbors.add(get(node.x, node.y + 1, node));
		}

		return neighbors;
	}

	@Override
	public int distanceTo(Node other) {
		Array2dNode other2DArray = (Array2dNode) other;
		if (getNeighbors().contains(other)) {
			return this.cost.apply(this, other2DArray);
		} else { // use heuristic
			return this.heuristicDistance.apply(this, other2DArray);
		}
	}

	public static int defaultHeuristicDistance(Array2dNode from, Array2dNode to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	public static int defaultCost(Array2dNode from, Array2dNode to) {
		return to.getValue();
	}

	public int getValue() {
		return map.get(this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Array2dNode that = (Array2dNode) o;
		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public Pair<Integer, Integer> getXYcoordinates() {
		return Pair.of(this.x, this.y);
	}

}
