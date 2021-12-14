package com.clement.advent2021.day.fourteen;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayFourteenTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		log.info("answer Part1: " + getResult(10));
	}

	@Test
	void part2() throws IOException {
		log.info("answer Part2: " + getResult(40));
	}

	private long getResult(int numberOfSteps) throws IOException {
		String polymerTemplate = getInputsReader().readLine();

		Map<String, String> pairInsertionRules = getInputsReader().lines()
				.skip(2)
				.map(line -> line.split(" -> "))
				.collect(toMap(pairInsertionRule -> pairInsertionRule[0], pairInsertionRule -> pairInsertionRule[1]));

		Map<Character, Long> elementCountMap = new ConcurrentHashMap<>();

		List<String> pairs = new ArrayList<>();
		for (int i = 1; i < polymerTemplate.length(); i++) {
			pairs.add(polymerTemplate.substring(i - 1, i + 1));
		}

		pairs.parallelStream()
				.forEach(currentPair -> calculateCharacterCountAfterInsertionForStep(currentPair, pairInsertionRules, elementCountMap, numberOfSteps));

		long mostCommonElement = elementCountMap.values().stream().max(Comparator.comparing(identity())).get();
		long leastCommonElement = elementCountMap.values().stream().min(Comparator.comparing(identity())).get();

		return mostCommonElement - leastCommonElement;
	}


	private void calculateCharacterCountAfterInsertionForStep(String currentPair, Map<String, String> pairInsertionRules, Map<Character, Long> elementCountMap, int step) {
		if (step <= 0) {
			return;
		}

		String insertedElement = pairInsertionRules.get(currentPair);
		elementCountMap.compute(insertedElement.charAt(0), (key, value) -> value == null ? 1 : value + 1);

		String leftPair = currentPair.charAt(0) + insertedElement;
		String rightPair = insertedElement + currentPair.charAt(1);
		calculateCharacterCountAfterInsertionForStep(leftPair, pairInsertionRules, elementCountMap, step - 1);
		calculateCharacterCountAfterInsertionForStep(rightPair, pairInsertionRules, elementCountMap, step - 1);
	}
}