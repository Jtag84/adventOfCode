package com.clement.advent2021.day.two;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayTwoTest {
	private static final Logger log = LoggerFactory.getLogger(DayTwoTest.class);

	private Stream<String> getInputLines() {
		InputStream is = this.getClass().getResourceAsStream("/2021/dayTwo/inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader.lines();
	}

	private Pair<Function<Long, Long>, Function<Long, Long>> parseInstructionPart1(String line) {
		String[] instructionValuePair = line.split(" ");
		String instruction = instructionValuePair[0];
		return switch (instruction) {
			case "forward" -> 	Pair.of((horizontalPosition) -> horizontalPosition + Long.parseLong(instructionValuePair[1]), Function.identity());
			case "up" -> 		Pair.of(Function.identity(), (depthPosition) -> depthPosition - Long.parseLong(instructionValuePair[1]));
			case "down" -> 		Pair.of(Function.identity(), (depthPosition) -> depthPosition + Long.parseLong(instructionValuePair[1]));
			default -> throw new IllegalArgumentException("Can't handle " + instruction);
		};
	}

	public Stream<Pair<Function<Long, Long>, Function<Long, Long>>> getInstructionsPart1() {
		return getInputLines().map(this::parseInstructionPart1);
	}

	private Pair<Long, Long> applyInstructionPart1(Pair<Long, Long> position, Pair<Function<Long, Long>, Function<Long, Long>> instruction) {
		return Pair.of(instruction.getLeft().apply(position.getLeft()), instruction.getRight().apply(position.getRight()));
	}

	private Triple<Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>> parseInstructionPart2(String line) {
		String[] instructionValuePair = line.split(" ");
		String instruction = instructionValuePair[0];
		final long value = Long.parseLong(instructionValuePair[1]);
		return switch (instruction) {
			case "up" -> 		Triple.of(Triple::getLeft, Triple::getMiddle, (triple) -> triple.getRight() - value);
			case "down" -> 		Triple.of(Triple::getLeft, Triple::getMiddle, (triple) -> triple.getRight() + value);
			case "forward" -> 	Triple.of((triple) -> triple.getLeft() + value, (triple) -> triple.getMiddle() + value * triple.getRight(), Triple::getRight);
			default -> throw new IllegalArgumentException("Can't handle " + instruction);
		};
	}

	public Stream<Triple<Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>>> getInstructionsPart2() {
		return getInputLines().map(this::parseInstructionPart2);
	}

	private Triple<Long, Long, Long> applyInstructionPart2(Triple<Long, Long, Long> position, Triple<Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>, Function<Triple<Long, Long, Long>, Long>> instruction) {
		return Triple.of(instruction.getLeft().apply(position), instruction.getMiddle().apply(position), instruction.getRight().apply(position));
	}

	@Test
	void part1(){
		Pair<Long, Long> finalPosition = getInstructionsPart1().reduce(Pair.of(0L, 0L), this::applyInstructionPart1,(previousPosition, newPosition) -> newPosition);
		log.info("answer Part1: " + (finalPosition.getLeft() * finalPosition.getRight()));
	}

	@Test
	void part2(){
		Triple<Long, Long, Long> finalPosition = getInstructionsPart2().reduce(Triple.of(0L, 0L, 0L), this::applyInstructionPart2, (previousPosition, newPosition) -> newPosition);
		log.info("answer Part2: " + (finalPosition.getLeft() * finalPosition.getMiddle()));
	}
}
