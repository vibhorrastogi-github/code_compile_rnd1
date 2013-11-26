/**
 * 
 */
package com.xyz.code.compile.rnd.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author vrasto1
 * 
 */
public class ExecuteCommand {

	private static final int timeoutInSeconds = 5;

	public static void main(String[] args) throws IOException,
			InterruptedException {

		System.out.println(compile("javac", "Sample.java"));
	}

	public static String codeCompileAndExecute(final String sourceCode,
			final String fileName, final String lang) {
		String result = null;

		if (lang.equalsIgnoreCase("java")) {
			try {
				final File sourceFile = new File(fileName + ".java");
				sourceFile.createNewFile();
				JOut.write(sourceFile, sourceCode);
			} catch (final Exception e) {
				return e.toString();
			}
			result = compile("javac", fileName + ".java");
			if (result.trim().length() <= 0) {
				result = exec("java", fileName, "-Xmx10m");
			}
		}
		return result;
	}

	public static String compile(final String... command) {
		final StringBuilder result = new StringBuilder();
		try {
			final ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			final Process p = pb.start();
			readFromInputStream(p.getInputStream(), result);
			wait(p);
			p.destroy();
		} catch (final Throwable t) {
			return t.toString();
		}
		return result.toString().trim();
	}

	public static String exec(final String... command) {
		final StringBuilder result = new StringBuilder();
		try {
			final ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			final Process p = pb.start();
			writeToOutputStream(p.getOutputStream());
			readFromInputStream(p.getInputStream(), result);
			wait(p);
			p.destroy();
		} catch (final Throwable t) {
			return t.toString();
		}
		return result.toString().trim();
	}

	private static void readFromInputStream(final InputStream in,
			final StringBuilder result) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = br.readLine()) != null) {
			result.append(line + "\n");
		}
		br.close();
	}

	private static void writeToOutputStream(final OutputStream out)
			throws IOException {

		final BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(out));

		final BufferedReader br = new BufferedReader(new FileReader(
				"./in_out/max_nbr.in"));
		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}

	private static void wait(Process p) throws InterruptedException {
		long now = System.currentTimeMillis();
		long timeoutInMillis = 1000L * timeoutInSeconds;
		long finish = now + timeoutInMillis;

		while (isAlive(p) && (System.currentTimeMillis() < finish)) {
			Thread.sleep(10);
		}
		if (isAlive(p)) {
			throw new IllegalStateException("Process timeout out after "
					+ timeoutInSeconds + " seconds");
		}
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
