package com.clement.advent2021.day16;

import java.util.Iterator;

public class PacketLiteral extends Packet {

	public final long value;

	public PacketLiteral(long version, long typeId, long value, long length) {
		super(version, typeId, length);
		this.value = value;
	}

	public static PacketLiteral decodeLiteralPacket(long version, long typeId, Iterator<Long> bitIterator) {
		long value = 0;
		long length = 6;
		long next5bits = 0;
		do {
			next5bits = getNextValueOfNbits(bitIterator, 5);
			length += 5;
			value = (value << 4) + (next5bits & 0b01111);
		} while ((next5bits & 0b010000) > 0);
		return new PacketLiteral(version, typeId, value, length);
	}
}
