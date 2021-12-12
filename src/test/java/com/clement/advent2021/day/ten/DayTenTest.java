package com.clement.advent2021.day.ten;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.base.Optional;

class DayTenTest extends SolutionBase {
	Map<Character, Character> openingClosingCharacters = Map.of('(', ')', '[', ']', '{', '}', '<', '>');

	@Test
	void part1() throws IOException {
		long total = getInputsReader().lines()
				.map(this::findWrongTerminationCharacter)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.mapToLong(this::toClosingCharacterValue)
				.sum();

		log.info("answer Part1: " + total);
	}

	private long toClosingCharacterValue(Character closingCharacter) {
		return switch (closingCharacter) {
			case ')' -> 3;
			case ']' -> 57;
			case '}' -> 1197;
			case '>' -> 25137;
			default -> throw new IllegalArgumentException("Error " + closingCharacter);
		};
	}

	private Optional<Character> findWrongTerminationCharacter(String s) {
		LinkedList<Character> queue = new LinkedList<>();
		for (Character c : s.toCharArray()) {
			if (openingClosingCharacters.containsKey(c)) {
				queue.addLast(c);
			} else {
				Character openingCharacter = queue.removeLast();
				if (!openingClosingCharacters.get(openingCharacter).equals(c)) {
					return Optional.of(c);
				}
			}
		}
		return Optional.absent();
	}

	@Test
	void part2() throws IOException {
		List<Long> totals = getInputsReader().lines()
				.map(this::calculateCompleteSequenceValue)
				.filter(value -> value > 0)
				.sorted()
				.toList();

		log.info("answer Part2: " + totals.get(totals.size() / 2));
	}

	private long calculateCompleteSequenceValue(String s) {
		LinkedList<Character> queue = new LinkedList<>();
		for (Character c : s.toCharArray()) {
			if (openingClosingCharacters.containsKey(c)) {
				queue.addLast(c);
			} else {
				Character openingCharacter = queue.removeLast();
				if (!openingClosingCharacters.get(openingCharacter).equals(c)) {
					return 0;
				}
			}
		}
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
						queue.descendingIterator(),
						Spliterator.ORDERED), false)
				.map(openingClosingCharacters::get)
				.mapToLong(this::toTerminationCharValue)
				.reduce(0, (previousValue, newValue) -> previousValue * 5 + newValue);
	}

	private long toTerminationCharValue(Character closingCharacter) {
		return switch (closingCharacter) {
			case ')' -> 1;
			case ']' -> 2;
			case '}' -> 3;
			case '>' -> 4;
			default -> throw new IllegalArgumentException("Error " + closingCharacter);
		};
	}
}