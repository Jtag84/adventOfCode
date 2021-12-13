package com.clement.advent2021.day.thirteen;

import static java.util.Comparator.naturalOrder;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayThirteenTest extends SolutionBase {
	@Test
	void part1() throws IOException {

		Set<Pair<Long, Long>> dots = getDots();

		List<Function<Pair<Long, Long>, Pair<Long, Long>>> foldFunctions = getFoldFunctions();

		long dotCount = dots.stream().map(foldFunctions.get(0)).distinct().count();

		log.info("answer Part1: " + dotCount);
	}

	@Test
	void part2() throws IOException {

		Set<Pair<Long, Long>> dots = getDots();

		List<Function<Pair<Long, Long>, Pair<Long, Long>>> foldFunctions = getFoldFunctions();

		Set<Pair<Long, Long>> foldedDots = foldFunctions.stream().reduce(
				dots,
				(currentDots, foldFunction) -> currentDots.stream()
						.map(foldFunction)
						.collect(Collectors.toSet()),
				(previousSet, newSet) -> newSet);

		Map<Long, Set<Long>> dotLines = foldedDots.stream().collect(groupingBy(Pair<Long, Long>::getRight, Collectors.mapping(Pair<Long, Long>::getLeft, Collectors.toSet())));
		long maxLine = dotLines.keySet().stream().max(naturalOrder()).orElse(0L);

		log.info("answer Part2: ");
		for (long i = 0; i <= maxLine; i++) {
			Set<Long> dotXCoordinates = dotLines.getOrDefault(i, Collections.emptySet());
			long maxLineLenght = dotXCoordinates.stream().max(naturalOrder()).orElse(0L);

			String line = LongStream.rangeClosed(0, maxLineLenght)
					.mapToObj(yCharIndex -> dotXCoordinates.contains(yCharIndex) ? "#" : " ")
					.collect(Collectors.joining());

			log.info(line);
		}
	}

	private Set<Pair<Long, Long>> getDots() {
		Set<Pair<Long, Long>> dots = getInputsReader().lines()
				.takeWhile(not(String::isBlank))
				.map(line -> line.split(","))
				.map(xAndY -> Pair.of(Long.parseLong(xAndY[0]), Long.parseLong(xAndY[1])))
				.collect(Collectors.toSet());
		return dots;
	}

	private List<Function<Pair<Long, Long>, Pair<Long, Long>>> getFoldFunctions() {
		List<Function<Pair<Long, Long>, Pair<Long, Long>>> foldFunctions = getInputsReader().lines()
				.dropWhile(line -> !line.startsWith("fold along"))
				.map(this::toFoldFunction)
				.toList();
		return foldFunctions;
	}

	private Function<Pair<Long, Long>, Pair<Long, Long>> toFoldFunction(String line) {
		String[] xyCoordinate = line.split("=");
		long foldLine = Long.parseLong(xyCoordinate[1]);
		if (xyCoordinate[0].charAt(11) == 'x') {
			return (xy) -> foldFunction(xy, Pair::getLeft, (pair, xValue) -> Pair.of(xValue, pair.getRight()), foldLine);
		} else {
			return (xy) -> foldFunction(xy, Pair::getRight, (pair, yValue) -> Pair.of(pair.getLeft(), yValue), foldLine);
		}
	}

	private Pair<Long, Long> foldFunction(Pair<Long, Long> xy, Function<Pair<Long, Long>, Long> getter, BiFunction<Pair<Long, Long>, Long, Pair<Long, Long>> setter, Long foldLine) {
		Long axisToFold = getter.apply(xy);
		if (axisToFold > foldLine) {
			return setter.apply(xy, 2 * foldLine - axisToFold);
		} else {
			return xy;
		}
	}
}