package com.clement.advent2021.day21;

import java.util.List;

import com.google.common.collect.Lists;

public class DiracDie {
	public static List<Integer> oneRoll = List.of(1, 2, 3);
	public static List<Integer> threeRolls = Lists.cartesianProduct(oneRoll, oneRoll, oneRoll).stream()
			.map(rolls -> rolls.stream().mapToInt(i -> i).sum())
			.toList();
}
