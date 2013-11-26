/**
 * 
 */
package com.xyz.code.compile.rnd.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author vrasto1
 * 
 */
public class JOut {

	public static void write(final File outputFile, final Object content) throws IOException {
		final FileWriter writer = new FileWriter(outputFile);
		writer.write(content.toString());
		writer.flush();
		writer.close();
	}
}
