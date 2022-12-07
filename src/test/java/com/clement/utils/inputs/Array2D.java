package com.clement.utils.inputs;

import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

public class Array2D {

	protected final int[][] array2d;

	public Array2D(int[][] array2d) {
		this.array2d = array2d;
	}

	public int get(int x, int y) {
		return array2d[x][y];
	}

	public int getMaxX() {
		return this.array2d.length - 1;
	}

	public int getMaxY() {
		return this.array2d[0].length - 1;
	}

	public static Array2D parseInputs(Stream<String> inputStream, IntUnaryOperator characterConverter) {
		int[][] array2d = inputStream.map(String::chars)
				.map(characters -> characters.map(characterConverter).toArray())
				.toArray(int[][]::new);

		return new Array2D(transposeArray(array2d));
	}

	public static int[][] transposeArray(int[][] array) {
		int rows = array.length;
		int cols = array[0].length;

		int[][] transposedArray = new int[cols][rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				transposedArray[j][i] = array[i][j];
			}
		}

		return transposedArray;
	}

}
