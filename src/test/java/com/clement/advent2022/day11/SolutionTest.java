package com.clement.advent2022.day11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		List<Monkey> monkeys = getExampleMonkeys();
		long monkeyBusiness = getMonkeyBusinessScorePart1(monkeys);

		log.info("answer Part1 Example: " + monkeyBusiness);
		Assertions.assertEquals(10605, monkeyBusiness);
	}


	@Test
	void part1() {
		List<Monkey> monkeys = getMonkeys();
		long monkeyBusiness = getMonkeyBusinessScorePart1(monkeys);

		log.info("answer Part1: " + monkeyBusiness);
		Assertions.assertEquals(50616, monkeyBusiness);
	}

	@Test
	void part2Example() {
		List<Monkey> monkeys = getExampleMonkeys();
		long monkeyBusiness = getMonkeyBusinessScorePart2(monkeys);

		log.info("answer Part2 Example: " + monkeyBusiness);
		Assertions.assertEquals(2713310158L, monkeyBusiness);
	}

	@Test
	void part2() {
		List<Monkey> monkeys = getMonkeys();
		long monkeyBusiness = getMonkeyBusinessScorePart2(monkeys);

		log.info("answer Part2: " + monkeyBusiness);
		Assertions.assertEquals(11309046332L, monkeyBusiness);
	}

	@NotNull
	private static List<Monkey> getMonkeys() {
		Monkey monkey0 = new Monkey(0,
				mutableLongList(78, 53, 89, 51, 52, 59, 58, 85),
				old -> old * 3,
				5, 2, 7);
		Monkey monkey1 = new Monkey(1,
				mutableLongList(64),
				old -> old + 7,
				2, 3, 6);
		Monkey monkey2 = new Monkey(2,
				mutableLongList(71, 93, 65, 82),
				old -> old + 5,
				13, 5, 4);
		Monkey monkey3 = new Monkey(3,
				mutableLongList(67, 73, 95, 75, 56, 74),
				old -> old + 8,
				19, 6, 0);
		Monkey monkey4 = new Monkey(4,
				mutableLongList(85, 91, 90),
				old -> old + 4,
				11, 3, 1);
		Monkey monkey5 = new Monkey(5,
				mutableLongList(67, 96, 69, 55, 70, 83, 62),
				old -> old * 2,
				3, 4, 1);
		Monkey monkey6 = new Monkey(6,
				mutableLongList(53, 86, 98, 70, 64),
				old -> old + 6,
				7, 7, 0);
		Monkey monkey7 = new Monkey(7,
				mutableLongList(88, 64),
				old -> old * old,
				17, 2, 5);

		return List.of(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7);
	}

	@NotNull
	private static List<Monkey> getExampleMonkeys() {
		Monkey monkey0 = new Monkey(0,
				mutableLongList(79, 98),
				old -> old * 19,
				23, 2, 3);
		Monkey monkey1 = new Monkey(1,
				mutableLongList(54, 65, 75, 74),
				old -> old + 6,
				19, 2, 0);
		Monkey monkey2 = new Monkey(2,
				mutableLongList(79, 60, 97),
				old -> old * old,
				13, 1, 3);
		Monkey monkey3 = new Monkey(3,
				mutableLongList(74),
				old -> old + 3,
				17, 0, 1);

		return List.of(monkey0, monkey1, monkey2, monkey3);
	}

	private static List<Long> mutableLongList(Integer... integers) {
		return Stream.of(integers).map(Integer::longValue).collect(Collectors.toCollection(ArrayList::new));
	}

	private static void inspectRounds(int numberOfRounds, List<Monkey> monkeys, Map<Integer, Long> numberOfInspectedItemsPerMonkey, Function<Long, Long> postInspectionOperation) {
		IntStream.range(0, numberOfRounds)
				.forEach(i ->
						monkeys.forEach(monkey -> {
							numberOfInspectedItemsPerMonkey.compute(monkey.id(), (monkeyId, currentValue) -> monkey.items().size() + Optional.ofNullable(currentValue).orElse(0L));
							monkey.inspectItems(monkeys, postInspectionOperation);
						}));
	}

	private static long getMonkeyBusinessScorePart1(List<Monkey> monkeys) {
		Map<Integer, Long> numberOfInspectedItemsPerMonkey = new HashMap<>();
		inspectRounds(20, monkeys, numberOfInspectedItemsPerMonkey, i -> i / 3);

		return calculateMonkeyBusiness(numberOfInspectedItemsPerMonkey);
	}

	private static long getMonkeyBusinessScorePart2(List<Monkey> monkeys) {
		Map<Integer, Long> numberOfInspectedItemsPerMonkey = new HashMap<>();
		long coPrime = monkeys.stream().mapToLong(Monkey::divisibleBy).reduce(1, ((left, right) -> left * right));

		inspectRounds(10000, monkeys, numberOfInspectedItemsPerMonkey, i -> i % coPrime);

		return calculateMonkeyBusiness(numberOfInspectedItemsPerMonkey);
	}

	@NotNull
	private static Long calculateMonkeyBusiness(Map<Integer, Long> numberOfInspectedItemsPerMonkey) {
		return numberOfInspectedItemsPerMonkey.values().stream()
				.sorted(Comparator.reverseOrder())
				.limit(2)
				.reduce((i1, i2) -> i1 * i2)
				.orElse(0L);
	}
}