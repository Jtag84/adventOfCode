package com.clement.advent2021.day18;

import org.apache.commons.lang3.tuple.Triple;

public interface PairElement {
	Triple<PairElement, Integer, Integer> explode(int addFromLeft, int addFromRight, int nestLevel);

	PairElement split();

	long getMagnitude();
}
