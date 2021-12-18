package com.clement.advent2021.day.seventeen;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DaySeventeenTest extends SolutionBase {

	int minX = 150;
	int maxX = 171;
	int minY = -70;
	int maxY = -129;

	@Test
	void part1() throws IOException {
		int highestY = Math.abs(maxY) - 1;
		int maxHeight = highestY * (highestY + 1) / 2;

		log.info("answer Part1: " + maxHeight);
	}

	@Test
	void part2() throws IOException {
		int highestY = Math.abs(maxY) - 1;
		int count = 0;
		for (int startVX = 0; startVX <= maxX * 100; startVX++) {
			for (int startVY = maxY; startVY <= highestY; startVY++) {
				int x = 0, y = 0;
				int vx = startVX;
				int vy = startVY;

				while (x <= maxX && y >= maxY) {
					x += vx;
					y += vy;
					if (vx > 0) {
						vx--;
					}
					vy--;
					if (x >= minX && x <= maxX && y <= minY && y >= maxY) {
						count++;
						break;
					}
				}
			}
		}
		log.info("answer Part2: " + count);
	}
}
