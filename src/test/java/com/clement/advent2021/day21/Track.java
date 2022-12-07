package com.clement.advent2021.day21;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class Track {
	private final Map<Triple<Player, Player, Integer>, Pair<Long, Long>> cacheResult = new ConcurrentHashMap<>();

	public static Player move(Player player, int numberOfSpacesToMove) {
		int newPosition = (player.trackPosition() + numberOfSpacesToMove) % 10;
		if (newPosition == 0) {
			newPosition = 10;
		}

		long newScore = player.score() + newPosition;

		return new Player(player.id(), newPosition, newScore);
	}

	public static Pair<Player, Player> playDeterministic(Player current, Player next, DeterministicDie die) {
		if (current.score() >= 1000 || next.score() >= 1000) {
			return Pair.of(current, next);
		}

		Player newCurrent = Track.move(current, die.roll() + die.roll() + die.roll());

		return playDeterministic(next, newCurrent, die);
	}

	public Pair<Long, Long> playDirac(Player current, Player next) {
		if (current.score() >= 21 || next.score() >= 21) {
			if (current.score() > next.score() && current.id() == 1
					|| current.score() < next.score() && current.id() == 2) {
				return Pair.of(1L, 0L);
			} else {
				return Pair.of(0L, 1L);
			}
		}

		return DiracDie.threeRolls.parallelStream()
				.map(rolls -> rolls.stream().mapToInt(i -> i).sum())
				.map(roll -> moveAndPlayWithCache(current, next, roll))
				.reduce((pair1, pair2) -> Pair.of(pair1.getLeft() + pair2.getLeft(), pair1.getRight() + pair2.getRight()))
				.orElseThrow(IllegalStateException::new);
	}

	private Pair<Long, Long> moveAndPlayWithCache(Player current, Player next, int roll) {
		Triple<Player, Player, Integer> cacheKey = Triple.of(current, next, roll);
		return Optional.ofNullable(cacheResult.get(cacheKey))
				.orElseGet(() -> {
					Player newCurrent = Track.move(current, roll);
					Pair<Long, Long> result = playDirac(next, newCurrent);
					cacheResult.putIfAbsent(cacheKey, result);
					return result;
				});
	}
}
