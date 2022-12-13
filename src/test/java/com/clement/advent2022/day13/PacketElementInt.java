package com.clement.advent2022.day13;

import org.jetbrains.annotations.NotNull;

public record PacketElementInt(int value) implements PacketElement {

	@Override
	public int compareTo(@NotNull PacketElement other) {
		if (other instanceof Packet) {
			return Packet.of(this).compareTo(other);
		}

		return this.value - ((PacketElementInt) other).value;
	}

	public static PacketElementInt parse(String packetElementString) {
		return new PacketElementInt(Integer.parseInt(packetElementString));
	}
}
