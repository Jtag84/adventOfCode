package com.clement.advent2021.day24;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.clement.utils.SolutionBase;

class DayTwentyFourTest extends SolutionBase {
	@Test
	void part16() throws IOException {
		List<Consumer<State>> instructions = getInputsReader().lines()
				.map(this::toInstruction)
				.toList();

		Long input = 90_999_999_999_001L;
		State state = new State(null);
		state.setZ(1);
		do {
			if (input % 1000000 == 0) {
				log.info("input: " + input);
			}
			input--;
			if (input <= 87_000_000_000_000L) {
				throw new IllegalStateException("too low " + input);
			}
			String inputString = input.toString();
			int zeroIndex = inputString.indexOf("0");

			if (zeroIndex != -1) {
				int subtractValue = (int) Math.pow(10, (inputString.length() - zeroIndex - 1));
				input = input - subtractValue + 1;
				continue;
			}
			state = runInstructions(inputString.chars().map(c -> c - '0').iterator(), instructions);
		} while (state.getZ() != 0);

		log.info("answer Part1: " + input);
	}

	private State runInstructions(Iterator<Integer> inputIterator, List<Consumer<State>> instructions) {
		State state = new State(inputIterator);

		instructions.forEach(instruction -> instruction.accept(state));

		return state;
	}

	private Consumer<State> toInstruction(String s) {
		String[] lineSplit = s.split(" ");
		final Function<State, Integer> varLeft = getVar(lineSplit[1]);

		final Function<State, Integer> varRight;
		if (lineSplit.length > 2) {
			varRight = getVar(lineSplit[2]);
		} else {
			varRight = null;
		}

		final BiConsumer<State, Integer> varSetter = getVarSetter(lineSplit[1]);

		return switch (lineSplit[0]) {
			case "inp" -> (state) -> varSetter.accept(state, state.inputIterator.next());
			case "add" -> (state) -> varSetter.accept(state, varLeft.apply(state) + varRight.apply(state));
			case "mul" -> (state) -> varSetter.accept(state, varLeft.apply(state) * varRight.apply(state));
			case "div" -> (state) -> varSetter.accept(state, varLeft.apply(state) / varRight.apply(state));
			case "mod" -> (state) -> varSetter.accept(state, varLeft.apply(state) % varRight.apply(state));
			case "eql" -> (state) -> varSetter.accept(state, (varLeft.apply(state) == varRight.apply(state)) ? 1 : 0);
			default -> throw new IllegalStateException();
		};
	}

	private Function<State, Integer> getVar(String s) {
		return switch (s) {
			case "w" -> State::getW;
			case "x" -> State::getX;
			case "y" -> State::getY;
			case "z" -> State::getZ;
			default -> {
				final int value = Integer.parseInt(s);
				yield (state) -> value;
			}
		};
	}

	private BiConsumer<State, Integer> getVarSetter(String s) {
		return switch (s) {
			case "w" -> State::setW;
			case "x" -> State::setX;
			case "y" -> State::setY;
			case "z" -> State::setZ;
			default -> throw new IllegalStateException();
		};
	}

	@Test
	void part2() throws IOException {
		log.info("answer Part2: " + 0);
	}

	class State {
		public final Iterator<Integer> inputIterator;
		private int w = 0;
		private int x = 0;
		private int y = 0;
		private int z = 0;

		State(Iterator<Integer> inputIterator) {
			this.inputIterator = inputIterator;
		}

		@Override
		public String toString() {
			return "w=" + getW() + " x=" + getX() + " y=" + getY() + " z=" + getZ();
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}
	}
}