package com.clement.advent2021.day.fifteen;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.function.Predicate.not;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;
import com.google.common.collect.MoreCollectors;

class DayFifteenTest extends SolutionBase {
	@Test
	void part1() throws IOException {
		Map<Pair<Integer, Integer>, Integer> map = getMap();
		final Pair<Integer, Integer> start = Pair.of(0, 0);
		final Pair<Integer, Integer> goal = map.keySet().stream().max(Comparator.comparing(Pair<Integer, Integer>::getLeft).thenComparing(Pair::getRight)).get();
		log.info("answer Part1: " + dijkstra(map, start, goal));
		log.info("answer Part1: " + aStarSearch(map, start, goal));
	}

	@Test
	void part2() throws IOException {
		Map<Pair<Integer, Integer>, Integer> map = getMapPart2();
		final Pair<Integer, Integer> start = Pair.of(0, 0);
		final Pair<Integer, Integer> goal = map.keySet().stream().max(Comparator.comparing(Pair<Integer, Integer>::getLeft).thenComparing(Pair::getRight)).get();
		// Takes 1h20min
		log.info("answer Part2: " + aStarSearch(map, start, goal));
	}

	private int dijkstra(Map<Pair<Integer, Integer>, Integer> map, Pair<Integer, Integer> start, Pair<Integer, Integer> goal) throws IOException {
		TreeSet<Pair<Integer, Pair<Integer, Integer>>> openSet = new TreeSet<>(comparing(Pair<Integer, Pair<Integer, Integer>>::getLeft).thenComparing(Pair::getRight));

		map.keySet()
				.stream()
				.filter(not(start::equals))
				.forEach(pair -> openSet.add(Pair.of(Integer.MAX_VALUE, pair)));

		openSet.add(Pair.of(0, start));

		while (!openSet.isEmpty()) {
			Pair<Integer, Pair<Integer, Integer>> current = openSet.pollFirst();

			final Pair<Integer, Integer> currentPosition = current.getRight();
			final Integer currentCost = current.getLeft();

			if (currentPosition.equals(goal)) {
				return current.getLeft();
			}

			this.getValidCoordinatesAroundWithoutDiagonals(map, currentPosition)
					.forEach(neighbor -> {
						Optional<Pair<Integer, Pair<Integer, Integer>>> optionalNeighborCost = openSet.stream().filter(costPosition -> costPosition.getRight().equals(neighbor)).collect(MoreCollectors.toOptional());
						optionalNeighborCost.ifPresent(neighborCost -> {
							int tentativeCost = currentCost + map.get(neighbor);
							if (tentativeCost < neighborCost.getLeft()) {
								openSet.remove(neighborCost);
								openSet.add(Pair.of(tentativeCost, neighborCost.getRight()));
							}
						});
					});
		}

		//error
		return -1;
	}

	private int aStarSearch(Map<Pair<Integer, Integer>, Integer> map, Pair<Integer, Integer> start, Pair<Integer, Integer> goal) {
		PriorityQueue<Pair<Integer, Integer>> openSet = new PriorityQueue<>();
		openSet.add(start);

		Map<Pair<Integer, Integer>, Integer> gScoreMap = new HashMap<>();
		gScoreMap.put(start, 0);

		Map<Pair<Integer, Integer>, Integer> fScoreMap = new HashMap<>();
		fScoreMap.put(start, calculateHeuristic(map, start, goal));

		while (!openSet.isEmpty()) {
			Pair<Integer, Integer> currentPosition = openSet.stream().map(position -> Pair.of(fScoreMap.getOrDefault(position, Integer.MAX_VALUE), position))
					.min(Comparator.comparing(Pair::getLeft))
					.map(Pair::getRight)
					.orElseThrow();

			if (currentPosition.equals(goal)) {
				return gScoreMap.get(currentPosition);
			}

			openSet.remove(currentPosition);

			this.getValidCoordinatesAroundWithoutDiagonals(map, currentPosition)
					.forEach(neighbor -> {
						int tentativeGscore = gScoreMap.getOrDefault(currentPosition, Integer.MAX_VALUE) + map.get(neighbor);

						if (tentativeGscore < gScoreMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
							gScoreMap.put(neighbor, tentativeGscore);
							int neighborFScore = tentativeGscore + calculateHeuristic(map, neighbor, goal);
							fScoreMap.put(neighbor, neighborFScore);
							openSet.add(neighbor);
						}
					});
		}

		// no path
		return -1;
	}

	private int calculateHeuristic(Map<Pair<Integer, Integer>, Integer> map, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
		return to.getLeft() - from.getLeft() + to.getRight() - from.getRight();
	}

	private Set<Pair<Integer, Integer>> getValidCoordinatesAroundWithoutDiagonals(Map<Pair<Integer, Integer>, Integer> map, Pair<Integer, Integer> position) {
		int maxXmap = map.keySet().stream().map(Pair::getLeft).max(naturalOrder()).orElseThrow();
		int maxYmap = map.keySet().stream().map(Pair::getRight).max(naturalOrder()).orElseThrow();

		int positionX = position.getLeft();
		int positionY = position.getRight();

		Set<Pair<Integer, Integer>> neighbors = new HashSet<>();
		if (positionX > 0) {
			neighbors.add(Pair.of(positionX - 1, positionY));
		}
		if (positionX < maxXmap) {
			neighbors.add(Pair.of(positionX + 1, positionY));
		}
		if (positionY > 0) {
			neighbors.add(Pair.of(positionX, positionY - 1));
		}
		if (positionY < maxYmap) {
			neighbors.add(Pair.of(positionX, positionY + 1));
		}

		return neighbors;
	}

	private Map<Pair<Integer, Integer>, Integer> getMap() throws IOException {
		Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();

		final BufferedReader reader = getInputsReader();
		String line = reader.readLine();
		int x = 0;
		while (line != null) {
			for (int y = 0; y < line.length(); y++) {
				map.put(Pair.of(x, y), line.charAt(y) - '0');
			}
			line = reader.readLine();
			x++;
		}
		return map;
	}

	private Map<Pair<Integer, Integer>, Integer> getMapPart2() throws IOException {
		Map<Pair<Integer, Integer>, Integer> mapPart1 = this.getMap();
		int mapPart1Xsize = mapPart1.keySet().stream().map(Pair::getLeft).max(naturalOrder()).orElseThrow() + 1;
		int mapPart1Ysize = mapPart1.keySet().stream().map(Pair::getRight).max(naturalOrder()).orElseThrow() + 1;

		Map<Pair<Integer, Integer>, Integer> mapPart2 = new HashMap<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				for (Map.Entry<Pair<Integer, Integer>, Integer> positionCost : mapPart1.entrySet()) {
					Pair<Integer, Integer> position = positionCost.getKey();
					int cost = positionCost.getValue();
					int newCost = (cost + i + j);
					if (newCost > 9) {
						newCost -= 9;
					}
					mapPart2.put(Pair.of(position.getLeft() + i * mapPart1Xsize, position.getRight() + j * mapPart1Ysize), newCost);
				}
			}
		}

		return mapPart2;
	}
}