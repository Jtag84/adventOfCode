package com.clement.advent2022.day.one;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayOneTest extends SolutionBase {

	@Test
	void part1() {
		Map<Integer, Integer> caloriesByElfNumber = getCaloriesByElfNumber();

		int elfNumberWithMostCalories = caloriesByElfNumber.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElseThrow(IllegalStateException::new);

		log.info("answer Part1: elf #" + elfNumberWithMostCalories + " calories: " + caloriesByElfNumber.get(elfNumberWithMostCalories));
	}

	@Test
	void part2() throws IOException {
		Map<Integer, Integer> caloriesByElfNumber = getCaloriesByElfNumber();

		int top3CalorieSum= caloriesByElfNumber.values().stream()
				.sorted(Comparator.reverseOrder())
				.limit(3)
				.mapToInt(i -> i)
				.sum();

		log.info("answer Part2: top3CalorieSum = " + top3CalorieSum);
	}

	private Map<Integer, Integer> getCaloriesByElfNumber() {
		List<String> calorieLines = getInputsReader().lines().toList();

		Map<Integer, Integer> caloriesByElfNumber = new HashMap<>();

		int currentElf = 1;
		int currentCalorieCount = 0;
		for(String calorieString: calorieLines) {
			if(Strings.isBlank(calorieString)) {
				caloriesByElfNumber.put(currentElf, currentCalorieCount);
				currentCalorieCount=0;
				currentElf++;
			}
			else {
				currentCalorieCount += Integer.parseInt(calorieString);
			}
		}
		caloriesByElfNumber.put(currentElf, currentCalorieCount);
		return caloriesByElfNumber;
	}
}