package com.clement.advent2021.day16;

import java.util.Iterator;
import java.util.stream.LongStream;

public class Packet {
	public final long version;
	public final long typeId;
	public final long length;

	public Packet(long version, long typeId, long length) {
		this.version = version;
		this.typeId = typeId;
		this.length = length;
	}

	public static Packet decodePacket(Iterator<Long> bitIterator) {

		long version = getNextValueOfNbits(bitIterator, 3);
		long typeId = getNextValueOfNbits(bitIterator, 3);

		return switch ((int) typeId) {
			case 4 -> PacketLiteral.decodeLiteralPacket(version, typeId, bitIterator);
			default -> PacketOperator.decodeOperatorPacket(version, typeId, bitIterator);
		};
	}

	public static long getNextValueOfNbits(Iterator<Long> bitIterator, int numberOfBits) {
		return LongStream.rangeClosed(1, numberOfBits)
				.map(i -> bitIterator.next())
				.reduce(0, (previousBit, newBit) -> (previousBit << 1) + newBit);
	}
}
