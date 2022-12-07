package com.clement.advent2022.day07;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;

public class Directory {
	public static Directory ROOT = new Directory("/");

	private final String name;
	private final Map<String, Directory> directoriesByName = new HashMap<>();
	private final Map<String, File> filesByName = new HashMap<>();
	private final Directory parent;

	private Directory(String name) {
		this.name = name;
		this.parent = null;
	}

	public Directory(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
		parent.addDirectory(this);
	}

	public Path getPath() {
		if (this == ROOT) {
			return Path.of("/");
		} else {
			return parent.getPath().resolve(name);
		}
	}

	public String getName() {
		return this.name;
	}

	private void addDirectory(Directory directory) {
		Directory previous = this.directoriesByName.putIfAbsent(directory.getName(), directory);
		Assert.isNull(previous, "There is already a directory by that name");
	}

	public Optional<Directory> getDirectory(String name) {
		return Optional.ofNullable(this.directoriesByName.get(name));
	}

	public void addFile(File file) {
		File previous = this.filesByName.putIfAbsent(file.name(), file);
		Assert.isNull(previous, "There is already a file by that name");
	}

	public Directory getParent() {
		return this.parent;
	}

	public Map<Path, Integer> getDirectorySizes() {
		Map<Path, Integer> directorySizes = this.directoriesByName.values().stream()
				.map(Directory::getDirectorySizes)
				.reduce(new HashMap<>(), (all, current) -> {
					all.putAll(current);
					return all;
				});

		int sumSubDirectorySizes = this.directoriesByName.values().stream()
				.map(Directory::getPath)
				.mapToInt(directorySizes::get)
				.sum();

		int fileSizes = this.filesByName.values().stream().mapToInt(File::size).sum();

		directorySizes.put(this.getPath(), sumSubDirectorySizes + fileSizes);

		return directorySizes;
	}
}
