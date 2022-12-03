package com.clement.advent2020.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DayFourTest {
	private static final Logger log = LoggerFactory.getLogger(DayFourTest.class);

	Set<String> validFieldsWithOptional = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid");
	Set<String> validFieldsWithoutOptional = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
	Set<String> validEcl = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

	private BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

	@Test
	void part1() throws IOException {
		List<Map<String, String>> passports = getPassports();

		long numberOfValidPassports = passports.stream().filter(this::isPassportValidPart1).count();
		log.info("answer Part1: " + numberOfValidPassports);
	}

	private List<Map<String, String>> getPassports() throws IOException {
		final BufferedReader reader = getInputsReader();

		String line = reader.readLine();
		List<Map<String, String>> passports = new ArrayList<>();
		Map<String, String> currentPassport = new HashMap<>();
		while (line != null) {
			if (line.isEmpty()) {
				passports.add(currentPassport);
				currentPassport = new HashMap<>();
			} else {
				List<Pair<String, String>> fields = Stream.of(line.split(" "))
						.map(field -> field.trim().split(":"))
						.map(keyValueField -> Pair.of(keyValueField[0], keyValueField[1]))
						.toList();

				for (Pair<String, String> field : fields) {
					currentPassport.put(field.getKey(), field.getValue());
				}
			}

			line = reader.readLine();
		}
		passports.add(currentPassport);
		return passports;
	}

	private boolean isPassportValidPart1(Map<String, String> fields) {
		return fields.keySet().containsAll(validFieldsWithOptional) || fields.keySet().containsAll(validFieldsWithoutOptional);
	}

	@Test
	void part2() throws IOException {
		List<Map<String, String>> passports = getPassports();

		long numberOfValidPassports = passports.stream().filter(this::isPassportValidPart2).count();

		log.info("answer Part2: " + numberOfValidPassports);
	}

	private boolean isPassportValidPart2(Map<String, String> fields) {
		boolean part1Valid = this.isPassportValidPart1(fields);

		if (!part1Valid) {
			return false;
		}

		boolean isByrValid = checkYears(fields, "byr", 1920, 2002);
		boolean isIyrValid = checkYears(fields, "iyr", 2010, 2020);
		boolean isEyrValid = checkYears(fields, "eyr", 2020, 2030);

		String hgt = fields.get("hgt");
		boolean isValidHgt;
		if (hgt.length() > 3) {
			String unit = hgt.substring(hgt.length() - 2);
			int hgtValue = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
			switch (unit) {
				case "cm":
					isValidHgt = hgtValue >= 150 && hgtValue <= 193;
					break;
				case "in":
					isValidHgt = hgtValue >= 59 && hgtValue <= 76;
					break;
				default:
					isValidHgt = false;
					break;
			}
		} else {
			return false;
		}
		boolean isHclValid = fields.get("hcl").matches("#[a-z|0-9]{6}");
		boolean isEclValid = validEcl.contains(fields.get("ecl"));
		boolean isPidValid = fields.get("pid").matches("[0-9]{9}");
		return isByrValid && isIyrValid && isEyrValid && isValidHgt && isHclValid && isEclValid && isPidValid;
	}

	private boolean checkYears(Map<String, String> fields, String fieldName, int atLeastYear, int atMostYear) {
		final int year = Integer.parseInt(fields.get(fieldName));
		return year >= atLeastYear && year <= atMostYear;
	}
}
