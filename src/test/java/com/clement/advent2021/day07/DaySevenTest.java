package com.clement.advent2021.day07;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DaySevenTest extends SolutionBase {

	@Test
	void part1() throws IOException {
		List<Integer> sortedCrabPositions = getSortedCrabPositions();

		int median = Math.round((sortedCrabPositions.get(499) + sortedCrabPositions.get(500)) / 2);

		int totalFuelUsed = sortedCrabPositions.stream().mapToInt(position -> Math.abs(position - median)).sum();

		log.info("answer Part1: " + totalFuelUsed);
	}

	@Test
	void part2() throws IOException {
		List<Integer> sortedCrabPositions = getSortedCrabPositions();

		int max = sortedCrabPositions.get(999);
		int min = sortedCrabPositions.get(0);

		int minTotalFuelUsed = IntStream.rangeClosed(min, max)
				.map(aligningPosition -> sortedCrabPositions.stream().mapToInt(position -> calculateFuelUsed(aligningPosition, position)).sum())
				.min().orElseThrow(() -> new IllegalStateException("error"));

		log.info("answer Part2: " + minTotalFuelUsed);
	}

	private List<Integer> getSortedCrabPositions() throws IOException {
		return Stream.of(getInputsReader().readLine().split(","))
				.map(Integer::parseInt)
				.sorted()
				.toList();
	}

	private int calculateFuelUsed(Integer aligningPosition, Integer currentPosition) {
		int absoluteDifference = Math.abs(currentPosition - aligningPosition);

		return absoluteDifference * (absoluteDifference + 1) / 2;
	}

}
