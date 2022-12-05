package com.clement.advent2022.day05;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class Solution extends SolutionBase {
	public static final int NUMBER_OF_LINES_FOR_STACK_INPUT = 8;

	record Instruction(int numberToMove, int from, int to) {
		public static Instruction parse(String line) {
			String withoutMove = line.substring(5);
			String[] withoutFrom = withoutMove.split(" from ");
			String[] withoutTo = withoutFrom[1].split(" to ");

			int numberToMove = Integer.parseInt(withoutFrom[0]);
			int from = Integer.parseInt(withoutTo[0]);
			int to = Integer.parseInt(withoutTo[1]);

			return new Instruction(numberToMove, from, to);
		}
	}

	@Test
	void part1() {
		List<LinkedList<Character>> stacks = getStacks();
		List<Instruction> instructions = getInstructions();

		executeInstructionsOnStacksPart1(instructions, stacks);

		String topOfEveryStack = getTopOfEveryStack(stacks).stream().map(Object::toString).collect(Collectors.joining());
		log.info("answer Part1: top of each stack = " + topOfEveryStack);
	}

	@Test
	void part2() {
		List<LinkedList<Character>> stacks = getStacks();
		List<Instruction> instructions = getInstructions();

		executeInstructionsOnStacksPart2(instructions, stacks);

		String topOfEveryStack = getTopOfEveryStack(stacks).stream().map(Object::toString).collect(Collectors.joining());
		log.info("answer Part2: top of each stack = " + topOfEveryStack);
	}

	private List<LinkedList<Character>> getStacks() {
		List<String> lineStacks = getInputsReader().lines().limit(NUMBER_OF_LINES_FOR_STACK_INPUT).toList();
		int numberOfStacks = (lineStacks.get(0).length() + 1) / 4;

		List<LinkedList<Character>> stacks = new ArrayList<>();
		IntStream.range(0, numberOfStacks).forEach(i -> stacks.add(new LinkedList<>()));

		lineStacks.forEach(line -> {
			IntStream.range(0, numberOfStacks).forEach(i -> {
				Character stackValue = line.charAt(i * 4 + 1);
				if (stackValue != ' ') {
					stacks.get(i).addLast(stackValue);
				}
			});
		});
		return stacks;
	}

	private List<Instruction> getInstructions() {
		return getInputsReader().lines()
				.skip(NUMBER_OF_LINES_FOR_STACK_INPUT + 2)
				.map(Instruction::parse)
				.toList();
	}

	private void executeInstructionsOnStacksPart1(List<Instruction> instructions, List<LinkedList<Character>> stacks) {
		instructions.forEach(instruction -> {
			LinkedList<Character> fromStack = stacks.get(instruction.from - 1);
			LinkedList<Character> toStack = stacks.get(instruction.to - 1);

			IntStream.range(0, instruction.numberToMove).forEach(i -> toStack.addFirst(fromStack.pollFirst()));
		});
	}

	private void executeInstructionsOnStacksPart2(List<Instruction> instructions, List<LinkedList<Character>> stacks) {
		instructions.forEach(instruction -> {
			LinkedList<Character> fromStack = stacks.get(instruction.from - 1);
			LinkedList<Character> toStack = stacks.get(instruction.to - 1);

			List<Character> toMove = fromStack.subList(0, instruction.numberToMove);
			toStack.addAll(0, toMove);

			IntStream.range(0, instruction.numberToMove).forEach(i -> fromStack.removeFirst());
		});
	}

	private List<Character> getTopOfEveryStack(List<LinkedList<Character>> stacks) {
		return stacks.stream().map(LinkedList::getFirst).toList();
	}
}