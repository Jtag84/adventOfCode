package com.clement.advent2021.day.eleven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Lists;
import com.google.common.collect.MoreCollectors;

class DayElevenTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		int[][] octopuses = getOctopuses();

		int flashCount = IntStream.rangeClosed(1, 100)
				.map(i -> executeStep(octopuses))
				.sum();

		log.info("answer Part1: " + flashCount);
	}

	@Test
	void part2() throws IOException {
		int[][] octopuses = getOctopuses();

		int firstAllFlashingStep = IntStream.iterate(1, i -> i + 1)
				.mapToObj(i -> Pair.of(i, executeStep(octopuses)))
				.dropWhile(indexFlashesCountPair -> indexFlashesCountPair.getRight() < 100)
				.limit(1)
				.map(Pair::getLeft)
				.collect(MoreCollectors.onlyElement());

		log.info("answer Part2: " + firstAllFlashingStep);
	}

	private int executeStep(int[][] octopuses) {
		for (int x = 0; x < octopuses[0].length; x++) {
			for (int y = 0; y < octopuses.length; y++) {
				incrementEnergy(octopuses, x, y);
			}
		}

		int flashCount = 0;
		for (int x = 0; x < octopuses[0].length; x++) {
			for (int y = 0; y < octopuses.length; y++) {
				if (octopuses[y][x] > 9) {
					octopuses[y][x] = 0;
					flashCount++;
				}
			}
		}
		return flashCount;
	}

	private void incrementEnergy(int[][] octopuses, int x, int y) {
		octopuses[y][x]++;
		if (octopuses[y][x] == 10) {
			List<Integer> validXs = getValidCoordinates(x, octopuses[0].length);
			List<Integer> validYs = getValidCoordinates(y, octopuses.length);
			Lists.cartesianProduct(validXs, validYs).stream()
					.skip(1)
					.forEach(coordinates -> incrementEnergy(octopuses, coordinates.get(0), coordinates.get(1)));
		}

	}

	private List<Integer> getValidCoordinates(int position, int length) {
		List<Integer> validCoordinates = new ArrayList<>();
		validCoordinates.add(position);
		if (position > 0) {
			validCoordinates.add(position - 1);
		}
		if (position < length - 1) {
			validCoordinates.add(position + 1);
		}
		return validCoordinates;
	}

	private int[][] getOctopuses() {
		return getInputsReader().lines()
				.map(String::chars)
				.map(intStream -> intStream
						.map(character -> character - '0')
						.toArray())
				.toArray(int[][]::new);
	}
}