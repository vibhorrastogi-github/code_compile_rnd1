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

		try {
			final ProcessBuilder pb = new ProcessBuilder();
			pb.redirectErrorStream(true);

			pb.command(new String[] { "cmd", "/c", "tt" });

			// pb.redirectOutput(new File("Output.txt"));

			StringBuilder result = new StringBuilder();

			final Process p = pb.start();

			readFromInputStream(p.getInputStream(), result);

			System.out.println(result.toString());
			// if (command.toString().contains("java")) {
			// writeToOutputStream(p.getOutputStream());
			// }

			p.destroy();
		} catch (final Throwable t) {
			t.printStackTrace();
		}

	}

	public static String codeCompileAndExecute(final String sourceCode,
			final String fileName, final String lang) {
		String result = "";

		try {
			final File sourceFile = new File(fileName + "." + lang);
			sourceFile.createNewFile();
			JOut.write(sourceFile, sourceCode);
		} catch (final Exception e) {
			return e.toString();
		}

		if (lang.equalsIgnoreCase("java")) {

			result = compile("javac " + fileName + "." + lang);
			if (result.trim().length() <= 0) {
				result = exec("java " + fileName + " -Xmx10m", true,
						"max_nbr.in");
			}
		} else if (lang.equalsIgnoreCase("c")) {

			result = compile("tcc " + fileName + "." + lang);
			System.out.println(result);
			if (!result.contains("Error:")
					&& new File(fileName + ".exe").exists()) {
				result = exec(fileName, true, "random_nbr.in");
			}
		}
		return result;
	}

	public static String compile(final String command) {
		final StringBuilder result = new StringBuilder();
		try {
			final ProcessBuilder pb = new ProcessBuilder();
			pb.redirectErrorStream(true);

			pb.command(new String[] { "cmd", "/c", command });
			final Process p = pb.start();
			readFromInputStream(p.getInputStream(), result);
			wait(p);
			p.destroy();
		} catch (final Throwable t) {
			return t.toString();
		}
		return result.toString().trim();
	}

	public static String exec(final String command, boolean writeOutput,
			String inputFile) {
		final StringBuilder result = new StringBuilder();
		try {
			final ProcessBuilder pb = new ProcessBuilder();
			pb.redirectErrorStream(true);

			pb.command(new String[] { "cmd", "/c", command });

			final Process p = pb.start();
			if (writeOutput) {
				writeToOutputStream(p.getOutputStream(), inputFile);
			}

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

	private static void writeToOutputStream(final OutputStream out,
			String inputFile) throws IOException {

		final BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(out));

		final BufferedReader br = new BufferedReader(new FileReader("./in_out/"
				+ inputFile));
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
