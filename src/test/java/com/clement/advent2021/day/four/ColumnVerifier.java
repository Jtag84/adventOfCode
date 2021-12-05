package com.clement.advent2021.day.four;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrixChangingVisitor;

public class ColumnVerifier implements RealMatrixChangingVisitor {

	private final List<Double> currentDrawings;
	private boolean isWinner = false;
	private boolean isColumnWinner = true;
	private int sumOfUnmarkedNumbers = 0;

	public ColumnVerifier(List<Double> currentDrawings) {
		this.currentDrawings = currentDrawings;
	}

	@Override
	public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
		isWinner = false;
		isColumnWinner = true;
		sumOfUnmarkedNumbers = 0;
	}

	@Override
	public double visit(int row, int column, double value) {
		if(!currentDrawings.contains(value)) {
			isColumnWinner = false;
			sumOfUnmarkedNumbers += value;
		}

		if(row == 4) {
			if(isColumnWinner) {
				isWinner = true;
			}
			else {
				isColumnWinner = true;
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
