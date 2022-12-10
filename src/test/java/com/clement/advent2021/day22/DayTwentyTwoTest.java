package com.clement.advent2021.day22;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwentyTwoTest extends SolutionBase {

	@Test
	void part1Example() {
		long numberOfCubesOn = getNumberOfCubesOnPart1(getInputsExampleReader());
		log.info("answer Part1: " + numberOfCubesOn);
		Assertions.assertEquals(474140, numberOfCubesOn);
	}

	@Test
	void part1() {
		long numberOfCubesOn = getNumberOfCubesOnPart1(getInputsReader());
		log.info("answer Part1: " + numberOfCubesOn);
		Assertions.assertEquals(615700, numberOfCubesOn);
	}


	@Test
	void part2Example() {
		long numberOfCubesOn = getNumberOfCubesOnPart2(getInputsExampleReader());
		log.info("answer Part2 Example: " + numberOfCubesOn);
		Assertions.assertEquals(2758514936282235L, numberOfCubesOn);
	}

	@Test
	void part2() {
		// Takes ~14min to run. Some optimizations most likely needed
		long numberOfCubesOn = getNumberOfCubesOnPart2(getInputsReader());
		log.info("answer Part2: " + numberOfCubesOn);
		Assertions.assertEquals(1236463892941356L, numberOfCubesOn);
	}

	private long getNumberOfCubesOnPart2(BufferedReader bufferedReader) {
		return getNumberOfCubesOn(bufferedReader, p -> true);
	}

	private long getNumberOfCubesOnPart1(BufferedReader bufferedReader) {
		Cuboid cuboid50Limit = new Cuboid(Pair.of(-50, 50), Pair.of(-50, 50), Pair.of(-50, 50));
		return getNumberOfCubesOn(bufferedReader, pair -> pair.getRight().difference(cuboid50Limit).isEmpty());
	}

	private long getNumberOfCubesOn(BufferedReader bufferedReader, Predicate<Pair<Function<Set<Cuboid>, Set<Cuboid>>, Cuboid>> pairPredicate) {
		AtomicInteger lineProgress = new AtomicInteger(0);
		Set<Cuboid> cubesOn = bufferedReader.lines()
				.map(line -> line.split(" "))
				.map(this::getCuboidFunction)
				.filter(pairPredicate)
				.reduce(Collections.emptySet(),
						(currentOnCuboids, functionCuboidPair) -> {
							Set<Cuboid> newSet = functionCuboidPair.getLeft().apply(currentOnCuboids);
							log.debug("Processed line " + lineProgress.incrementAndGet() + " newSet.size() = " + newSet.size());
							return newSet;
						},
						(oldSet, newSet) -> newSet);

		return Cuboid.getVolume(cubesOn);
	}

	private Pair<Function<Set<Cuboid>, Set<Cuboid>>, Cuboid> getCuboidFunction(String[] lineSplit) {
		String[] ranges = lineSplit[1].split(",");
		Pair<Integer, Integer> xRange = getRange(ranges[0]);
		Pair<Integer, Integer> yRange = getRange(ranges[1]);
		Pair<Integer, Integer> zRange = getRange(ranges[2]);
		Assertions.assertTrue(xRange.getLeft() <= xRange.getRight());
		Assertions.assertTrue(yRange.getLeft() <= yRange.getRight());
		Assertions.assertTrue(zRange.getLeft() <= zRange.getRight());
		Cuboid lineCuboid = new Cuboid(xRange, yRange, zRange);

		if (lineSplit[0].equals("on")) {
			return Pair.of(lineCuboid::union, lineCuboid);
		} else {
			return Pair.of(cuboids -> cuboids.stream()
							.map(cuboid -> cuboid.difference(lineCuboid))
							.flatMap(Collection::stream)
							.collect(Collectors.toSet()),
					lineCuboid);
		}
	}

	private Pair<Integer, Integer> getRange(String range) {
		int min = Integer.parseInt(StringUtils.substringBetween(range, "=", ".."));
		int max = Integer.parseInt(StringUtils.substringAfter(range, ".."));
		return Pair.of(min, max);
	}

	@Test
	void testCuboidUnion_shouldReturnSameCuboids_whenNotOverlapping() {
		Cuboid cuboid1 = new Cuboid(Pair.of(0, 5), Pair.of(0, 5), Pair.of(0, 5));
		Cuboid cuboid2 = new Cuboid(Pair.of(6, 7), Pair.of(0, 5), Pair.of(0, 5));

		Set<Cuboid> union = cuboid1.union(cuboid2);
		Assertions.assertEquals(2, union.size());
		Assertions.assertTrue(union.contains(cuboid1));
		Assertions.assertTrue(union.contains(cuboid2));
	}

	@Test
	void testCuboidUnion_shouldReturnUnion_whenOverlapping() {
		Cuboid cuboid1 = new Cuboid(Pair.of(0, 5), Pair.of(0, 5), Pair.of(0, 5));
		Cuboid cuboid2 = new Cuboid(Pair.of(4, 7), Pair.of(4, 5), Pair.of(0, 5));

		Cuboid intersection = cuboid1.intersection(cuboid2).orElseThrow();

		Set<Cuboid> union = cuboid1.union(cuboid2);
		long totalUnionVolume = Cuboid.getVolume(union);
		Assertions.assertEquals(5, union.size());
		Assertions.assertEquals(cuboid1.getVolume() + cuboid2.getVolume() - intersection.getVolume(), totalUnionVolume);
		Assertions.assertEquals(240, totalUnionVolume);

		cuboid1 = new Cuboid(Pair.of(-5, 47), Pair.of(-31, 22), Pair.of(-19, 33));
		cuboid2 = new Cuboid(Pair.of(-44, 5), Pair.of(-27, 21), Pair.of(-14, 35));

		intersection = cuboid1.intersection(cuboid2).orElseThrow();

		union = cuboid1.union(cuboid2);
		Set<Cuboid> unions = cuboid1.union(Set.of(cuboid2));
		totalUnionVolume = Cuboid.getVolume(union);
		long totalUnionsVolume = Cuboid.getVolume(unions);
		Assertions.assertEquals(totalUnionVolume, totalUnionsVolume);
		long expectedVolume = cuboid1.getVolume() + cuboid2.getVolume() - intersection.getVolume();
		Assertions.assertEquals(expectedVolume, totalUnionVolume);
	}
}