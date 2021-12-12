package com.clement.advent2021.day.twelve;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayTwelveTest {
	private static final Logger log = LoggerFactory.getLogger(DayTwelveTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	@Test
	void part1() throws IOException {
		Map<Object, List<String>> cavesMap = getCavesMap();

		long numberOfPaths = cavesMap.get("end").stream()
				.map(toCave -> findPaths(cavesMap, asList("end", toCave), emptySet()))
				.flatMap(List::stream)
				.filter(CollectionUtils::isNotEmpty)
				.count();

		log.info("answer Part1: " + numberOfPaths);
	}

	private Map<Object, List<String>> getCavesMap() {
		Map<Object, List<String>> cavesMap = getInputsReader().lines()
				.map(line -> line.split("-"))
				.map(caves -> asList(Pair.of(caves[0], caves[1]), Pair.of(caves[1], caves[0])))
				.flatMap(List::stream)
				.collect(groupingBy(Pair::getLeft, mapping(Pair::getRight, toList())));
		return cavesMap;
	}

	private List<List<String>> findPaths(Map<Object, List<String>> cavesMap, List<String> path, Set<String> alreadyVisitedSmallCaves) {
		String toCave = path.get(path.size() - 1);
		if (toCave.equals("start")) {
			return singletonList(path);
		}

		if (alreadyVisitedSmallCaves.contains(toCave) || toCave.equals("end")) {
			return emptyList();
		}

		Set<String> newAlreadyVisitedSmallCaves = new HashSet<>(alreadyVisitedSmallCaves);
		if (StringUtils.isAllLowerCase(toCave)) {
			newAlreadyVisitedSmallCaves.add(toCave);
		}

		List<String> cavePaths = cavesMap.get(toCave);
		return cavePaths.stream()
				.map(cave -> findPaths(cavesMap, ListUtils.union(path, singletonList(cave)), newAlreadyVisitedSmallCaves))
				.flatMap(List::stream)
				.toList();
	}

	@Test
	void part2() throws IOException {
		Map<Object, List<String>> cavesMap = getCavesMap();

		long numberOfPaths = cavesMap.get("end").stream()
				.map(toCave -> findPaths(cavesMap, asList("end", toCave), emptyMap()))
				.flatMap(List::stream)
				.filter(CollectionUtils::isNotEmpty)
				.count();

		log.info("answer Part2: " + numberOfPaths);
	}

	private List<List<String>> findPaths(Map<Object, List<String>> cavesMap, List<String> path, Map<String, Integer> alreadyVisitedSmallCaves) {
		String toCave = path.get(path.size() - 1);
		if (toCave.equals("start")) {
			return singletonList(path);
		}

		if (toCave.equals("end")) {
			return emptyList();
		}

		if (alreadyVisitedSmallCaves.containsKey(toCave)) {
			boolean hasAnyCaveBeenVisitedTwice = alreadyVisitedSmallCaves.values().stream().anyMatch(numberOfVisits -> numberOfVisits >= 2);
			if (hasAnyCaveBeenVisitedTwice) {
				return emptyList();
			}
		}

		Map<String, Integer> newAlreadyVisitedSmallCaves = new HashMap<>(alreadyVisitedSmallCaves);
		if (StringUtils.isAllLowerCase(toCave)) {
			newAlreadyVisitedSmallCaves.compute(toCave, (caveName, numberOfVisits) -> (numberOfVisits == null) ? 1 : numberOfVisits + 1);
		}

		List<String> cavePaths = cavesMap.get(toCave);
		return cavePaths.stream()
				.map(cave -> findPaths(cavesMap, ListUtils.union(path, singletonList(cave)), newAlreadyVisitedSmallCaves))
				.flatMap(List::stream)
				.toList();
	}

}