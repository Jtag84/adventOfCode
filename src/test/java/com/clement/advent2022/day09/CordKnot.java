package com.clement.advent2022.day09;

public record CordKnot(int x, int y) {
	public CordKnot moveUp() {
		return new CordKnot(x, y + 1);
	}

	public CordKnot moveDown() {
		return new CordKnot(x, y - 1);
	}

	public CordKnot moveLeft() {
		return new CordKnot(x - 1, y);
	}

	public CordKnot moveRight() {
		return new CordKnot(x + 1, y);
	}

	public CordKnot follow(CordKnot head) {
		int xDiff = head.x - this.x;
		int yDiff = head.y - this.y;

		if (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1) {
			return new CordKnot((int) (x + Math.signum(xDiff)), (int) (y + Math.signum(yDiff)));
		} else {
			return this;
		}
	}

}
