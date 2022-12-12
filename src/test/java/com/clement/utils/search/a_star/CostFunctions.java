package com.clement.utils.search.a_star;

public class CostFunctions {
	public static int toNodeValueCost(Array2dNode from, Array2dNode to) {
		return to.getValue();
	}

	public static int simpleCost(Array2dNode from, Array2dNode to) {
		return 1;
	}
}
