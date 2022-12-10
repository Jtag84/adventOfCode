package com.clement.advent2022.day09;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		Set<CordKnot> tailPositionVisited = getTailPositionVisited(getInputsExampleReader(), 2);

		log.info("answer Part1 Example: " + tailPositionVisited.size());
		Assertions.assertEquals(88, tailPositionVisited.size());
	}

	@Test
	void part1() {
		Set<CordKnot> tailPositionVisited = getTailPositionVisited(getInputsReader(), 2);

		log.info("answer Part1: " + tailPositionVisited.size());
		Assertions.assertEquals(6464, tailPositionVisited.size());
	}

	@Test
	void part2Example() {
		Set<CordKnot> tailPositionVisited = getTailPositionVisited(getInputsExampleReader(), 10);

		log.info("answer Part2Example: " + tailPositionVisited.size());
		Assertions.assertEquals(36, tailPositionVisited.size());
	}

	@Test
	void part2() {
		Set<CordKnot> tailPositionVisited = getTailPositionVisited(getInputsReader(), 10);

		log.info("answer Part2: " + tailPositionVisited.size());
		Assertions.assertEquals(2604, tailPositionVisited.size());
	}

	@NotNull
	private Set<CordKnot> getTailPositionVisited(BufferedReader bufferedReader, int cordLength) {
		List<Pair<Function<CordKnot, CordKnot>, Integer>> functionTimesPairs = bufferedReader.lines()
				.map(this::parseLine)
				.toList();

		Set<CordKnot> tailPositionVisited = new HashSet<>();

		List<CordKnot> currentCord = IntStream.range(0, cordLength).mapToObj(i -> new CordKnot(0, 0)).toList();
		for (Pair<Function<CordKnot, CordKnot>, Integer> functionTimesPair : functionTimesPairs) {
			for (int i = 0; i < functionTimesPair.getRight(); i++) {
				currentCord = currentCord.stream().reduce(
						Collections.emptyList(),
						(cord, cordKnot) -> {
							CordKnot newCordKnot;
							if (cord.isEmpty()) {
								newCordKnot = functionTimesPair.getLeft().apply(cordKnot);
							} else {
								newCordKnot = cordKnot.follow(cord.get(cord.size() - 1));
							}
							return ListUtils.union(cord, List.of(newCordKnot));
						},
						(oldCord, newCord) -> newCord);

				tailPositionVisited.add(currentCord.get(currentCord.size() - 1));
			}
		}
		return tailPositionVisited;
	}

	private Pair<Function<CordKnot, CordKnot>, Integer> parseLine(String line) {
		String[] lineSplit = line.split(" ");
		Function<CordKnot, CordKnot> moveFunction =
				switch (lineSplit[0].charAt(0)) {
					case 'R' -> CordKnot::moveRight;
					case 'L' -> CordKnot::moveLeft;
					case 'U' -> CordKnot::moveUp;
					case 'D' -> CordKnot::moveDown;
					default -> throw new IllegalStateException("Unexpected value: " + lineSplit[0].charAt(0));
				};

		int numberOfTimes = Integer.parseInt(lineSplit[1]);
		return Pair.of(moveFunction, numberOfTimes);
	}
}