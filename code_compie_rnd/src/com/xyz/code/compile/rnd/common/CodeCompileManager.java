/**
 * 
 */
package com.xyz.code.compile.rnd.common;

import java.io.File;
import java.io.IOException;

/**
 * @author vrasto1
 * 
 */
public class CodeCompileManager {

	private static final String J_COMPILE_BAT = "jCompile.bat";

	private static final int timeoutInSeconds = 5;

	public String compileCodeAndReturnOutput(final String sourceCode, final String fileName, final String lang)
			throws IOException, InterruptedException {

		ProcessBuilder pb = null;

		if (lang.equalsIgnoreCase("java")) {
			final File sourceFile = new File(fileName + ".java");
			sourceFile.createNewFile();
			JOut.write(sourceFile, sourceCode);
		} else {
			return null;
		}

		pb = new ProcessBuilder(J_COMPILE_BAT, fileName);
		pb.redirectErrorStream(true);

		final Process p = pb.start();

		long now = System.currentTimeMillis();
		long timeoutInMillis = 1000L * timeoutInSeconds;
		long finish = now + timeoutInMillis;

		while (isAlive(p) && (System.currentTimeMillis() < finish)) {
			Thread.sleep(10);
		}
		if (isAlive(p)) {
			return ("Process timeout out after " + timeoutInSeconds + " seconds");
		}
		return In.readFileAsString(fileName);
	}

	public static boolean isAlive(final Process p) {
		try {
			p.exitValue();
			return false;
		} catch (IllegalThreadStateException e) {
			return true;
		}
	}

}
