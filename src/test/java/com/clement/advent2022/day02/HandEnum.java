package com.clement.advent2022.day02;

enum HandEnum {
	ROCK(1),
	SCISSOR(3),
	PAPER(2);

	private final int value;

	HandEnum(int value) {
		this.value = value;
	}

	private HandEnum winsAgainst() {
		HandEnum[] allHands = HandEnum.values();
		int nextIndex = (this.ordinal() + 1) %  allHands.length;
		return allHands[nextIndex];
	}

	private HandEnum losesAgainst() {
		HandEnum[] allHands = HandEnum.values();
		int previousIndex = (this.ordinal() + allHands.length - 1) %  allHands.length;
		return allHands[previousIndex];
	}

	public HandEnum parseShouldPlayPart2(char letter) {
		return switch(letter) {
			case 'X' -> winsAgainst();
			case 'Y' -> this;
			case 'Z' -> losesAgainst();
			default -> throw new IllegalStateException("Unexpected value: " + letter);
		};
	}

	public static HandEnum parsePart1(char letter) {
		return switch(letter) {
			case 'A', 'X' -> ROCK;
			case 'B', 'Y' -> PAPER;
			case 'C', 'Z' -> SCISSOR;
			default -> throw new IllegalStateException("Unexpected value: " + letter);
		};
	}

	public int scoreAgainst(HandEnum adversaryHand) {
		if(this == adversaryHand) { // draw
			return this.value + 3;
		}
		else if (adversaryHand == winsAgainst() ) { // win
			return this.value + 6;
		}
		else {
			return this.value; // lose
		}
	}
}
