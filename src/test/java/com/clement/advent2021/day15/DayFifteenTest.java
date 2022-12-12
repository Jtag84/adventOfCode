package com.clement.advent2021.day15;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.clement.utils.inputs.Array2D;
import com.clement.utils.inputs.DynamicArray2D;
import com.clement.utils.search.a_star.AStar;
import com.clement.utils.search.a_star.Array2dNode;
import com.clement.utils.search.a_star.CostFunctions;
import com.clement.utils.search.a_star.HeuristicFunctions;
import com.clement.utils.search.a_star.NeighborsCalculators;
import com.clement.utils.search.a_star.Node;

class DayFifteenTest extends SolutionBase {

	@Test
	void part1WithGenericAStar() {
		Array2D map = getMap();

		Node start = getNode(0, 0, map);
		int maxX = map.getMaxX();
		int maxY = map.getMaxY();
		Node goal = getNode(maxX, maxY, map);
		Pair<Integer, List<Node>> costPathPair = AStar.aStarSearch(start, goal);

		log.info("answer Part1: " + costPathPair);
		Assertions.assertEquals(415, costPathPair.getLeft());
	}

	@Test
	void part2GenericAStar() {
		Array2D map = getMap();
		DynamicArray2D dynamicMap = DynamicArray2D.from(map);

		Node start = getNode(0, 0, dynamicMap);
		int maxX = dynamicMap.getMaxX();
		int maxY = dynamicMap.getMaxY();
		Node goal = getNode(maxX, maxY, dynamicMap);
		Pair<Integer, List<Node>> costPathPair = AStar.aStarSearch(start, goal);

		log.info("answer Part2: " + costPathPair);
		Assertions.assertEquals(2864, costPathPair.getLeft());
	}

	@NotNull
	private Array2D getMap() {
		return Array2D.parseInputs(getInputsReader().lines(), c -> c - '0');
	}

	@NotNull
	private static Array2dNode getNode(int x, int y, Array2D map) {
		return new Array2dNode(x, y, map, NeighborsCalculators::simpleArray2DNeighborsCalculator, HeuristicFunctions::manhattanDistanceHeuristic, CostFunctions::toNodeValueCost);
	}
}