package com.clement.advent2022.day03;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Sets;

class Solution extends SolutionBase {

	@Test
	void part1() {
		int prioritySum = getInputsReader().lines()
				.map(this::getCompartmentTypes)
				.map(compartments -> Sets.intersection(compartments.getLeft(), compartments.getRight()))
				.mapToInt(this::getTypePrioritiesValue)
				.sum();

		log.info("answer Part1: prioritySum = " + prioritySum);
	}

	@Test
	void part2() throws IOException {
		List<List<String>> lineGroups = ListUtils.partition(getInputsReader().lines().toList(), 3);
		int prioritySum = lineGroups.stream()
				.map(lineGroup -> lineGroup.stream().map(line -> this.getTypesFromCompartment(line.toCharArray())).toList())
				.map(rucksaCk -> Sets.intersection(Sets.intersection(rucksaCk.get(0), rucksaCk.get(1)), rucksaCk.get(2)))
				.mapToInt(this::getTypePrioritiesValue)
				.sum();

		log.info("answer Part2: prioritySum = " + prioritySum);
	}

	private Pair<Set<Character>, Set<Character>> getCompartmentTypes(String line) {
		int splitIndex = line.length() / 2;
		char[] leftCompartment = line.substring(0, splitIndex).toCharArray();
		char[] rightCompartment = line.substring(splitIndex).toCharArray();

		return Pair.of(getTypesFromCompartment(leftCompartment), getTypesFromCompartment(rightCompartment));
	}

	private Set<Character> getTypesFromCompartment(char[] compartment) {
		Set<Character> typesInCompartment = new HashSet<>();
		for(char type: compartment){
			typesInCompartment.add(Character.valueOf(type));
		}
		return typesInCompartment;
	}

	private int getTypePrioritiesValue(Set<Character> sharedTypes) {
		return sharedTypes.stream()
				.mapToInt(c -> {
					if(c >= 'A' && c<='Z') {
						return c - 'A' + 27;
					} else {
						return c - 'a' + 1;
					}
				})
				.sum();
	}
}