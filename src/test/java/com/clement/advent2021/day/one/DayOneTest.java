package com.clement.advent2021.day.one;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayOneTest extends SolutionBase {
	List<Long> inputs = getInputsReader().lines()
			.map(Long::parseLong)
			.toList();

	@Test
	void part1() {
		int numberOfincrease = 0;
		for (int i = 1; i < inputs.size(); i++) {
			if (inputs.get(i) - inputs.get(i - 1) > 0) {
				numberOfincrease++;
			}
		}
		log.info("Anwswer part 1: " + numberOfincrease);
	}

	@Test
	void part2(){
		int numberOfincrease = 0;
		long previousSum = inputs.get(0) + inputs.get(1) + inputs.get(2);
		for(int i = 3; i <inputs.size(); i++) {
			long currentSum = inputs.get(i) + inputs.get(i-1) + inputs.get(i-2);
			if(currentSum > previousSum) {
				numberOfincrease++;
			}
			previousSum = currentSum;
		}
		log.info("Anwswer part 2: " + numberOfincrease);
	}
}
