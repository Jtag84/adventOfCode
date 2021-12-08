package com.clement.advent2020.day.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayTwoTest {
	private static final Logger log = LoggerFactory.getLogger(DayTwoTest.class);

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	@Test
	void part1() throws IOException {
		long numberOfValidPasswords = getPasswordLineFields()
				.filter(this::isPasswordValidPart1)
				.count();

		log.info("answer Part1: " + numberOfValidPasswords);
	}

	private Stream<String[]> getPasswordLineFields() {
		return getInputsReader().lines()
				.map(line -> line.replaceAll("-", " "))
				.map(line -> line.replaceAll(":", ""))
				.map(line -> line.split(" "));
	}

	private boolean isPasswordValidPart1(String[] passwordLineFields) {
		long minCharacterOccurence = Long.parseLong(passwordLineFields[0]);
		long maxCharacterOccurence = Long.parseLong(passwordLineFields[1]);
		char characterToCheck = passwordLineFields[2].charAt(0);
		String password = passwordLineFields[3];

		long characterOccurence = password.chars().filter(currentCharacter -> currentCharacter == characterToCheck).count();
		return characterOccurence >= minCharacterOccurence && characterOccurence <= maxCharacterOccurence;
	}

	@Test
	void part2() throws IOException {
		long numberOfValidPasswords = getPasswordLineFields()
				.filter(this::isPasswordValidPart2)
				.count();

		log.info("answer Part2: " + numberOfValidPasswords);
	}

	private boolean isPasswordValidPart2(String[] passwordLineFields) {
		int firstPositionCharacterOccurence = Integer.parseInt(passwordLineFields[0]) - 1;
		int secondPositionCharacterOccurence = Integer.parseInt(passwordLineFields[1]) - 1;
		char characterToCheck = passwordLineFields[2].charAt(0);
		String password = passwordLineFields[3];

		return (password.charAt(firstPositionCharacterOccurence) == characterToCheck) ^ (password.charAt(secondPositionCharacterOccurence) == characterToCheck);
	}
}
