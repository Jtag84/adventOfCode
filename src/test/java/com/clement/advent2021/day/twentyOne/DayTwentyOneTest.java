package com.clement.advent2021.day.twentyOne;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwentyOneTest extends SolutionBase {
	long player1StartingPosition = 3;
	long player2StartingPosition = 5;

	@Test
	void part1() throws IOException {
		long player1TotalScore = 0;
		long player2TotalScore = 0;

		long dieRolls = 0;
		long nextThreeDiceValue = 6;

		while (player1TotalScore < 1000 && player2TotalScore < 1000) {
			player1StartingPosition = (player1StartingPosition + nextThreeDiceValue) % 10;
			if (player1StartingPosition == 0) {
				player1StartingPosition = 10;
			}
			player1TotalScore += player1StartingPosition;
			dieRolls += 3;
			nextThreeDiceValue += 9;
			if (player1TotalScore >= 1000) {
				break;
			}
			player2StartingPosition = (player2StartingPosition + nextThreeDiceValue) % 10;
			if (player2StartingPosition == 0) {
				player2StartingPosition = 10;
			}
			player2TotalScore += player2StartingPosition;
			dieRolls += 3;
			nextThreeDiceValue += 9;
		}

		long losingScore = Math.min(player1TotalScore, player2TotalScore);
		log.info("answer Part1: " + (losingScore * dieRolls));
	}

	@Test
	void part2() throws IOException {


		log.info("answer Part2: " + 0);
	}
}