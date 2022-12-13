package com.clement.advent2022.day13;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Streams;

public record Packet(List<PacketElement> elements) implements PacketElement {

	public static Packet of(PacketElement packetElement) {
		return new Packet(List.of(packetElement));
	}

	public static Packet parse(String packetString) {
		List<PacketElement> elements = new ArrayList<>();

		for (int i = 1; i < packetString.length() - 1; ) {
			char currentChar = packetString.charAt(i);
			if (currentChar == '[') {
				int matchingClosingBracketIndex = findMatchingClosingBracketIndex(packetString, i);
				elements.add(Packet.parse(packetString.substring(i, matchingClosingBracketIndex + 1)));
				i = matchingClosingBracketIndex + 2;
			} else {
				int nextComma = packetString.indexOf(',', i);
				String packetElementIntString;
				if (nextComma == -1) {
					packetElementIntString = packetString.substring(i, packetString.length() - 1);
				} else {
					packetElementIntString = packetString.substring(i, nextComma);
				}

				elements.add(PacketElementInt.parse(packetElementIntString));
				i += packetElementIntString.length() + 1;
			}
		}

		return new Packet(elements);
	}

	private static int findMatchingClosingBracketIndex(String packetString, int i) {
		int parenthesisCount = 1;
		int parenthesisIndex = i;
		while (parenthesisCount > 0) {
			parenthesisIndex++;
			char currentChar = packetString.charAt(parenthesisIndex);
			if (currentChar == '[') {
				parenthesisCount++;
			} else if (currentChar == ']') {
				parenthesisCount--;
			}
		}

		return parenthesisIndex;
	}

	@Override
	public int compareTo(@NotNull PacketElement other) {
		if (other instanceof PacketElementInt) {
			return this.compareTo(Packet.of(other));
		}

		return Streams.zip(this.elements.stream(), ((Packet) other).elements.stream(), Comparable::compareTo)
				.filter(comp -> comp != 0)
				.findFirst()
				.orElseGet(() -> this.elements.size() - ((Packet) other).elements.size());
	}
}
