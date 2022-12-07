package com.clement.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;

public class SolutionBase {
	protected final Log log = LogFactory.getLog(getClass());

	protected BufferedReader getInputsExampleReader() {
		return getBufferedReader("inputsExample");
	}

	protected BufferedReader getInputsReader() {
		return getBufferedReader("inputs");
	}

	@NotNull
	private BufferedReader getBufferedReader(String fileName) {
		InputStream is = this.getClass().getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}
}
