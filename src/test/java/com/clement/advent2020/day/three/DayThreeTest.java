package com.clement.advent2020.day.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayThreeTest {
	private static final Logger log = LoggerFactory.getLogger(DayThreeTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	@Test
	void part1() throws IOException {
		char[][] forest = getForest();

		long numberOfTreesInSlope = getNumberOfTreesInSlope(forest, 3, 1);
		log.info("answer Part1: " + numberOfTreesInSlope);
	}

	private char[][] getForest() {
		return getInputsReader().lines()
				.map(String::toCharArray)
				.toArray(char[][]::new);
	}

	private long getNumberOfTreesInSlope(char[][] forest, int xSlope, int ySlope) {
		long numberOfTreesInSlope = 0;
		for (int x = 0, y = 0; y < forest.length; x = (x + xSlope) % forest[0].length, y = y + ySlope) {
			if (forest[y][x] == '#') {
				numberOfTreesInSlope++;
			}
		}
		return numberOfTreesInSlope;
	}

	@Test
	void part2() throws IOException {
		char[][] forest = getForest();

		long numberOfTreesInSlope1 = getNumberOfTreesInSlope(forest, 1, 1);
		long numberOfTreesInSlope2 = getNumberOfTreesInSlope(forest, 3, 1);
		long numberOfTreesInSlope3 = getNumberOfTreesInSlope(forest, 5, 1);
		long numberOfTreesInSlope4 = getNumberOfTreesInSlope(forest, 7, 1);
		long numberOfTreesInSlope5 = getNumberOfTreesInSlope(forest, 1, 2);

		log.info("answer Part2: " + (numberOfTreesInSlope1 * numberOfTreesInSlope2 * numberOfTreesInSlope3 * numberOfTreesInSlope4 * numberOfTreesInSlope5));
	}
}
