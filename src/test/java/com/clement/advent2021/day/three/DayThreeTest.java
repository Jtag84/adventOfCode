package com.clement.advent2021.day.three;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Streams;

class DayThreeTest extends SolutionBase {

	@Test
	void part1() {
		int gamma = getInputsReader().lines()
				.map(String::chars)
				.map(intStream -> intStream.mapToObj(intChar -> intChar - '0'))
				.reduce(Stream.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
						(countOfOnes, binaryValue) -> Streams.zip(countOfOnes, binaryValue, Integer::sum),
						(previousCount, newCount) -> newCount)
				.map(numberOfOnes -> numberOfOnes > 500 ? 1 : 0)
				.reduce(0, (previousBinaryDigit, newBinaryDigit) -> (previousBinaryDigit << 1) + newBinaryDigit);

		int epsilon = 0b111111111111 ^ gamma;

		log.info("answer Part1: " + (gamma * epsilon));
	}

	@Test
	void part2(){
		int initialMask = 0b100000000000;
		Map<Integer, List<Integer>> split0andOnesMap = getInputsReader().lines()
				.map(binaryString -> Integer.parseInt(binaryString, 2))
				.collect(Collectors.groupingBy(binaryInt -> binaryInt & initialMask));

		List<Integer> oxygenValueList;
		List<Integer> co2ValueList;
		if(split0andOnesMap.get(0).size() > split0andOnesMap.get(initialMask).size()) {
			oxygenValueList = split0andOnesMap.get(0);
			co2ValueList = split0andOnesMap.get(initialMask);
		}
		else {
			oxygenValueList = split0andOnesMap.get(initialMask);
			co2ValueList = split0andOnesMap.get(0);
		}

		for(int i=10; i>=0; i--) {
			int mask = (1 << i);

			if(oxygenValueList.size() > 1) {
				oxygenValueList = getOxygenRemainingList(oxygenValueList, mask);
			}

			if(co2ValueList.size()>1) {
				co2ValueList = getCo2RemainingList(co2ValueList, mask);
			}
		}

		Assertions.assertEquals(1, oxygenValueList.size());
		Assertions.assertEquals(1, co2ValueList.size());

		log.info("answer Part2: " + (oxygenValueList.get(0) * co2ValueList.get(0)));
	}

	private List<Integer> getOxygenRemainingList(List<Integer> oxygenValueList, int mask) {
		Map<Integer, List<Integer>> oxygenSplitMap = get1and0splitListsWithMask(oxygenValueList, mask);
		if(oxygenSplitMap.keySet().size() == 1) {
			return oxygenValueList;
		}

		if(oxygenSplitMap.get(0).size() > oxygenSplitMap.get(mask).size()) {
			return oxygenSplitMap.get(0);
		}
		else {
			return oxygenSplitMap.get(mask);
		}
	}

	private List<Integer> getCo2RemainingList(List<Integer> co2ValueList, int mask) {
		Map<Integer, List<Integer>> co2SplitMap = get1and0splitListsWithMask(co2ValueList, mask);
		if(co2SplitMap.keySet().size() == 1) {
			return co2ValueList;
		}

		if(co2SplitMap.get(0).size() <= co2SplitMap.get(mask).size()) {
			return co2SplitMap.get(0);
		}
		else {
			return co2SplitMap.get(mask);
		}
	}

	private Map<Integer, List<Integer>> get1and0splitListsWithMask(List<Integer> list, int mask) {
		return list.stream().collect(Collectors.groupingBy(binaryInt -> binaryInt & mask));
	}
}
