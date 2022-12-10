package com.clement.advent2022.day08;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.clement.utils.inputs.Array2D;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		Set<Pair<Integer, Integer>> visibleTrees = getVisibleTrees(getInputsExampleReader());

		log.info("answer Part1 Example: " + visibleTrees.size());
		Assertions.assertEquals(21, visibleTrees.size());
	}

	@Test
	void part1() {
		Set<Pair<Integer, Integer>> visibleTrees = getVisibleTrees(getInputsReader());

		log.info("answer Part1: " + visibleTrees.size());
	}

	@Test
	void part2Example() {
		Map<Pair<Integer, Integer>, Integer> scenicScores = getViewFromTrees(getInputsExampleReader());

		int maxScenicScore = scenicScores.values().stream().max(Comparator.comparing(Function.identity())).orElseThrow(IllegalStateException::new);

		log.info("answer Part2Example: maxScenicScore=" + maxScenicScore);
		Assertions.assertEquals(8, maxScenicScore);
	}

	@Test
	void part2() {
		Map<Pair<Integer, Integer>, Integer> scenicScores = getViewFromTrees(getInputsReader());

		int maxScenicScore = scenicScores.values().stream().max(Comparator.comparing(Function.identity())).orElseThrow(IllegalStateException::new);

		log.info("answer Part2: maxScenicScore=" + maxScenicScore);
	}

	@NotNull
	private Set<Pair<Integer, Integer>> getVisibleTrees(BufferedReader bufferedReader) {
		Array2D forest = Array2D.parseInputs(bufferedReader.lines(), c -> c - '0');

		return getVisibleTrees(forest);
	}

	private Set<Pair<Integer, Integer>> getVisibleTrees(Array2D forest) {

		Set<Pair<Integer, Integer>> visibleTrees = new HashSet<>();

		addVisibleTreesFromForestIterator(visibleTrees, forest.getCoordinateValueRowFromLeftIterators());
		addVisibleTreesFromForestIterator(visibleTrees, forest.getCoordinateValueRowFromRightIterators());
		addVisibleTreesFromForestIterator(visibleTrees, forest.getCoordinateValueColumnFromBottomIterators());
		addVisibleTreesFromForestIterator(visibleTrees, forest.getCoordinateValueColumnFromTopIterators());

		return visibleTrees;
	}

	private static void addVisibleTreesFromForestIterator(Set<Pair<Integer, Integer>> visibleTrees, Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> vectorForestIterator) {
		for (Iterable<Pair<Pair<Integer, Integer>, Integer>> treeIterator : vectorForestIterator) {
			int currentHighest = -1;
			for (Pair<Pair<Integer, Integer>, Integer> tree : treeIterator) {
				if (tree.getRight() > currentHighest) {
					visibleTrees.add(tree.getLeft());
					currentHighest = tree.getRight();
					if (currentHighest == 9) {
						break;
					}
				}
			}
		}
	}

	private Map<Pair<Integer, Integer>, Integer> getViewFromTrees(BufferedReader bufferedReader) {
		Array2D forest = Array2D.parseInputs(bufferedReader.lines(), c -> c - '0');

		return getViewFromTrees(forest);
	}

	private Map<Pair<Integer, Integer>, Integer> getViewFromTrees(Array2D forest) {
		Map<Pair<Integer, Integer>, Integer> scenicViewScoresByTree = new HashMap<>();

		Map<Pair<Integer, Integer>, Integer> treeViewsFromLeft = getViewFromTreesFromForestIterator(forest.getCoordinateValueRowFromLeftIterators());
		Map<Pair<Integer, Integer>, Integer> treeViewsFromRight = getViewFromTreesFromForestIterator(forest.getCoordinateValueRowFromRightIterators());
		Map<Pair<Integer, Integer>, Integer> treeViewsFromTop = getViewFromTreesFromForestIterator(forest.getCoordinateValueColumnFromTopIterators());
		Map<Pair<Integer, Integer>, Integer> treeViewsFromBottom = getViewFromTreesFromForestIterator(forest.getCoordinateValueColumnFromBottomIterators());

		calculateScenicScore(scenicViewScoresByTree, treeViewsFromLeft);
		calculateScenicScore(scenicViewScoresByTree, treeViewsFromRight);
		calculateScenicScore(scenicViewScoresByTree, treeViewsFromTop);
		calculateScenicScore(scenicViewScoresByTree, treeViewsFromBottom);

		return scenicViewScoresByTree;
	}

	private static void calculateScenicScore(Map<Pair<Integer, Integer>, Integer> scenicViewScoresByTree, Map<Pair<Integer, Integer>, Integer> treeViews) {
		treeViews.forEach((tree, viewNumber) -> scenicViewScoresByTree.merge(tree, viewNumber, (currentValue, newValue) -> currentValue * newValue));
	}

	private Map<Pair<Integer, Integer>, Integer> getViewFromTreesFromForestIterator(Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> vectorForestIterator) {
		Map<Pair<Integer, Integer>, Integer> viewFromTrees = new HashMap<>();

		for (Iterable<Pair<Pair<Integer, Integer>, Integer>> treeIterator : vectorForestIterator) {
			Set<Pair<Pair<Integer, Integer>, Integer>> treesWithAView = new HashSet<>();

			for (Pair<Pair<Integer, Integer>, Integer> tree : treeIterator) {
				treesWithAView.forEach(treeWithAView -> viewFromTrees.compute(treeWithAView.getLeft(), (treeCoordinate, currentView) -> currentView + 1));
				treesWithAView = treesWithAView.stream().filter(treeWithAView -> treeWithAView.getRight() > tree.getRight()).collect(Collectors.toSet());
				treesWithAView.add(tree);
				viewFromTrees.put(tree.getLeft(), 0);
			}
		}

		return viewFromTrees;
	}

}