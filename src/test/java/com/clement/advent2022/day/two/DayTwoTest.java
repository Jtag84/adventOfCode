package com.clement.advent2022.day.two;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwoTest extends SolutionBase {

	@Test
	void part1() {
		int totalScore = getInputsReader().lines()
				.mapToInt(this::calculateLineScorePart1)
				.sum();

		log.info("answer Part1: totalScore = " + totalScore);
	}

	@Test
	void part2() throws IOException {
		int totalScore = getInputsReader().lines()
				.mapToInt(this::calculateLineScorePart2)
				.sum();

		log.info("answer Part2: totalScore = " + totalScore);
	}

	private int calculateLineScorePart1(String line) {
		HandEnum adversaryHand = HandEnum.parsePart1(line.charAt(0));
		HandEnum myHand = HandEnum.parsePart1(line.charAt(2));

		return myHand.scoreAgainst(adversaryHand);
	}

	private int calculateLineScorePart2(String line) {
		HandEnum adversaryHand = HandEnum.parsePart1(line.charAt(0));
		HandEnum myHand = adversaryHand.parseShouldPlayPart2(line.charAt(2));

		return myHand.scoreAgainst(adversaryHand);
	}
}