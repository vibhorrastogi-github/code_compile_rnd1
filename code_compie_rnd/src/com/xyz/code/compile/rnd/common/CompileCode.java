package com.xyz.code.compile.rnd.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 */

/**
 * @author vrasto1
 * 
 */
public class CompileCode {

	public static void main(String[] args) throws Exception {
		useProcessBuilder();
	}

	private static void useProcessBuilder() throws Exception {
		ProcessBuilder pb = new ProcessBuilder("jCompile.bat", "Sample", "1384966296529");
		pb.redirectErrorStream(true);
		Process p = pb.start();
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
//		while (!(line = br.readLine()).equalsIgnoreCase("start-print")) {
//			
//		}
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		p.waitFor();
	}

	/*
	 * private static void useRuntime() throws Exception { final String
	 * cmdArray[] = new String[2]; cmdArray[0] = "javac Test.java"; cmdArray[1]
	 * = "java Test"; Runtime.getRuntime().exec(cmdArray); }
	 */
}
