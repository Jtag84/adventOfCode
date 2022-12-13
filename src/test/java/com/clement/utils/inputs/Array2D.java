package com.clement.utils.inputs;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Streams;

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

	private Iterator<Pair<Pair<Integer, Integer>, Integer>> getCoordinateValueRowIteratorFromLeft(int rowIndex) {
		return new Iterator<>() {
			private int currentRowElementIndex = 0;

			@Override
			public boolean hasNext() {
				return currentRowElementIndex <= getMaxX();
			}

			@Override
			public Pair<Pair<Integer, Integer>, Integer> next() {
				Pair<Pair<Integer, Integer>, Integer> coordinateValuePair = Pair.of(Pair.of(currentRowElementIndex, rowIndex), get(currentRowElementIndex, rowIndex));
				currentRowElementIndex++;
				return coordinateValuePair;
			}
		};
	}

	private Iterator<Pair<Pair<Integer, Integer>, Integer>> getCoordinateValueRowIteratorFromRight(int rowIndex) {
		return new Iterator<>() {
			private int currentRowElementIndex = getMaxX();

			@Override
			public boolean hasNext() {
				return currentRowElementIndex >= 0;
			}

			@Override
			public Pair<Pair<Integer, Integer>, Integer> next() {
				Pair<Pair<Integer, Integer>, Integer> coordinateValuePair = Pair.of(Pair.of(currentRowElementIndex, rowIndex), get(currentRowElementIndex, rowIndex));
				currentRowElementIndex--;
				return coordinateValuePair;
			}
		};
	}

	private Iterator<Pair<Pair<Integer, Integer>, Integer>> getCoordinateValueColumnIteratorFromTop(int columnIndex) {
		return new Iterator<>() {
			private int currentColumnElementIndex = 0;

			@Override
			public boolean hasNext() {
				return currentColumnElementIndex <= getMaxY();
			}

			@Override
			public Pair<Pair<Integer, Integer>, Integer> next() {
				Pair<Pair<Integer, Integer>, Integer> coordinateValuePair = Pair.of(Pair.of(columnIndex, currentColumnElementIndex), get(columnIndex, currentColumnElementIndex));
				currentColumnElementIndex++;
				return coordinateValuePair;
			}
		};
	}

	private Iterator<Pair<Pair<Integer, Integer>, Integer>> getCoordinateValueColumnIteratorFromBottom(int columnIndex) {
		return new Iterator<>() {
			private int currentColumnElementIndex = getMaxY();

			@Override
			public boolean hasNext() {
				return currentColumnElementIndex >= 0;
			}

			@Override
			public Pair<Pair<Integer, Integer>, Integer> next() {
				Pair<Pair<Integer, Integer>, Integer> coordinateValuePair = Pair.of(Pair.of(columnIndex, currentColumnElementIndex), get(columnIndex, currentColumnElementIndex));
				currentColumnElementIndex--;
				return coordinateValuePair;
			}
		};
	}

	public Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> getCoordinateValueRowFromLeftIterators() {
		return toIterable(IntStream.rangeClosed(0, getMaxY()).mapToObj(this::getCoordinateValueRowIteratorFromLeft).map(Array2D::toIterable).iterator());
	}

	public Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> getCoordinateValueRowFromRightIterators() {
		return toIterable(IntStream.rangeClosed(0, getMaxY()).mapToObj(this::getCoordinateValueRowIteratorFromRight).map(Array2D::toIterable).iterator());
	}

	public Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> getCoordinateValueColumnFromTopIterators() {
		return toIterable(IntStream.rangeClosed(0, getMaxX()).mapToObj(this::getCoordinateValueColumnIteratorFromTop).map(Array2D::toIterable).iterator());
	}

	public Iterable<Iterable<Pair<Pair<Integer, Integer>, Integer>>> getCoordinateValueColumnFromBottomIterators() {
		return toIterable(IntStream.rangeClosed(0, getMaxX()).mapToObj(this::getCoordinateValueColumnIteratorFromBottom).map(Array2D::toIterable).iterator());
	}

	private static <T> Iterable<T> toIterable(Iterator<T> iterator) {
		return () -> iterator;
	}

	public Stream<Pair<Pair<Integer, Integer>, Integer>> streamByRowFromTopLeft() {
		return Streams.stream(getCoordinateValueRowFromLeftIterators())
				.flatMap(Streams::stream);
	}

	public Optional<Pair<Integer, Integer>> findFirstCoordinatesByValue(int value) {
		return findCoordinatesByValueStream(value).findFirst();
	}

	@NotNull
	public Stream<Pair<Integer, Integer>> findCoordinatesByValueStream(int value) {
		return streamByRowFromTopLeft()
				.filter(coordinateValuePair -> coordinateValuePair.getRight() == value)
				.map(Pair::getLeft);
	}

	public void set(Pair<Integer, Integer> xYcoordinates, int value) {
		this.array2d[xYcoordinates.getLeft()][xYcoordinates.getRight()] = value;
	}
}
