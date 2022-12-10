package com.clement.advent2022.day10;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.base.Splitter;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		int totalSignalStrength = getTotalSignalStrength(getInputsExampleReader());

		log.info("answer Part1 Example: " + totalSignalStrength);
		Assertions.assertEquals(13140, totalSignalStrength);
	}

	@Test
	void part1() {
		int totalSignalStrength = getTotalSignalStrength(getInputsReader());

		log.info("answer Part1: " + totalSignalStrength);
		Assertions.assertEquals(14780, totalSignalStrength);
	}

	@Test
	void part2Example() {
		List<String> linesToPrint = getLinesToPrint(getInputsExampleReader());

		log.info("answer Part2Example: ");
		linesToPrint.forEach(log::info);

		String expected =
				"""
						##..##..##..##..##..##..##..##..##..##..
						###...###...###...###...###...###...###.
						####....####....####....####....####....
						#####.....#####.....#####.....#####.....
						######......######......######......####
						#######.......#######.......#######.....
						""";
		Assertions.assertEquals(expected.lines().toList(), linesToPrint);
	}

	@Test
	void part2() {
		List<String> linesToPrint = getLinesToPrint(getInputsReader());

		log.info("answer Part2: ");
		linesToPrint.forEach(log::info);

		String expected =
				"""
						####.#....###..#....####..##..####.#....
						#....#....#..#.#.......#.#..#....#.#....
						###..#....#..#.#......#..#......#..#....
						#....#....###..#.....#...#.##..#...#....
						#....#....#....#....#....#..#.#....#....
						####.####.#....####.####..###.####.####.
						""";

		Assertions.assertEquals(expected.lines().toList(), linesToPrint);
	}

	private List<String> getLinesToPrint(BufferedReader bufferedReader) {
		NavigableMap<Integer, Integer> cycleXValueMap = getCycleToXValueMap(bufferedReader);

		String lines = IntStream.rangeClosed(1, 240)
				.mapToObj(cycle -> getCharToPrint(cycleXValueMap, cycle))
				.collect(Collectors.joining());
		return Splitter.fixedLength(40).splitToList(lines);
	}

	@NotNull
	private static String getCharToPrint(NavigableMap<Integer, Integer> cycleXValueMap, int cycle) {
		int drawPosition = (cycle - 1) % 40;
		int spriteMiddlePosition = cycleXValueMap.lowerEntry(cycle).getValue();
		if (drawPosition >= (spriteMiddlePosition - 1) && drawPosition <= (spriteMiddlePosition + 1)) {
			return "#";
		} else {
			return ".";
		}
	}

	private int getTotalSignalStrength(BufferedReader bufferedReader) {
		NavigableMap<Integer, Integer> cycleToXValueMap = getCycleToXValueMap(bufferedReader);

		return IntStream.of(20, 60, 100, 140, 180, 220)
				.map(cycle -> cycleToXValueMap.lowerEntry(cycle).getValue() * cycle)
				.sum();
	}

	@NotNull
	private NavigableMap<Integer, Integer> getCycleToXValueMap(BufferedReader bufferedReader) {
		NavigableMap<Integer, Integer> initialCycleToXValueMap = new TreeMap<>();
		initialCycleToXValueMap.put(0, 1);
		return bufferedReader.lines()
				.map(this::parseLineToCycleXValueFunction)
				.reduce(initialCycleToXValueMap,
						(cycleToXValueMap, CycleToXValueFunction) -> {
							Pair<Integer, Integer> newCycleXValuePair = CycleToXValueFunction.apply(cycleToXValueMap.lastEntry());
							cycleToXValueMap.put(newCycleXValuePair.getKey(), newCycleXValuePair.getValue());
							return cycleToXValueMap;
						},
						(left, right) -> right);
	}

	private Function<Map.Entry<Integer, Integer>, Pair<Integer, Integer>> parseLineToCycleXValueFunction(String line) {
		String[] lineSplit = line.split(" ");

		String instruction = lineSplit[0];
		return switch (instruction) {
			case "addx" -> cycleXValuePair -> Pair.of(cycleXValuePair.getKey() + 2, cycleXValuePair.getValue() + Integer.parseInt(lineSplit[1]));
			case "noop" -> cycleXValuePair -> Pair.of(cycleXValuePair.getKey() + 1, cycleXValuePair.getValue());
			default -> throw new IllegalStateException("Don't know how to handle " + instruction);
		};
	}
}