/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.base;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class PluginSupport {

	public static final String LINE_SEP = System.getProperty("line.separator");

	public static int getCursorPosition(ITextEditor editor) {
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();

		return sel.getOffset();
	}

	public static ICompilationUnit getCompilationUnit(IEditorPart editor) {
		IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);

		return file == null ? null : JavaCore.createCompilationUnitFrom(file);
	}

	public static Object invokeInaccessibleMethod(Object target, String methodName, Object[] args) throws Exception {
		Class<?>[] paramTypes;

		if (args != null) {
			paramTypes = new Class[args.length];

			for (int i = 0; i < paramTypes.length; i++) {
				paramTypes[i] = args[i].getClass();
			}
		} else {
			paramTypes = null;
		}
		return invokeInaccessibleMethod(target, methodName, args, paramTypes);
	}

	public static Object invokeInaccessibleMethod(Object target, String methodName, Object[] args, Class<?>[] paramTypes)
			throws Exception {
		Class<?> targetClass = target.getClass();
		Method targetMethod = null;

		while (targetClass != Object.class) {
			try {
				targetMethod = targetClass.getDeclaredMethod(methodName, paramTypes);
				break;
			} catch (NoSuchMethodException ex) {
				targetClass = targetClass.getSuperclass();
			}
		}

		if (targetMethod == null) {
			throw new NoSuchMethodException(methodName);
		}

		targetMethod.setAccessible(true);
		return targetMethod.invoke(target, args);
	}

	public static Object getInaccessibleValue(Object target, String name) throws Exception {
		Class<?> targetClass = target.getClass();
		Field targetField = null;

		while (targetClass != Object.class) {
			try {
				targetField = target.getClass().getDeclaredField(name);
				break;
			} catch (NoSuchFieldException ex) {
				targetClass = targetClass.getSuperclass();
			}
		}

		if (targetField == null) {
			throw new NoSuchFieldException(name);
		}

		targetField.setAccessible(true);
		return targetField.get(target);
	}

	public static Object[] toArray(Iterator<?> elements) {
		ArrayList<Object> list = new ArrayList<Object>();
		while (elements.hasNext()) {
			list.add(elements.next());
		}
		return list.toArray();
	}

	public static Object[] toArray(Iterator<?> elements, Class<?> type) {
		ArrayList<Object> list = new ArrayList<Object>();
		while (elements.hasNext()) {
			list.add(elements.next());
		}
		Object[] array = (Object[]) Array.newInstance(type, list.size());
		list.toArray(array);
		return array;
	}

	public static String toString(Iterator<?> elements, String methodName) {
		return toString(toArray(elements), methodName);
	}

	public static String toString(Iterator<?> elements, String[] methodName) {
		return toString(toArray(elements), methodName);
	}

	public static String toString(Object[] elements, String methodName) {
		return toString(elements, new String[] { methodName });
	}

	public static String toString(Object[] elements, String[] methodNames) {
		String msg = concat(methodNames) + LINE_SEP;

		for (int i = 0; i < elements.length; i++) {
			try {
				Object[] results = new Object[methodNames.length];

				for (int j = 0; j < methodNames.length; j++) {
					Method method = elements[i].getClass().getMethod(methodNames[j]);
					results[j] = method.invoke(elements[i]);
				}

				msg += "[" + i + "] - " + concat(results) + LINE_SEP;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return msg;
	}

	private static String concat(Object[] elements) {
		String concat = "";
		for (int i = 0; i < elements.length; i++) {
			if (i > 0) {
				concat += ", ";
			}

			concat += elements[i];
		}
		return concat;
	}
}
