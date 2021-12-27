package com.clement.advent2021.day.twentyTwo;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Sets;

class DayTwentyTwoTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		Set<Triple<Integer, Integer, Integer>> cubesOn = new HashSet<>();

		getInputsReader().lines()
				.map(line -> line.split(" "))
				.map(this::toTuples)
				.forEach(actionCubes -> actionCubes.getRight()
						.forEach(cube -> actionCubes.getLeft().accept(cubesOn, cube)));

		log.info("answer Part1: " + cubesOn.size());
	}

	private Pair<BiConsumer<Set<Triple<Integer, Integer, Integer>>, Triple<Integer, Integer, Integer>>, Set<Triple<Integer, Integer, Integer>>> toTuples(String[] lineSplit) {
		String[] ranges = lineSplit[1].split(",");
		Set<Integer> xs = getAxisValuesFromRange(ranges[0]);
		Set<Integer> ys = getAxisValuesFromRange(ranges[1]);
		Set<Integer> zs = getAxisValuesFromRange(ranges[2]);
		Set<List<Integer>> cubes = Sets.cartesianProduct(xs, ys, zs);
		Set<Triple<Integer, Integer, Integer>> cubeTriples = cubes.stream().map(cube -> Triple.of(cube.get(0), cube.get(1), cube.get(2))).collect(Collectors.toSet());
		BiConsumer<Set<Triple<Integer, Integer, Integer>>, Triple<Integer, Integer, Integer>> action;
		if (lineSplit[0].equals("on")) {
			action = Set::add;
		} else {
			action = Set::remove;
		}

		return Pair.of(action, cubeTriples);
	}

	private Set<Integer> getAxisValuesFromRange(String range) {
		int min = Integer.parseInt(StringUtils.substringBetween(range, "=", ".."));
		int max = Integer.parseInt(StringUtils.substringAfter(range, ".."));
		if (min > 50 || max < -50) {
			return Collections.emptySet();
		}
		return IntStream.rangeClosed(Math.max(-50, min), Math.min(50, max)).boxed().collect(Collectors.toSet());
	}

	@Test
	void part2() throws IOException {
		log.info("answer Part2: " + 0);
	}
}