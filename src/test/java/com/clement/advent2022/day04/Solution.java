package com.clement.advent2022.day04;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class Solution extends SolutionBase {

	@Test
	void part1() {
		long numberOfFullyContainedRanges = getInputsReader().lines()
				.map(this::lineToRanges)
				.filter(this::isFullyContained)
				.count();

		log.info("answer Part1: numberOfFullyContainedRanges = " + numberOfFullyContainedRanges);
	}

	@Test
	void part2() {
		long numberOfOverlappedRanges = getInputsReader().lines()
				.map(this::lineToRanges)
				.filter(this::isOverlap)
				.count();

		log.info("answer Part2: numberOfOverlappedRanges = " + numberOfOverlappedRanges);
	}

	private List<Pair<Integer, Integer>> lineToRanges(String line) {
		String[] ranges = line.split(",");
		return List.of(toRangePair(ranges[0]), toRangePair(ranges[1]));
	}

	private Pair<Integer, Integer> toRangePair(String rangeString) {
		String[] boundaries = rangeString.split("-");
		return Pair.of(Integer.parseInt(boundaries[0]), Integer.parseInt(boundaries[1]));
	}

	private boolean isFullyContained(List<Pair<Integer, Integer>> ranges) {
		int leftDifference = ranges.stream()
				.mapToInt(Pair::getLeft)
				.reduce(Math::subtractExact)
				.orElseThrow(RuntimeException::new);

		int rightDifference = ranges.stream()
				.mapToInt(Pair::getRight)
				.reduce(Math::subtractExact)
				.orElseThrow(RuntimeException::new);

		log.debug(ranges + " " + leftDifference + " " + rightDifference + " " + (leftDifference * rightDifference <= 0));
		return leftDifference * rightDifference <= 0;
	}

	private boolean isOverlap(List<Pair<Integer, Integer>> ranges) {
		Pair<Integer, Integer> range1 = ranges.get(0);
		Pair<Integer, Integer> range2 = ranges.get(1);
		if (range2.getLeft() >= range1.getLeft() && range2.getLeft() <= range1.getRight()) {
			return true;
		}

		if (range2.getRight() >= range1.getLeft() && range2.getRight() <= range1.getRight()) {
			return true;
		}

		if (range1.getLeft() >= range2.getLeft() && range1.getLeft() <= range2.getRight()) {
			return true;
		}

		return range1.getRight() >= range2.getLeft() && range1.getRight() <= range2.getRight();
	}
}