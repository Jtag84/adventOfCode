package com.clement.advent2021.day.eighteen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayEighteenTest extends SolutionBase {

	@Test
	void part1() throws IOException {
		PairElement sum = getInputsReader().lines().map(line -> line.substring(1))
				.map(this::toPair)
				.map(Pair::reduce)
				.reduce((p1, p2) -> Pair.of(p1, p2).reduce()).get();
		log.info("answer Part1: " + sum.getMagnitude());
	}

	@Test
	void part2() throws IOException {
		List<Pair> pairLines = getInputsReader().lines().map(line -> line.substring(1))
				.map(this::toPair)
				.toList();

		List<Long> magnitudes = new ArrayList<>();
		for (int i = 0; i < pairLines.size() - 1; i++) {
			for (int j = i + 1; j < pairLines.size(); j++) {
				magnitudes.add(Pair.of(pairLines.get(i), pairLines.get(j)).reduce().getMagnitude());
				magnitudes.add(Pair.of(pairLines.get(j), pairLines.get(i)).reduce().getMagnitude());
			}
		}
		log.info("answer Part2: " + magnitudes.stream().max(Comparator.comparing(Function.identity())));
	}

	private Pair toPair(String line) {
		char firstChar = line.charAt(0);
		PairElement left;
		int indexOfRight = 2;
		if (firstChar == '[') {
			final int closingBracketIndex = findClosingBracketIndex(line);
			left = toPair(line.substring(1, closingBracketIndex));
			indexOfRight = closingBracketIndex + 2;
		} else {
			left = PairElementNumber.of(Integer.parseInt(line.substring(0, 1)));
		}

		char thirdChar = line.charAt(indexOfRight);
		PairElement right;
		if (thirdChar == '[') {
			right = toPair(line.substring(indexOfRight + 1));
		} else {
			right = PairElementNumber.of(Integer.parseInt("" + thirdChar));
		}
		return Pair.of(left, right);
	}

	private int findClosingBracketIndex(String line) {
		LinkedList<Character> queue = new LinkedList<>();
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '[') {
				queue.addLast(c);
			} else if (c == ']') {
				Character openingCharacter = queue.removeLast();
				if (queue.isEmpty()) {
					return i;
				}
			}
		}
		throw new IllegalStateException();
	}
}
