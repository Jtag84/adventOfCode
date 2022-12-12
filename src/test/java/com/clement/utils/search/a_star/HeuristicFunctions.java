package com.clement.utils.search.a_star;

public class HeuristicFunctions {
	public static int manhattanDistanceHeuristic(Array2dNode from, Array2dNode to) {
		return manhattanDistanceHeuristic(1, from, to);
	}

	public static int manhattanDistanceHeuristic(int minimumCost, Array2dNode from, Array2dNode to) {
		return minimumCost * (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()));
	}
}
