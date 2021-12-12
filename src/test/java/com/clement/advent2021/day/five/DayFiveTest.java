package com.clement.advent2021.day.five;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

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
				.map(this::cacluateAllPoints)
				.flatMap(List::stream)
				.collect(groupingBy(identity()))
				.values()
				.stream()
				.filter(list -> list.size() > 1)
				.count();
		log.info("answer Part1: " + numberOfOverlaps);
	}

	private List<Pair<Integer, Integer>> cacluateAllPoints(List<Pair<Integer, Integer>> coordinates) {
		int x1 = coordinates.get(0).getLeft();
		int y1 = coordinates.get(0).getRight();
		int x2 = coordinates.get(1).getLeft();
		int y2 = coordinates.get(1).getRight();

		int slope;
		if(x1 != x2) {
			slope = (y2 - y1) / (x2 - x1);
		}
		else {
			slope = 0;
		}

		int offset = y1 - slope * x1;

		if(x1 == x2) {
			return IntStream.rangeClosed(Math.min(y1, y2), Math.max(y1, y2))
					.mapToObj(y -> Pair.of(x1, y))
					.toList();
		}
		else {
			return IntStream.rangeClosed(Math.min(x1, x2), Math.max(x1, x2))
					.mapToObj(x -> Pair.of(x, slope * x + offset))
					.toList();
		}
	}

	@Test
	void part2() throws IOException {
		long numberOfOverlaps = getLineCoordinates()
				.map(this::cacluateAllPoints)
				.flatMap(List::stream)
				.collect(groupingBy(identity()))
				.values()
				.stream()
				.filter(list -> list.size() > 1)
				.count();

		log.info("answer Part2: " + numberOfOverlaps);
	}

}
