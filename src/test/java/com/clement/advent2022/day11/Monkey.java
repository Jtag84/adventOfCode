package com.clement.advent2022.day11;

import java.util.List;
import java.util.function.Function;

public record Monkey(int id,
					 List<Long> items,
					 Function<Long, Long> operation,
					 long divisibleBy,
					 int throwsToMonkeyIfTrue,
					 int throwsToMonkeyIfFalse
) {
	public void inspectItems(List<Monkey> monkeys, Function<Long, Long> postInspectionOperation) {
		this.items.forEach(item -> inspectItem(item, monkeys, postInspectionOperation));
		this.items.clear();
	}

	private void inspectItem(Long item, List<Monkey> monkeys, Function<Long, Long> postInspectionOperation) {
		Long worryLevel = postInspectionOperation.apply(operation.apply(item));
		if ((worryLevel % divisibleBy) == 0) {
			monkeys.get(throwsToMonkeyIfTrue).items.add(worryLevel);
		} else {
			monkeys.get(throwsToMonkeyIfFalse).items.add(worryLevel);
		}
	}
}
