package com.clement.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SolutionBase {
	protected final Log log = LogFactory.getLog(getClass());

	protected BufferedReader getInputsReader() {
		InputStream is = this.getClass().getResourceAsStream("inputs");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}
}
