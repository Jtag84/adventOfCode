package com.clement.utils.inputs;

public class DynamicArray2D extends Array2D {
	public DynamicArray2D(int[][] initialArray2d) {
		super(initialArray2d);
	}

	@Override
	public int get(int x, int y) {
		int lengthX = super.getMaxX() + 1;
		int lengthY = super.getMaxY() + 1;

		int xIncrement = x / lengthX;
		int yIncrement = y / lengthY;

		int initialArrayX = x % lengthX;
		int initialArrayY = y % lengthY;

		int initialArrayResult = super.get(initialArrayX, initialArrayY);

		int result = (initialArrayResult + xIncrement + yIncrement) % 9;
		return result == 0 ? 9 : result;
	}

	@Override
	public int getMaxX() {
		return super.getMaxX() * 5 + 4;
	}

	@Override
	public int getMaxY() {
		return super.getMaxY() * 5 + 4;
	}

	public static DynamicArray2D from(Array2D array2D) {
		return new DynamicArray2D(array2D.array2d);
	}


}
