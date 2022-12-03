package com.clement.advent2021.day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwentyTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		String imageEnhancementAlgorithm = getInputsReader().readLine();

		List<String> imageLines = getInputsReader().lines().skip(2).toList();

		List<String> enhancedImage = enhanceTimes(imageEnhancementAlgorithm, imageLines, 2);

		long litPixels = enhancedImage.subList(0, enhancedImage.size() - 1).stream().flatMapToInt(s -> s.chars().skip(1)).filter(c -> c == '#').count();
		log.info("answer Part1: " + litPixels);
	}

	private List<String> addFrame(List<String> image, int size, boolean expand) {
		final String expandingChar;
		if (expand) {
			expandingChar = image.get(0).substring(0, 1);
		} else {
			expandingChar = ".";
		}

		String prePostFix = IntStream.range(0, size).mapToObj(i -> expandingChar).collect(Collectors.joining());
		List<String> imageLinesInput = image.stream().map(s -> prePostFix + s + prePostFix).toList();
		String extraLine = IntStream.range(0, imageLinesInput.get(0).length()).mapToObj(i -> expandingChar).collect(Collectors.joining());
		List<String> imageLines = new ArrayList<>();
		IntStream.range(0, size).forEach(i -> imageLines.add(extraLine));
		imageLines.addAll(imageLinesInput);
		IntStream.range(0, size).forEach(i -> imageLines.add(extraLine));
		return imageLines;
	}

	private List<String> enhanceTimes(String imageEnhancementAlgorithm, List<String> imageLines, int times) {
		List<String> enhancedImage = addFrame(imageLines, 10, false);
		for (int i = 0; i < times; i++) {
			enhancedImage = removeFrame(enhanceImage(imageEnhancementAlgorithm, addFrame(enhancedImage, 20, true)), 10);
		}
		return enhancedImage;
	}

	private List<String> removeFrame(List<String> image, int size) {
		return image.stream()
				.skip(size)
				.limit(image.size() - size - 1)
				.map(line -> line.substring(size, line.length() - size))
				.toList();
	}

	private List<String> enhanceImage(String imageEnhancementAlgorithm, List<String> image) {
		List<String> enhancedImage = new ArrayList<>();
		int nineBitValue = 0;
		for (int y = 0; y < image.size(); y++) {
			String newLine = "";
			for (int x = 0; x < image.get(0).length(); x++) {
				nineBitValue = getPixelValue(image, x - 1, y - 1);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x, y - 1);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x + 1, y - 1);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x - 1, y);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x, y);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x + 1, y);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x - 1, y + 1);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x, y + 1);
				nineBitValue = (nineBitValue << 1) + getPixelValue(image, x + 1, y + 1);

				newLine += imageEnhancementAlgorithm.charAt(nineBitValue);
			}
			enhancedImage.add(newLine);
		}
		return enhancedImage;
	}

	private int getPixelValue(List<String> imageLines, int x, int y) {
		if (x < 0 || x >= imageLines.get(0).length() || y < 0 | y >= imageLines.size()) {
			return 0;
		}
		return imageLines.get(y).charAt(x) == '#' ? 1 : 0;
	}

	@Test
	void part2() throws IOException {
		String imageEnhancementAlgorithm = getInputsReader().readLine();

		List<String> imageLines = getInputsReader().lines().skip(2).toList();

		List<String> enhancedImage = enhanceTimes(imageEnhancementAlgorithm, imageLines, 50);

		enhancedImage.forEach(log::info);
		long litPixels = enhancedImage.subList(0, enhancedImage.size() - 1).stream().flatMapToInt(s -> s.chars().skip(1)).filter(c -> c == '#').count();


		log.info("answer Part2: " + litPixels);
	}
}