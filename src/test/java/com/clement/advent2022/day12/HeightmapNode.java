package com.clement.advent2022.day12;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.clement.utils.inputs.Array2D;
import com.clement.utils.search.a_star.Array2dNode;

public class HeightmapNode {
	public static Array2dNode buildHeightmapNodePart2(Pair<Integer, Integer> coordinates, Array2D map) {
		return new Array2dNode(coordinates.getLeft(), coordinates.getRight(), map, HeightmapNode::calculateNeighborsPart2, Array2dNode::defaultHeuristicDistance, Array2dNode::defaultHeuristicDistance);
	}

	public static Array2dNode buildHeightmapNode(Pair<Integer, Integer> coordinates, Array2D map) {
		return new Array2dNode(coordinates.getLeft(), coordinates.getRight(), map, HeightmapNode::calculateNeighbors, Array2dNode::defaultHeuristicDistance, Array2dNode::defaultHeuristicDistance);
	}

	protected static Set<Array2dNode> calculateNeighbors(Array2dNode node) {
		return Array2dNode.defaultCalculateNeighbors(node).stream()
				.filter(neighbor -> neighbor.getValue() - node.getValue() <= 1)
				.collect(Collectors.toSet());
	}

	protected static Set<Array2dNode> calculateNeighborsPart2(Array2dNode node) {
		return calculateNeighbors(node).stream()
				.filter(neighbor -> neighbor.getValue() != 'a')
				.collect(Collectors.toSet());
	}
}
