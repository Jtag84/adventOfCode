package com.clement.advent2021.day16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.LongStream;

public class PacketOperator extends Packet {

	public final List<Packet> subPackets;

	public PacketOperator(long version, long typeId, long length, List<Packet> subPackets) {
		super(version, typeId, length);
		this.subPackets = subPackets;
	}

	public static PacketOperator decodeOperatorPacket(long version, long typeId, Iterator<Long> bitIterator) {
		long length = 6;

		boolean is11bitsNumberOfSubPAcket = getNextValueOfNbits(bitIterator, 1) == 1;
		length++;

		List<Packet> subPackets = new ArrayList<>();
		if (is11bitsNumberOfSubPAcket) {
			long numberOfSubPacket = getNextValueOfNbits(bitIterator, 11);
			length += 11;
			subPackets = LongStream.rangeClosed(1, numberOfSubPacket).mapToObj(i -> Packet.decodePacket(bitIterator)).toList();
			length += subPackets.stream().mapToLong(p -> p.length).sum();
		} else {
			long subPacketLength = getNextValueOfNbits(bitIterator, 15);
			length += 15;
			length += subPacketLength;

			int lengthOfSubPacketsRetrieved = 0;
			while (lengthOfSubPacketsRetrieved < subPacketLength) {
				Packet subPacket = Packet.decodePacket(bitIterator);
				subPackets.add(subPacket);
				lengthOfSubPacketsRetrieved += subPacket.length;
			}

			if (lengthOfSubPacketsRetrieved > subPacketLength) {
				throw new IllegalStateException();
			}
		}

		return new PacketOperator(version, typeId, length, subPackets);
	}

	public long executeOperation() {
		final LongStream packetsExecutedStream = subPackets.stream().mapToLong(this::executeSubPacket);
		return switch ((int) typeId) {
			case 0 -> packetsExecutedStream.sum();
			case 1 -> packetsExecutedStream.reduce(1, (p1, p2) -> p1 * p2);
			case 2 -> packetsExecutedStream.min().getAsLong();
			case 3 -> packetsExecutedStream.max().getAsLong();
			case 5 -> packetsExecutedStream.reduce((p1, p2) -> (p1 > p2) ? 1 : 0).getAsLong();
			case 6 -> packetsExecutedStream.reduce((p1, p2) -> (p1 < p2) ? 1 : 0).getAsLong();
			case 7 -> packetsExecutedStream.reduce((p1, p2) -> (p1 == p2) ? 1 : 0).getAsLong();
			default -> throw new IllegalStateException("Unexpected value: " + typeId);
		};
	}

	private long executeSubPacket(Packet packet) {
		if (packet instanceof PacketOperator) {
			return ((PacketOperator) packet).executeOperation();
		} else {
			return ((PacketLiteral) packet).value;
		}
	}
}
