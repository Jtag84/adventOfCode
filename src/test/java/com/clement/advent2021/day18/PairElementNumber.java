package com.clement.advent2021.day18;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Triple;

public record PairElementNumber(int number) implements PairElement {

	public static PairElement of(int number) {
		return new PairElementNumber(number);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		PairElementNumber that = (PairElementNumber) o;

		return new EqualsBuilder().append(number, that.number).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(number).toHashCode();
	}

	@Override
	public String toString() {
		return "" + number;
	}

	@Override
	public Triple<PairElement, Integer, Integer> explode(int addFromLeft, int addFromRight, int nestLevel) {
		int newAddFromLeft = addFromLeft;
		int newAddFromRight = addFromRight;

		int newNumber = number;
		if (addFromLeft > 0) {
			newAddFromRight = -1;
			newAddFromLeft = -1;
			newNumber += addFromLeft;
		}
		if (newAddFromRight > 0) {
			newAddFromRight = -1;
			newAddFromLeft = -1;
			newNumber += addFromRight;
		}

		return Triple.of(PairElementNumber.of(newNumber), newAddFromLeft, newAddFromRight);
	}

	@Override
	public PairElement split() {
		if (number >= 10) {
			return Pair.of(
					PairElementNumber.of(Math.floorDiv(number, 2)),
					PairElementNumber.of(Math.floorDiv(number + 1, 2)));
		} else {
			return this;
		}
	}

	@Override
	public long getMagnitude() {
		return number;
	}
}
