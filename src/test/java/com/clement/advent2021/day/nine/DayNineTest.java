package com.clement.advent2021.day.nine;

import static java.util.Comparator.reverseOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Streams;

class DayNineTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		int[][] smokeFlows = getInputsReader().lines()
				.map(String::chars)
				.map(characters -> characters.map(c -> c - '0').toArray())
				.toArray(int[][]::new);

		int sumOfLowestValues = 0;
		for (int x = 0; x < smokeFlows[0].length; x++) {
			for (int y = 0; y < smokeFlows.length; y++) {
				int currentValue = smokeFlows[y][x];
				boolean isSmallerThanUp = true;
				boolean isSmallerThanDown = true;
				boolean isSmallerThanLeft = true;
				boolean isSmallerThanRight = true;
				if (x > 0) {
					int leftValue = smokeFlows[y][x - 1];
					isSmallerThanLeft = currentValue < leftValue;
				}
				if (x < smokeFlows[0].length - 1) {
					int rightValue = smokeFlows[y][x + 1];
					isSmallerThanRight = currentValue < rightValue;
				}
				if (y > 0) {
					int upValue = smokeFlows[y - 1][x];
					isSmallerThanUp = currentValue < upValue;
				}
				if (y < smokeFlows.length - 1) {
					int downValue = smokeFlows[y + 1][x];
					isSmallerThanDown = currentValue < downValue;
				}

				if (isSmallerThanDown && isSmallerThanLeft && isSmallerThanUp && isSmallerThanRight) {
					sumOfLowestValues += currentValue + 1;
				}
			}
		}

		log.info("answer Part1: " + sumOfLowestValues);
	}

	@Test
	void part2() throws IOException {
		List<List<Pair<Integer, Integer>>> basinLines = getInputsReader().lines()
				.map(this::findBasins)
				.toList();

		List<List<List<Pair<Integer, Integer>>>> basins = new ArrayList<>();
		basinLines.get(0).forEach(basinLine -> {
			List<List<Pair<Integer, Integer>>> newBasin = new ArrayList<>();
			final List<Pair<Integer, Integer>> basinLineList = new ArrayList<>();
			basinLineList.add(basinLine);
			newBasin.add(basinLineList);
			basins.add(newBasin);
		});

		for (int i = 1; i < basinLines.size(); i++) {
			basins.forEach(basin -> basin.add(new ArrayList<>()));

			for (Pair<Integer, Integer> basinLine : basinLines.get(i)) {
				List<List<List<Pair<Integer, Integer>>>> connectedBasins = new ArrayList<>();
				for (List<List<Pair<Integer, Integer>>> basin : basins) {
					if (isBasinLineConnectedToBasin(basin, basinLine)) {
						connectedBasins.add(basin);
					}
				}

				if (connectedBasins.size() > 0) {
					if (connectedBasins.size() > 1) {
						List<List<Pair<Integer, Integer>>> mergedBasins = connectedBasins.stream().reduce(this::mergeBasins).get();
						mergedBasins.get(i).add(basinLine);
						connectedBasins.stream().forEach(basins::remove);
						basins.add(mergedBasins);
					} else {
						connectedBasins.get(0).get(i).add(basinLine);
					}
				} else {
					List<List<Pair<Integer, Integer>>> newBasin = new ArrayList<>();
					IntStream.range(0, i).forEach(index -> newBasin.add(new ArrayList<>()));
					List<Pair<Integer, Integer>> newBasinLines = new ArrayList<>();
					newBasinLines.add(basinLine);
					newBasin.add(newBasinLines);
					basins.add(newBasin);
				}
			}
		}
		int total = basins.stream().map(this::calculateBasinSize).sorted(reverseOrder()).limit(3).reduce(1, (size1, size2) -> size1 * size2);

		log.info("answer Part2: " + total);
	}

	private int calculateBasinSize(List<List<Pair<Integer, Integer>>> basin) {
		return basin.stream().flatMap(List::stream).mapToInt(basinLine -> basinLine.getRight() - basinLine.getLeft() + 1).sum();
	}

	private List<List<Pair<Integer, Integer>>> mergeBasins(List<List<Pair<Integer, Integer>>> basin1, List<List<Pair<Integer, Integer>>> basin2) {
		return Streams.zip(basin1.stream(), basin2.stream(), ListUtils::union).collect(Collectors.toCollection(ArrayList::new));
	}

	private boolean isBasinLineConnectedToBasin(List<List<Pair<Integer, Integer>>> basin, Pair<Integer, Integer> basinLine) {
		List<Pair<Integer, Integer>> aboveBasinLines = basin.get(basin.size() - 2);
		return aboveBasinLines.stream().anyMatch(aboveBasinLine -> aboveBasinLine.getLeft() <= basinLine.getRight() && aboveBasinLine.getRight() >= basinLine.getLeft());
	}

	private List<Pair<Integer, Integer>> findBasins(String smokeFlowLine) {
		List<Pair<Integer, Integer>> basinsLine = new ArrayList<>();
		int previousNineIndex = -1;
		int nineIndex = smokeFlowLine.indexOf('9');
		while (nineIndex > -1) {
			if (nineIndex > previousNineIndex + 1) {
				basinsLine.add(Pair.of(previousNineIndex + 1, nineIndex - 1));
			}
			previousNineIndex = nineIndex;
			nineIndex = smokeFlowLine.indexOf('9', previousNineIndex + 1);
		}
		if (previousNineIndex < smokeFlowLine.length() - 1) {
			basinsLine.add(Pair.of(previousNineIndex + 1, smokeFlowLine.length() - 1));
		}
		return basinsLine;
	}
}
