package com.clement.advent2022.day07;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class SolutionTest extends SolutionBase {

	public static final int TOTAL_DISK_SPACE = 70000000;
	public static final int SPACE_NEEDED_FOR_UPDATE = 30000000;

	@Test
	void part1Example() {
		int totalFolderSizesUnder100000 = getTotalFolderSizesUnder100000(getInputsExampleReader());

		log.info("answer Part1 Example: " + totalFolderSizesUnder100000);
		Assertions.assertEquals(95437, totalFolderSizesUnder100000);
	}

	@Test
	void part1() {
		int totalFolderSizesUnder100000 = getTotalFolderSizesUnder100000(getInputsReader());

		log.info("answer Part1: " + totalFolderSizesUnder100000);
	}

	@Test
	void part2Example() {
		int sizeOfSmallestDirToDelete = getSizeOfSmallestDirectoryToDelete(getInputsExampleReader());
		log.info("answer Part2Example: sizeOfSmallestDirToDelete=" + sizeOfSmallestDirToDelete);
		Assertions.assertEquals(24933642, sizeOfSmallestDirToDelete);
	}

	@Test
	void part2() {
		int sizeOfSmallestDirToDelete = getSizeOfSmallestDirectoryToDelete(getInputsReader());
		log.info("answer Part2: sizeOfSmallestDirToDelete=" + sizeOfSmallestDirToDelete);
	}

	private int getSizeOfSmallestDirectoryToDelete(BufferedReader bufferedReader) {
		parseLines(bufferedReader);

		Map<Path, Integer> directorySizes = Directory.ROOT.getDirectorySizes();

		int spaceLeft = TOTAL_DISK_SPACE - directorySizes.get(Directory.ROOT.getPath());
		int spaceNeeded = SPACE_NEEDED_FOR_UPDATE - spaceLeft;

		return directorySizes.values().stream()
				.filter(size -> size >= spaceNeeded)
				.min(Comparator.comparing(Function.identity()))
				.orElseThrow(IllegalStateException::new);
	}

	private int getTotalFolderSizesUnder100000(BufferedReader bufferedReader) {
		parseLines(bufferedReader);

		return Directory.ROOT.getDirectorySizes().values().stream()
				.filter(size -> size < 100000)
				.mapToInt(i -> i).sum();
	}

	private void parseLines(BufferedReader bufferedReader) {
		List<String> lines = bufferedReader.lines().skip(1).toList();

		parseLines(lines);
	}

	private void parseLines(List<String> lines) {
		Directory current = Directory.ROOT;

		for (String line : lines) {
			String[] lineSplit = line.split(" ");

			switch (lineSplit[0]) {
				case "$" -> {
					if (Objects.equals(lineSplit[1], "cd")) {
						String directoryName = lineSplit[2];
						if (Objects.equals(directoryName, "..")) {
							current = current.getParent();
						} else {
							current = current.getDirectory(directoryName).orElseThrow(IllegalStateException::new);
						}
					}
					// ignore ls
				}
				case "dir" -> new Directory(lineSplit[1], current);
				default -> current.addFile(new File(lineSplit[1], Integer.parseInt(lineSplit[0])));
			}
		}
	}

}