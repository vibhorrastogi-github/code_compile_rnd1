/**
 * 
 */
package com.xyz.code.compile.rnd.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author vrasto1
 * 
 */
public class In {

	public static String readFileAsString(final String file) throws IOException {
		final StringBuilder result = new StringBuilder();
		final BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file + ".out")));
		String line = null;
		while ((line = r.readLine()) != null) {
			result.append(line + "\n");
		}
		return result.toString().trim();
	}
}
