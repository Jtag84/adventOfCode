package com.clement.advent2021.day.eight;

import static com.google.common.collect.MoreCollectors.onlyElement;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

class DayEightTest {
	private static final Logger log = LoggerFactory.getLogger(DayEightTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	@Test
	void part1() throws IOException {
		long numberOfOnesFoursSevensEights = getInputsReader().lines()
				.map(line -> line.split(" "))
				.flatMap(digits -> Stream.of(digits).skip(11L))
				.map(String::length)
				.filter(length -> length == 2 || length == 3 || length == 4 || length == 7)
				.count();

		log.info("answer Part1: " + numberOfOnesFoursSevensEights);
	}

	@Test
	void part2() throws IOException {
		List<String[]> tenDigitsFourOutputsList = getInputsReader().lines()
				.map(line -> line.split(" \\| "))
				.toList();

		List<Map<Set<Integer>, Integer>> decoderMaps = tenDigitsFourOutputsList.stream()
				.map(tenDigitsFourOutputs -> Stream.of(tenDigitsFourOutputs[0].trim().split(" ")))
				.map(digitStream -> digitStream
						.map(String::chars)
						.map(intStream -> intStream.boxed().collect(toSet()))
						.collect(groupingBy(Set::size)))
				.map(this::calulateDecoderMap)
				.toList();

		List<List<Set<Integer>>> scrambledOutputDigitSegmentSets = tenDigitsFourOutputsList.stream()
				.map(tenDigitsFourOutputs -> Stream.of(tenDigitsFourOutputs[1].trim().split(" ")))
				.map(digitStream -> digitStream
						.map(String::chars)
						.map(intStream -> intStream.boxed().collect(toSet()))
						.collect(Collectors.toList()))
				.toList();

		long total = 0;
		for (int i = 0; i < scrambledOutputDigitSegmentSets.size(); i++) {
			Map<Set<Integer>, Integer> decoderMap = decoderMaps.get(i);
			total += scrambledOutputDigitSegmentSets.get(i).stream()
					.map(decoderMap::get)
					.reduce(0, (previousDigit, newDigit) -> previousDigit * 10 + newDigit);
		}

		log.info("answer Part2: " + total);
	}

	private Map<Set<Integer>, Integer> calulateDecoderMap(Map<Integer, List<Set<Integer>>> sizeToDigitSegmentSetsMap) {
		final Set<Integer> oneSegments = sizeToDigitSegmentSetsMap.get(2).get(0);
		final Set<Integer> fourSegments = sizeToDigitSegmentSetsMap.get(4).get(0);
		final Set<Integer> sevenSegments = sizeToDigitSegmentSetsMap.get(3).get(0);
		final Set<Integer> eightSegments = sizeToDigitSegmentSetsMap.get(7).get(0);

		List<Set<Integer>> zeroSixNineSets = sizeToDigitSegmentSetsMap.get(6);
		final Set<Integer> sixSegments = zeroSixNineSets.stream().filter(digitSegments -> !digitSegments.containsAll(oneSegments)).collect(onlyElement());

		List<Set<Integer>> zeroNineSets = zeroSixNineSets;
		zeroNineSets.remove(sixSegments);

		final Set<Integer> nineSegments = zeroNineSets.stream().filter(digitSegments -> digitSegments.containsAll(fourSegments)).collect(onlyElement());

		zeroNineSets.remove(nineSegments);
		final Set<Integer> zeroSegments = zeroNineSets.get(0);

		List<Set<Integer>> twoThreeFiveSets = sizeToDigitSegmentSetsMap.get(5);
		final Set<Integer> threeSegments = twoThreeFiveSets.stream().filter(digitSegments -> digitSegments.containsAll(oneSegments)).collect(onlyElement());

		List<Set<Integer>> twoFiveSets = twoThreeFiveSets;
		twoFiveSets.remove(threeSegments);

		final Set<Integer> fiveSegments = Sets.intersection(sixSegments, nineSegments).immutableCopy();

		twoFiveSets.remove(fiveSegments);
		final Set<Integer> twoSegments = twoFiveSets.get(0);

		Map<Set<Integer>, Integer> decoderMap = new HashMap<>();

		decoderMap.put(zeroSegments, 0);
		decoderMap.put(oneSegments, 1);
		decoderMap.put(twoSegments, 2);
		decoderMap.put(threeSegments, 3);
		decoderMap.put(fourSegments, 4);
		decoderMap.put(fiveSegments, 5);
		decoderMap.put(sixSegments, 6);
		decoderMap.put(sevenSegments, 7);
		decoderMap.put(eightSegments, 8);
		decoderMap.put(nineSegments, 9);

		return decoderMap;
	}
}
