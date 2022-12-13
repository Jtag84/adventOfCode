package com.clement.advent2022.day13;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.Lists;

class SolutionTest extends SolutionBase {

	@Test
	void part1Example() {
		int rightOrderIndexesSum = getRightOrderIndexesSum(getInputsExampleReader());

		log.info("answer Part1 Example: " + rightOrderIndexesSum);
		Assertions.assertEquals(13, rightOrderIndexesSum);
	}

	@Test
	void part1() {
		int rightOrderIndexesSum = getRightOrderIndexesSum(getInputsReader());

		log.info("answer Part1: " + rightOrderIndexesSum);
		Assertions.assertEquals(5330, rightOrderIndexesSum);
	}

	@Test
	void part2Example() {
		int decoderKey = getDecoderKey(getInputsExampleReader());
		log.info("answer Part2 Example: " + decoderKey);

		Assertions.assertEquals(140, decoderKey);
	}

	@Test
	void part2() {
		int decoderKey = getDecoderKey(getInputsReader());

		log.info("answer Part2: " + decoderKey);
		Assertions.assertEquals(27648, decoderKey);
	}

	private int getDecoderKey(BufferedReader bufferedReader) {
		List<Packet> packets = getPackets(bufferedReader);
		Packet packetDivider2 = Packet.parse("[[2]]");
		Packet packetDivider6 = Packet.parse("[[6]]");
		packets.add(packetDivider2);
		packets.add(packetDivider6);
		packets.sort(Comparator.naturalOrder());

		int indexDivider2 = packets.indexOf(packetDivider2);
		int indexDivider6 = packets.indexOf(packetDivider6);

		return (indexDivider2 + 1) * (indexDivider6 + 1);
	}

	private int getRightOrderIndexesSum(BufferedReader bufferedReader) {
		List<Packet> packets = getPackets(bufferedReader);

		List<Pair<Packet, Packet>> packetPairs = Lists.partition(packets, 2).stream()
				.map(packetList -> Pair.of(packetList.get(0), packetList.get(1)))
				.toList();

		int rightOrderIndexesSum = 0;
		for (int i = 0; i < packetPairs.size(); i++) {
			Pair<Packet, Packet> packetPair = packetPairs.get(i);
			if (packetPair.getLeft().compareTo(packetPair.getRight()) < 0) {
				rightOrderIndexesSum += i + 1;
			}
		}
		return rightOrderIndexesSum;
	}

	@NotNull
	private static List<Packet> getPackets(BufferedReader bufferedReader) {
		return bufferedReader.lines()
				.filter(Strings::isNotBlank)
				.map(Packet::parse)
				.collect(Collectors.toCollection(ArrayList::new));
	}
}