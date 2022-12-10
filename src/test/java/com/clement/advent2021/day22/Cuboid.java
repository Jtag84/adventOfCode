package com.clement.advent2021.day22;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

public record Cuboid(Pair<Integer, Integer> xRange, Pair<Integer, Integer> yRange, Pair<Integer, Integer> zRange) {

	public long getVolume() {
		return getRangeAmount(xRange) * getRangeAmount(yRange) * getRangeAmount(zRange);
	}

	public static long getVolume(Set<Cuboid> cuboids) {
		return cuboids.parallelStream().mapToLong(Cuboid::getVolume).sum();
	}

	private long getRangeAmount(Pair<Integer, Integer> range) {
		return Math.abs(range.getRight() - range.getLeft()) + 1;
	}

	public Set<Cuboid> union(Set<Cuboid> cuboids) {
		if (CollectionUtils.isEmpty(cuboids)) {
			return Set.of(this);
		}

		if (cuboids.parallelStream().anyMatch(this::isFullyContainedWithin)) {
			return cuboids;
		}

		boolean noOverlap = cuboids.parallelStream().noneMatch(this::isOverlap);

		if (noOverlap) {
			return SetUtils.union(cuboids, Set.of(this));
		} else {
			Set<Cuboid> intersections = cuboids.parallelStream()
					.map(this::intersection)
					.filter(Predicate.not(Optional::isEmpty))
					.map(Optional::get)
					.collect(Collectors.toSet());

			return SetUtils.union(this.difference(intersections), cuboids);
		}
	}

	public Set<Cuboid> union(Cuboid cuboid) {
		if (this.isFullyContainedWithin(cuboid)) {
			return Set.of(cuboid);
		}

		if (cuboid.isFullyContainedWithin(this)) {
			return Set.of(this);
		}

		Optional<Cuboid> cuboidIntersectionOptional = intersection(cuboid);
		return cuboidIntersectionOptional
				.map(cuboidIntersection -> {
					Set<Cuboid> thisWithoutIntersection = this.difference(cuboidIntersection);
					Set<Cuboid> cuboidWithoutIntersection = cuboid.difference(cuboidIntersection);

					return (Set<Cuboid>) SetUtils.union(SetUtils.union(thisWithoutIntersection, cuboidWithoutIntersection), Set.of(cuboidIntersection));
				})
				.orElseGet(() -> {
					if (this.equals(cuboid)) {
						return Set.of(this);
					} else {
						return Set.of(cuboid, this);
					}
				});
	}

	public Set<Cuboid> difference(Set<Cuboid> cuboids) {
		return cuboids.stream()
				.reduce(Set.of(this),
						(currentDiffCuboids, cuboid) -> currentDiffCuboids.parallelStream()
								.map(currentDiff -> currentDiff.difference(cuboid))
								.flatMap(Collection::stream)
								.collect(Collectors.toSet()),
						(oldSet, newSet) -> newSet);
	}

