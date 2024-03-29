package com.clement.advent2022.day12;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.clement.utils.inputs.Array2D;
import com.clement.utils.search.a_star.AStar;
import com.clement.utils.search.a_star.Array2dNode;
import com.clement.utils.search.a_star.CostFunctions;
import com.clement.utils.search.a_star.HeuristicFunctions;
import com.clement.utils.search.a_star.NeighborsCalculators;
import com.clement.utils.search.a_star.Node;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		Pair<Integer, List<Node>> costPathToBestSignalPair = findPathToBestSignal(getInputsExampleReader());

		log.info("answer Part1 Example: " + costPathToBestSignalPair);
		Assertions.assertEquals(31, costPathToBestSignalPair.getLeft());
	}

	@Test
	void part1() {
		Pair<Integer, List<Node>> costPathToBestSignalPair = findPathToBestSignal(getInputsReader());

		log.info("answer Part1: " + costPathToBestSignalPair);
		Assertions.assertEquals(534, costPathToBestSignalPair.getLeft());
	}

	@Test
	void part2Example() {
		Pair<Integer, List<Node>> pathFromBestElevationAToBestSignalPair = findBestPathFromElevationAToBestSignal(getInputsExampleReader());

		log.info("answer Part2 Example: " + pathFromBestElevationAToBestSignalPair);
		Assertions.assertEquals(29, pathFromBestElevationAToBestSignalPair.getLeft());
	}

	@Test
	void part2() {
		Pair<Integer, List<Node>> pathFromBestElevationAToBestSignalPair = findBestPathFromElevationAToBestSignal(getInputsReader());

		log.info("answer Part2: " + pathFromBestElevationAToBestSignalPair);
		Assertions.assertEquals(525, pathFromBestElevationAToBestSignalPair.getLeft());
	}

	private Pair<Integer, List<Node>> findPathToBestSignal(BufferedReader bufferedReader) {
		Array2D heightmap = Array2D.parseInputs(bufferedReader.lines(), c -> c);

		Array2dNode start = findNodeByValue(heightmap, 'S');
		Array2dNode goal = findNodeByValue(heightmap, 'E');

		heightmap.set(start.getXYcoordinates(), 'a');
		heightmap.set(goal.getXYcoordinates(), 'z');

		return AStar.aStarSearch(start, goal);
	}

	private Pair<Integer, List<Node>> findBestPathFromElevationAToBestSignal(BufferedReader bufferedReader) {
		Array2D heightmap = Array2D.parseInputs(bufferedReader.lines(), c -> c);

		Array2dNode start = findNodeByValue(heightmap, 'S');
		Array2dNode goal = findNodeByValue(heightmap, 'E');

		heightmap.set(start.getXYcoordinates(), 'a');
		heightmap.set(goal.getXYcoordinates(), 'z');

		return heightmap.findCoordinatesByValueStream('a')
				.map(coordinates -> buildHeightmapNodePart2(coordinates, heightmap))
				.map(node -> AStar.aStarSearch(node, goal))
				.filter(path -> path.getLeft() > 0)
				.min(Comparator.comparing(Pair::getLeft))
				.orElseThrow();
	}

	@NotNull
	private Array2dNode findNodeByValue(Array2D heightmap, char toFind) {
		Pair<Integer, Integer> coordinates = heightmap.findFirstCoordinatesByValue(toFind).orElseThrow();
		return buildHeightmapNode(coordinates, heightmap);
	}

	private Array2dNode buildHeightmapNodePart2(Pair<Integer, Integer> coordinates, Array2D map) {
		return new Array2dNode(coordinates.getLeft(), coordinates.getRight(), map, this::calculateNeighborsPart2, HeuristicFunctions::manhattanDistanceHeuristic, CostFunctions::simpleCost);
	}

	private Array2dNode buildHeightmapNode(Pair<Integer, Integer> coordinates, Array2D map) {
		return new Array2dNode(coordinates.getLeft(), coordinates.getRight(), map, this::calculateNeighbors, HeuristicFunctions::manhattanDistanceHeuristic, CostFunctions::simpleCost);
	}

	private Set<Array2dNode> calculateNeighbors(Array2dNode node, Array2D map) {
		return NeighborsCalculators.simpleArray2DNeighborsCalculator(node, map).stream()
				.filter(neighbor -> neighbor.getValue() - node.getValue() <= 1)
				.collect(Collectors.toSet());
	}

	private Set<Array2dNode> calculateNeighborsPart2(Array2dNode node, Array2D map) {
		return calculateNeighbors(node, map).stream()
				.filter(neighbor -> neighbor.getValue() != 'a')
				.collect(Collectors.toSet());
	}
}