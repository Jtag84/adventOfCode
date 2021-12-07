package com.clement.advent2021.day.seven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DaySevenTest {
	private static final Logger log = LoggerFactory.getLogger(DaySevenTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

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
