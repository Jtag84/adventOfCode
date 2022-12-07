package com.clement.advent2021.day21;

public class DeterministicDie {
	private int currentValue = 1;
	private long numberOfRolls = 0;

	public DeterministicDie() {
	}

	public int roll() {
		numberOfRolls++;
		int rollValue = currentValue;

		currentValue = (currentValue + 1) % 101;
		if (currentValue == 0) {
			currentValue = 1;
		}

		return rollValue;
	}

	public long numberOfRolls() {
		return numberOfRolls;
	}
}
