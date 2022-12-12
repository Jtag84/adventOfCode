package com.clement.utils.search.a_star;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;

public abstract class Node implements Comparable<Node> {

	protected int fScore = Integer.MAX_VALUE;

	public abstract Collection<? extends Node> getNeighbors();

	public abstract int distanceTo(Node other);

	public Node withFScore(int fScore) {
		this.fScore = fScore;
		return this;
	}

	@Override
	public int compareTo(@NotNull Node other) {
		return this.fScore - other.fScore;
	}
}
