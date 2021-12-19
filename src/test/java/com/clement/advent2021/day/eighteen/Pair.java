package com.clement.advent2021.day.eighteen;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Triple;

public record Pair(PairElement left, PairElement right) implements PairElement {

	public static Pair of(PairElement left, PairElement right) {
		return new Pair(left, right);
	}

	@Override
	public String toString() {
		return "[" + left.toString() + "," + right.toString() + "]";
	}

	public PairElement reduce() {
		PairElement previousPair;
		PairElement newPair = this;
		do {
			do {
				previousPair = newPair;
				newPair = newPair.explode(0, 0, 0).getLeft();
			} while (!newPair.equals(previousPair));

			newPair = newPair.split();
		} while (!newPair.equals(previousPair));

		return newPair;
	}

	@Override
	public Triple<PairElement, Integer, Integer> explode(int addFromLeft, int addFromRight, int nestLevel) {
		if (addFromLeft < 0 && addFromRight == 0 || addFromRight < 0 && addFromLeft == 0) {
			return Triple.of(this, -1, -1);
		}

		boolean hasApairExploded = addFromRight != 0 || addFromLeft != 0;
		if (nestLevel == 4 && !hasApairExploded) {
			if (left instanceof PairElementNumber && right instanceof PairElementNumber) {
				return explode(this);
			} else {
				throw new IllegalStateException();
			}
		} else if (hasApairExploded) {
			if (addFromLeft > 0) {
				Triple<PairElement, Integer, Integer> leftReduce = left.explode(addFromLeft, 0, nestLevel + 1);
				Triple<PairElement, Integer, Integer> rightReduce = right.explode(leftReduce.getRight(), 0, nestLevel + 1);
				return getResultOfLeftRightReductions(leftReduce, rightReduce);
			} else {
				Triple<PairElement, Integer, Integer> rightReduce = right.explode(0, addFromRight, nestLevel + 1);
				Triple<PairElement, Integer, Integer> leftReduce = left.explode(0, rightReduce.getMiddle(), nestLevel + 1);
				return getResultOfLeftRightReductions(leftReduce, rightReduce);
			}
		} else {
			Triple<PairElement, Integer, Integer> leftReduce = left.explode(0, 0, nestLevel + 1);
			Triple<PairElement, Integer, Integer> rightReduce = right.explode(leftReduce.getRight(), 0, nestLevel + 1);
			if (rightReduce.getMiddle() > 0) {
				leftReduce = leftReduce.getLeft().explode(0, rightReduce.getMiddle(), nestLevel + 1);
			}
			return getResultOfLeftRightReductions(leftReduce, rightReduce);
		}
	}

	@Override
	public PairElement split() {
		PairElement left = left();
		PairElement splitLeft = left.split();
		PairElement splitRight = right();
		if (left.equals(splitLeft)) {
			splitRight = splitRight.split();
		}
		return Pair.of(splitLeft, splitRight);
	}

	@Override
	public long getMagnitude() {
		return 3 * left.getMagnitude() + 2 * right.getMagnitude();
	}

	private Triple<PairElement, Integer, Integer> getResultOfLeftRightReductions(Triple<PairElement, Integer, Integer> leftReduce, Triple<PairElement, Integer, Integer> rightReduce) {
		Integer addToLeft = leftReduce.getMiddle();
		Integer addToRight = rightReduce.getRight();

		if (addToLeft < 0 && addToRight == 0) {
			addToRight = -1;
		}
		if (addToRight < 0 && addToLeft == 0) {
			addToLeft = -1;
		}
		return Triple.of(
				Pair.of(leftReduce.getLeft(), rightReduce.getLeft()),
				addToLeft,
				addToRight
		);
	}

	private Triple<PairElement, Integer, Integer> explode(Pair pair) {
		PairElementNumber left = (PairElementNumber) pair.left;
		PairElementNumber right = (PairElementNumber) pair.right;
		int addToLeft = left.number();
		int addToRight = right.number();
		if (addToLeft == 0) {
			addToLeft = -1;
		}
		if (addToRight == 0) {
			addToRight = -1;
		}
		return Triple.of(PairElementNumber.of(0), addToLeft, addToRight);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Pair pair = (Pair) o;

		return new EqualsBuilder().append(left, pair.left).append(right, pair.right).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(left).append(right).toHashCode();
	}
}
