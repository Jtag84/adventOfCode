package com.clement.utils.search.a_star;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.clement.utils.inputs.Array2D;

public class Array2dNode extends Node {

	private final Array2D map;

	private final int x;
	private final int y;

	private Set<Node> neighbors;

	public Array2dNode(int x, int y, Array2D map) {
		this.map = map;
		this.x = x;
		this.y = y;
	}

	@Override
	public Collection<Node> getNeighbors() {
		Optional.ofNullable(this.neighbors).ifPresentOrElse(a -> {
		}, this::calculateNeighbors);
		return this.neighbors;
	}

	private void calculateNeighbors() {
		int maxXmap = map.getMaxX();
		int maxYmap = map.getMaxY();

		Set<Node> neighbors = new HashSet<>();

		if (x > 0) {
			neighbors.add(getNodeNeighbor(x - 1, y));
		}
		if (x < maxXmap) {
			neighbors.add(getNodeNeighbor(x + 1, y));
		}
		if (y > 0) {
			neighbors.add(getNodeNeighbor(x, y - 1));
		}
		if (y < maxYmap) {
			neighbors.add(getNodeNeighbor(x, y + 1));
		}

		this.neighbors = neighbors;
	}

	private Array2dNode getNodeNeighbor(int x, int y) {
		return new Array2dNode(x, y, map);
	}

	@Override
	public int distanceTo(Node other) {
		Array2dNode other2DArray = (Array2dNode) other;
		if (getNeighbors().contains(other)) {
			return getCost(other2DArray);
		} else { // use heuristic
			return other2DArray.x - this.x + other2DArray.y - this.y;
		}
	}

	private int getCost(Array2dNode node) {
		return map.get(node.x, node.y);
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

}
