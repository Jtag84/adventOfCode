package com.clement.advent2021.day.fourteen;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
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

		List<String> pairs = new ArrayList<>();
		for (int i = 1; i < polymerTemplate.length(); i++) {
			pairs.add(polymerTemplate.substring(i - 1, i + 1));
		}
		Map<Pair<String, Integer>, Map<Character, Long>> cache = new HashMap<>();
		Map<Character, Long> elementCountMap = pairs.stream()
				.map(currentPair -> calculateCharacterCountAfterInsertionForStep(currentPair, pairInsertionRules, new HashMap<>(), cache, numberOfSteps))
				.reduce(Collections.emptyMap(), this::mergeMap);

		polymerTemplate.chars().mapToObj(c -> (char) c).forEach(c -> elementCountMap.compute(c, (key, value) -> value == null ? 1 : value + 1));

		long mostCommonElement = elementCountMap.values().stream().max(Comparator.comparing(identity())).get();
		long leastCommonElement = elementCountMap.values().stream().min(Comparator.comparing(identity())).get();

		return mostCommonElement - leastCommonElement;
	}

	private Map<Character, Long> mergeMap(Map<Character, Long> leftMap, Map<Character, Long> rightMap) {
		Map<Character, Long> mergedMap = new HashMap<>(leftMap);
		rightMap.forEach((element, count) -> mergedMap.merge(element, count, Long::sum));
		return mergedMap;
	}


	private Map<Character, Long> calculateCharacterCountAfterInsertionForStep(String currentPair, Map<String, String> pairInsertionRules, Map<Character, Long> elementCountMap, Map<Pair<String, Integer>, Map<Character, Long>> cache, int step) {
		if (step <= 0) {
			return elementCountMap;
		}

		final Pair<String, Integer> cacheKey = Pair.of(currentPair, step);
		final Map<Character, Long> cachedMap = cache.get(cacheKey);
		if (cachedMap != null) {
			return cachedMap;
		}

		String insertedElement = pairInsertionRules.get(currentPair);
		elementCountMap.compute(insertedElement.charAt(0), (key, value) -> value == null ? 1 : value + 1);

		String leftPair = currentPair.charAt(0) + insertedElement;
		String rightPair = insertedElement + currentPair.charAt(1);
		Map<Character, Long> elementCountMapLeft = calculateCharacterCountAfterInsertionForStep(leftPair, pairInsertionRules, new HashMap<>(), cache, step - 1);
		Map<Character, Long> elementCountMapRight = calculateCharacterCountAfterInsertionForStep(rightPair, pairInsertionRules, new HashMap<>(), cache, step - 1);

		Map<Character, Long> mergedMaps = mergeMap(elementCountMapLeft, elementCountMapRight);
		mergedMaps = mergeMap(mergedMaps, elementCountMap);

		cache.put(cacheKey, mergedMaps);

		return mergedMaps;
	}
}