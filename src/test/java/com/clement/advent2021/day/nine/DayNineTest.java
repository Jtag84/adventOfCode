package com.clement.advent2021.day.nine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayNineTest {
	private static final Logger log = LoggerFactory.getLogger(DayNineTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

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

		log.info("answer Part2: " + 0);
	}
}
