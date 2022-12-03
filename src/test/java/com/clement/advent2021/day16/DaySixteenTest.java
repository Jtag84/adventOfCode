package com.clement.advent2021.day16;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DaySixteenTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		Iterator<Long> bitIterator = getBitIterator();

		Packet packet = Packet.decodePacket(bitIterator);

		long summedVersions = getAllPackets(packet).stream().mapToLong(p -> p.version).sum();
		log.info("answer Part1: " + summedVersions);
	}

	@Test
	void part2() throws IOException {
		Iterator<Long> bitIterator = getBitIterator();

		Packet packet = Packet.decodePacket(bitIterator);

		log.info("answer Part2: " + ((PacketOperator) packet).executeOperation());
	}

	private Iterator<Long> getBitIterator() throws IOException {
		Iterator<Long> bitIterator = getInputsReader().readLine().chars()
				.mapToLong(c -> Long.parseLong("1" + (char) c, 16))
				.mapToObj(Long::toBinaryString)
				.flatMapToLong(binary -> binary.chars().skip(1).asLongStream())
				.map(b -> b - '0')
				.iterator();
		return bitIterator;
	}

	private List<Packet> getAllPackets(Packet packet) {
		if (packet instanceof PacketOperator) {
			List<Packet> subPackets = ((PacketOperator) packet).subPackets.stream()
					.map(this::getAllPackets)
					.flatMap(List::stream)
					.toList();
			return ListUtils.union(singletonList(packet), subPackets);
		} else {
			return singletonList(packet);
		}
	}

}