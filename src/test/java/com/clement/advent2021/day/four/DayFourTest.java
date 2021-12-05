package com.clement.advent2021.day.four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayFourTest {
	private static final Logger log = LoggerFactory.getLogger(DayFourTest.class);

	private List<Double> getDrawings() throws IOException {
		return Stream.of(getInputsReader().readLine().split(","))
				.map(Double::parseDouble)
				.toList();
	}

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("/2021/dayFour/inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	private List<Array2DRowRealMatrix> getBoards() throws IOException {
		BufferedReader inputsReader = getInputsReader();
		inputsReader.readLine();
		inputsReader.readLine();

		List<Array2DRowRealMatrix> boards = new ArrayList<>();
		double[][] board = new double[5][5];
		String line = inputsReader.readLine();
		int i = 0;
		do {
			board[i] = Arrays.stream(line.trim().split("[ ]+")).mapToDouble(Double::parseDouble).toArray();
			if(i == 4) {
				boards.add(new Array2DRowRealMatrix(board, false));
				i = 0;
				inputsReader.skip(1L);
				board = new double[5][5];
			}
			else {
				i++;
			}
			line = inputsReader.readLine();
		} while(line != null);

		return boards;
	}

	@Test
	void part1() throws IOException {
		List<Double> drawings = getDrawings();
		List<Array2DRowRealMatrix> boards = getBoards();

		List<Double> winningScores = new ArrayList<>();
		for(int i = 5; i<drawings.size(); i++ ){
			List<Double> currentDrawings = drawings.subList(0, i);
			winningScores = boards.stream().map(board -> getScoreOrZero(board, currentDrawings)).filter(score -> score > 0).toList();

			if(!winningScores.isEmpty()) {
				log.info("answer Part1: " + winningScores.get(0) );
				break;
			}
		}

		Assertions.assertEquals(1, winningScores.size());
	}

	private double getScoreOrZero(Array2DRowRealMatrix array2DRowRealMatrix, List<Double> curentDrawings) {
		final double columnOrderScore = array2DRowRealMatrix.walkInColumnOrder(new ColumnVerifier(curentDrawings));
		if(columnOrderScore > 0) {
			return columnOrderScore;
		}
		else {
			return array2DRowRealMatrix.walkInRowOrder(new RowVerifier(curentDrawings));
		}
	}

	@Test
	void part2() throws IOException {
		List<Double> drawings = getDrawings();
		List<Array2DRowRealMatrix> boards = getBoards();

		for(int i = 5; i<drawings.size(); i++ ){
			List<Double> currentDrawings = drawings.subList(0, i);

			if(boards.size() == 1) {
				final double score = getScoreOrZero(boards.get(0), currentDrawings);
				if (score > 0) {
					log.info("answer Part2: " + score);
					return;
				}
			}

			boards = boards.stream().filter(board -> getScoreOrZero(board, currentDrawings) == 0 ).toList();

		}

		Assertions.fail("Error");
	}

}
