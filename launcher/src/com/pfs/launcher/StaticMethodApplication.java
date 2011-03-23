/*
 * Created on Feb 8, 2005
 * @author mike
 */
package com.pfs.launcher;

import java.lang.reflect.Method;

public class StaticMethodApplication {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: StaticMethodLauncher <class name> <method name>");
		}

		String className = args[0];
		String methodName = args[1];

		Class type = Class.forName(className);
		Method method = type.getMethod(methodName);

		method.invoke(null);
	}
}
