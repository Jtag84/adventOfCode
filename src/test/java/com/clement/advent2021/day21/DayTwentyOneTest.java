package com.clement.advent2021.day21;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwentyOneTest extends SolutionBase {
	int player1StartingPosition = 3;
	int player2StartingPosition = 5;

	int player1StartingPositionExample = 4;
	int player2StartingPositionExample = 8;

	@Test
	void part1Example() {
		long result = part1PlayWithStartingPosition(player1StartingPositionExample, player2StartingPositionExample);

		log.info("answer Part1 Example: " + result);
		Assertions.assertEquals(739785, result);
	}

	@Test
	void part1() {
		long result = part1PlayWithStartingPosition(player1StartingPosition, player2StartingPosition);

		log.info("answer Part1: " + result);
	}

	private long part1PlayWithStartingPosition(int player1Start, int player2Start) {
		DeterministicDie die = new DeterministicDie();

		Player player1 = new Player(1, player1Start, 0);
		Player player2 = new Player(2, player2Start, 0);

		Pair<Player, Player> playerPair = Track.playDeterministic(player1, player2, die);

		long loserScore = Math.min(playerPair.getLeft().score(), playerPair.getRight().score());

		return loserScore * die.numberOfRolls();
	}

	@Test
	void part2Example() {
		Pair<Long, Long> numberOfWinsPlayer1Player2Pair = getNumberOfWinsPlayer1Player2Pair(player1StartingPositionExample, player2StartingPositionExample);

		log.info("answer Part2 Example: " + numberOfWinsPlayer1Player2Pair);
		Assertions.assertEquals(444356092776315L, numberOfWinsPlayer1Player2Pair.getLeft());
		Assertions.assertEquals(341960390180808L, numberOfWinsPlayer1Player2Pair.getRight());
	}

	@Test
	void part2() {
		Pair<Long, Long> numberOfWinsPlayer1Player2Pair = getNumberOfWinsPlayer1Player2Pair(player1StartingPosition, player2StartingPosition);

		log.info("answer Part2: " + Math.max(numberOfWinsPlayer1Player2Pair.getLeft(), numberOfWinsPlayer1Player2Pair.getRight()));
	}

	private Pair<Long, Long> getNumberOfWinsPlayer1Player2Pair(int player1Start, int player2Start) {
		Player player1 = new Player(1, player1Start, 0);
		Player player2 = new Player(2, player2Start, 0);

		Track track = new Track();
		return track.playDirac(player1, player2);
	}
}