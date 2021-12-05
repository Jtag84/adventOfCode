package com.clement.advent2021.day.four;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrixChangingVisitor;

public class RowVerifier implements RealMatrixChangingVisitor {

	private final List<Double> currentDrawings;
	private boolean isWinner = false;
	private boolean isRowWinner = true;
	private int sumOfUnmarkedNumbers = 0;

	public RowVerifier(List<Double> currentDrawings) {
		this.currentDrawings = currentDrawings;
	}

	@Override
	public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
		isWinner = false;
		isRowWinner = true;
		sumOfUnmarkedNumbers = 0;
	}

	@Override
	public double visit(int row, int column, double value) {
		if(!currentDrawings.contains(value)) {
			isRowWinner = false;
			sumOfUnmarkedNumbers += value;
		}

		if(column == 4) {
			if(isRowWinner) {
				isWinner = true;
			}
			else {
				isRowWinner = true;
			}
		}

		return value;
	}

	@Override
	public double end() {
		if(isWinner) {
			return sumOfUnmarkedNumbers * currentDrawings.get(currentDrawings.size() - 1);
		}
		else {
			return 0;
		}
	}
}
