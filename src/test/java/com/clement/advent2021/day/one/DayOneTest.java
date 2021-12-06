package com.clement.advent2021.day.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayOneTest {
	private static final Logger log = LoggerFactory.getLogger(DayOneTest.class);

	List<Long> inputs;

	public DayOneTest() throws IOException {
		try (InputStream is = this.getClass().getResourceAsStream("inputs")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			inputs = reader.lines().map(Long::parseLong)
					.toList();
		}
	}

	@Test
	void part1(){
		int numberOfincrease = 0;
		for(int i = 1; i <inputs.size(); i++) {
			if(inputs.get(i) - inputs.get(i-1) > 0) {
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
