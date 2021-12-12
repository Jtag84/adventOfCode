package com.clement.advent2021.day.six;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DaySixTest extends SolutionBase {

	@Test
	void part1() throws IOException {
		long numberOfFishes = simulateFor(80);
		log.info("answer Part1: " + numberOfFishes);
	}

	@Test
	void part2() throws IOException {
		long numberOfFishes = simulateFor(256);
		log.info("answer Part2: " + numberOfFishes);
	}

	private long simulateFor(int numberOfDays) throws IOException {
		Map<Integer, Long> initialDayCounterNumberOfFishes = Stream.of(getInputsReader().readLine().split(","))
				.map(Integer::parseInt)
				.collect(groupingBy(identity(), counting()));

		Map<Integer, Long> dayCounterNumberOfFishes = initialDayCounterNumberOfFishes;
		for (int i = 1; i <= numberOfDays; i++) {
			dayCounterNumberOfFishes = dayCounterNumberOfFishes.entrySet().stream()
					.map(this::minusOneDay)
					.flatMap(List::stream)
					.collect(groupingBy(Map.Entry::getKey, summingLong(Map.Entry::getValue)));
		}

		return dayCounterNumberOfFishes.values().stream().mapToLong(l -> l).sum();
	}

	private List<Map.Entry<Integer, Long>> minusOneDay(Map.Entry<Integer, Long> entry) {
		int dayCounter = entry.getKey();
		long numberOfFishes = entry.getValue();

		if (dayCounter == 0) {
			return asList(new AbstractMap.SimpleImmutableEntry<>(6, numberOfFishes),
					new AbstractMap.SimpleImmutableEntry<>(8, numberOfFishes));
		} else {
			return singletonList(new AbstractMap.SimpleImmutableEntry<>(dayCounter - 1, numberOfFishes));
		}
	}
}
