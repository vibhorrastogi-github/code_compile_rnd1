package com.xyz.code.compile.rnd.common;

import java.lang.reflect.Method;

/**
 * 
 */

/**
 * @author vrasto1
 * 
 */
public class LoadClass {

	public static void main(String[] args) {

		ClassLoader classLoader = LoadClass.class.getClassLoader();

		try {
			final Class<?> aClass = classLoader.loadClass("com.xyz.code.compile.rnd.common.Test");
			final Object obj = aClass.newInstance();
			System.out.println("aClass.getName() = " + aClass.getName());
			final Method method = aClass.getMethod("getMyName", String.class);
			final Object resp = method.invoke(obj, "vibhor");
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
