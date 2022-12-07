package com.clement.advent2020.day03;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clement.utils.SolutionBase;

class DayThreeTest extends SolutionBase {
	private static final Logger log = LoggerFactory.getLogger(DayThreeTest.class);

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