	public Set<Cuboid> difference(Cuboid cuboid) {
		if (isFullyContainedWithin(cuboid)) {
			return Collections.emptySet();
		}

		if (!isOverlap(cuboid)) {
			return Set.of(this);
		}

		Cuboid cuboidIntersection = intersection(cuboid).orElseThrow(IllegalStateException::new);

		Pair<Integer, Integer> minXInterMinX = Pair.of(this.xRange.getLeft(), cuboidIntersection.xRange.getLeft() - 1);
		Pair<Integer, Integer> InterXRange = cuboidIntersection.xRange;
		Pair<Integer, Integer> InterMaxXMaxX = Pair.of(cuboidIntersection.xRange.getRight() + 1, this.xRange.getRight());

		Pair<Integer, Integer> minYInterMinY = Pair.of(this.yRange.getLeft(), cuboidIntersection.yRange.getLeft() - 1);
		Pair<Integer, Integer> InterYRange = cuboidIntersection.yRange;
		Pair<Integer, Integer> InterMaxYMaxY = Pair.of(cuboidIntersection.yRange.getRight() + 1, this.yRange.getRight());

		Pair<Integer, Integer> minZInterMinZ = Pair.of(this.zRange.getLeft(), cuboidIntersection.zRange.getLeft() - 1);
		Pair<Integer, Integer> InterZRange = cuboidIntersection.zRange;
		Pair<Integer, Integer> InterMaxZMaxZ = Pair.of(cuboidIntersection.zRange.getRight() + 1, this.zRange.getRight());

		return Lists.cartesianProduct(
						List.of(minXInterMinX, InterXRange, InterMaxXMaxX),
						List.of(minYInterMinY, InterYRange, InterMaxYMaxY),
						List.of(minZInterMinZ, InterZRange, InterMaxZMaxZ))
				.parallelStream()
				.filter(ranges -> ranges.parallelStream().noneMatch(range -> range.getLeft() > range.getRight()))
				.filter(ranges -> ranges.get(0).getLeft() >= Math.min(this.xRange.getLeft(), cuboid.xRange.getLeft()) && ranges.get(0).getLeft() <= Math.max(this.xRange.getRight(), cuboid.xRange.getRight()))
				.filter(ranges -> ranges.get(0).getRight() >= Math.min(this.xRange.getLeft(), cuboid.xRange.getLeft()) && ranges.get(0).getRight() <= Math.max(this.xRange.getRight(), cuboid.xRange.getRight()))
				.filter(ranges -> ranges.get(1).getLeft() >= Math.min(this.yRange.getLeft(), cuboid.yRange.getLeft()) && ranges.get(1).getLeft() <= Math.max(this.yRange.getRight(), cuboid.yRange.getRight()))
				.filter(ranges -> ranges.get(1).getRight() >= Math.min(this.yRange.getLeft(), cuboid.yRange.getLeft()) && ranges.get(1).getRight() <= Math.max(this.yRange.getRight(), cuboid.yRange.getRight()))
				.filter(ranges -> ranges.get(2).getLeft() >= Math.min(this.zRange.getLeft(), cuboid.zRange.getLeft()) && ranges.get(2).getLeft() <= Math.max(this.zRange.getRight(), cuboid.zRange.getRight()))
				.filter(ranges -> ranges.get(2).getRight() >= Math.min(this.zRange.getLeft(), cuboid.zRange.getLeft()) && ranges.get(2).getRight() <= Math.max(this.zRange.getRight(), cuboid.zRange.getRight()))
				.map(ranges -> new Cuboid(ranges.get(0), ranges.get(1), ranges.get(2)))
				.filter(Predicate.not(cuboidIntersection::equals))
				.collect(Collectors.toSet());
	}

	public Optional<Cuboid> intersection(Cuboid cuboid) {
		if (this.isFullyContainedWithin(cuboid)) {
			return Optional.of(this);
		}

		if (cuboid.isFullyContainedWithin(this)) {
			return Optional.of(cuboid);
		}

		if (!isOverlap(cuboid)) {
			return Optional.empty();
		}

		Pair<Integer, Integer> intersectionXRange = getIntersectionRange(this.xRange, cuboid.xRange);
		Pair<Integer, Integer> intersectionYRange = getIntersectionRange(this.yRange, cuboid.yRange);
		Pair<Integer, Integer> intersectionZRange = getIntersectionRange(this.zRange, cuboid.zRange);

		return Optional.of(new Cuboid(intersectionXRange, intersectionYRange, intersectionZRange));
	}

	@NotNull
	private Pair<Integer, Integer> getIntersectionRange(Pair<Integer, Integer> range1, Pair<Integer, Integer> range2) {
		return Pair.of(
				Math.max(range1.getLeft(), range2.getLeft()),
				Math.min(range1.getRight(), range2.getRight()));
	}

	public boolean isOverlap(Cuboid cuboid) {
		return isOverlap(this.xRange, cuboid.xRange)
				&& isOverlap(this.yRange, cuboid.yRange)
				&& isOverlap(this.zRange, cuboid.zRange);
	}

	public boolean isFullyContainedWithin(Cuboid cuboid) {
		return isBetweenRange(this.xRange.getLeft(), cuboid.xRange)
				&& isBetweenRange(this.xRange.getRight(), cuboid.xRange)
				&& isBetweenRange(this.yRange.getLeft(), cuboid.yRange)
				&& isBetweenRange(this.yRange.getRight(), cuboid.yRange)
				&& isBetweenRange(this.zRange.getLeft(), cuboid.zRange)
				&& isBetweenRange(this.zRange.getRight(), cuboid.zRange);
	}

	private boolean isOverlap(Pair<Integer, Integer> range1, Pair<Integer, Integer> range2) {
		return isBetweenRange(range1.getLeft(), range2)
				|| isBetweenRange(range1.getRight(), range2)
				|| isBetweenRange(range2.getLeft(), range1)
				|| isBetweenRange(range2.getRight(), range1);
	}

	private boolean isBetweenRange(Integer point, Pair<Integer, Integer> range) {
		return point >= range.getLeft() && point <= range.getRight();
	}
}
