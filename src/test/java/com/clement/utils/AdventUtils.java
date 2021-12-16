package com.clement.utils;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

public class AdventUtils {

	// map[y][x] |  position : (x, y) | return (x, y)
	public static Set<Pair<Integer, Integer>> getValidCoordinatesAroundWithoutDiagonals(int[][] map, Pair<Integer, Integer> position) {
		return getValidCoordinatesAround(map, position).stream()
				.filter(coordinate -> coordinate.getLeft().equals(position.getLeft()) || coordinate.getRight().equals(position.getRight()))
				.collect(toSet());
	}

	// map[y][x] |  position : (x, y) | return (x, y)
	public static Set<Pair<Integer, Integer>> getValidCoordinatesAround(int[][] map, Pair<Integer, Integer> position) {
		int x = position.getLeft();
		int y = position.getRight();
		List<Integer> validXs = getValidAxisCoordinates(x, map[0].length);
		List<Integer> validYs = getValidAxisCoordinates(y, map.length);
		return Lists.cartesianProduct(validXs, validYs).stream().map(positionXy -> Pair.of(positionXy.get(0), positionXy.get(1))).skip(1).collect(toSet());
	}

	public static List<Integer> getValidAxisCoordinates(int position, int length) {
		List<Integer> validCoordinates = new ArrayList<>();
		validCoordinates.add(position);
		if (position > 0) {
			validCoordinates.add(position - 1);
		}
		if (position < length - 1) {
			validCoordinates.add(position + 1);
		}
		return validCoordinates;
	}

	public static List<Pair<Integer, Integer>> calculateAllPointsOfLine(List<Pair<Integer, Integer>> coordinates) {
		return calculateAllPointsOfLine(coordinates.get(0), coordinates.get(1));
	}

	public static List<Pair<Integer, Integer>> calculateAllPointsOfLine(Pair<Integer, Integer> startCoordinate, Pair<Integer, Integer> endCoordinate) {
		int x1 = startCoordinate.getLeft();
		int y1 = startCoordinate.getRight();
		int x2 = endCoordinate.getLeft();
		int y2 = endCoordinate.getRight();

		double slope;
		if (x1 != x2) {
			slope = (double) (y2 - y1) / (x2 - x1);
		} else {
			slope = 0;
		}

		double offset = y1 - slope * x1;

		if (x1 == x2) {
			return IntStream.rangeClosed(Math.min(y1, y2), Math.max(y1, y2))
					.mapToObj(y -> Pair.of(x1, y))
					.collect(toCollection(ArrayList::new));
		} else {
			return IntStream.rangeClosed(Math.min(x1, x2), Math.max(x1, x2))
					.mapToObj(x -> Pair.of(x, (int) Math.round(slope * x + offset)))
					.collect(toCollection(ArrayList::new));
		}
	}
}
