package com.clement.advent2020.day.twentyfive;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayTwentyFiveTest {
	private static final Logger log = LoggerFactory.getLogger(DayTwentyFiveTest.class);

	private final static int doorPublicKey = 18499292; //17807724;
	private final static int cardPublicKey = 8790390; //5764801;

	@Test
	void part1() throws IOException {
		long doorLoopNumber = calculateLoopNumber(doorPublicKey);

		long encryptionKey = calculateEncryptionKey(doorLoopNumber);
		log.info("answer Part1: " + encryptionKey);
	}

	private long calculateLoopNumber(long publicKey) {
		long loopNumber = 0;
		long calculatedPublicKey = 1;
		do {
			loopNumber++;
			calculatedPublicKey = (calculatedPublicKey * 7) % 20201227;
		} while (calculatedPublicKey != publicKey);
		return loopNumber;
	}

	private long calculateEncryptionKey(long doorLoopNumber) {
		long calculatedEncryptionKey = 1;
		for (long i = doorLoopNumber; i > 0; i--) {
			calculatedEncryptionKey = (calculatedEncryptionKey * cardPublicKey) % 20201227;
		}
		return calculatedEncryptionKey;
	}

	@Test
	void part2() throws IOException {

		log.info("answer Part2: " + 0);
	}
}
