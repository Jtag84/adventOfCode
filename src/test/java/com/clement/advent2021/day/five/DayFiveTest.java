package com.clement.advent2021.day.five;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.AdventUtils;
import com.clement.utils.SolutionBase;

class DayFiveTest extends SolutionBase {

	private Stream<List<Pair<Integer, Integer>>> getLineCoordinates() throws IOException {
		return getInputsReader().lines()
				.map(line -> line.replaceAll(" -> ", ","))
				.map(line -> line.split(","))
				.map(this::toPairCoordinate);
	}

	private List<Pair<Integer, Integer>> toPairCoordinate(String[] coordinateString) {
		return asList(
					Pair.of(Integer.parseInt(coordinateString[0]), Integer.parseInt(coordinateString[1])),
					Pair.of(Integer.parseInt(coordinateString[2]), Integer.parseInt(coordinateString[3]))
				);
	}

	@Test
	void part1() throws IOException {
		long numberOfOverlaps = getLineCoordinates()
				.filter(coordinates ->
						coordinates.get(0).getLeft().equals(coordinates.get(1).getLeft())
								|| coordinates.get(0).getRight().equals(coordinates.get(1).getRight()))
				.map(AdventUtils::calculateAllPointsOfLine)
				.flatMap(List::stream)
				.collect(groupingBy(identity()))
				.values()
				.stream()
				.filter(list -> list.size() > 1)
				.count();
		log.info("answer Part1: " + numberOfOverlaps);
	}

	@Test
	void part2() throws IOException {
		long numberOfOverlaps = getLineCoordinates()
				.map(AdventUtils::calculateAllPointsOfLine)
				.flatMap(List::stream)
				.collect(groupingBy(identity()))
				.values()
				.stream()
				.filter(list -> list.size() > 1)
				.count();

		log.info("answer Part2: " + numberOfOverlaps);
	}

}
