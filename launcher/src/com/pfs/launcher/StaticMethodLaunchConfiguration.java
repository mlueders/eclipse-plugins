/*
 * Created on Feb 8, 2005
 * @author mike
 */
package com.pfs.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

import java.io.IOException;
import java.net.URL;

public class StaticMethodLaunchConfiguration extends JavaLaunchDelegate {
	public static final String ID_STATIC_APPLICATION = QuickLaunchPlugin.PLUGIN_ID + ".StaticMethodLaunchConfigurationType";
	public static final String ID_MAIN_TYPE_NAME = QuickLaunchPlugin.PLUGIN_ID + ".typeName";
	public static final String ID_METHOD_NAME = QuickLaunchPlugin.PLUGIN_ID + ".methodName";
	private static final String MAIN_CLASS_NAME = StaticMethodApplication.class.getName();

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationWorkingCopy config = configuration.getWorkingCopy();
		String className = config.getAttribute(ID_MAIN_TYPE_NAME, "");
		String methodName = config.getAttribute(ID_METHOD_NAME, "");
		String argString = className + " " + methodName;

		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MAIN_CLASS_NAME);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, argString);
		config.doSave();
		super.launch(configuration, mode, launch, monitor);
	}

	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException {
		URL url = Platform.getBundle(QuickLaunchPlugin.PLUGIN_ID).getEntry("/");
		String[] cp = super.getClasspath(configuration);
		String[] classPath = null;

		try {
			String entry = null;

			if (Platform.inDevelopmentMode()) {
				try {
					entry = FileLocator.toFileURL(new URL(url, "bin")).getFile();
				} catch (IOException ex) {
					try {
						entry = FileLocator.toFileURL(new URL(url, QuickLaunchPlugin.PLUGIN_JAR_NAME)).getFile();
					} catch (IOException ex2) {
						QuickLaunchPlugin.getDefault().logError("Failed to resolve plugin jar", ex2);
					}
				}
			} else {
				entry = FileLocator.toFileURL(new URL(url, QuickLaunchPlugin.PLUGIN_JAR_NAME)).getFile();
			}

			if (entry != null) {
				classPath = new String[cp.length + 1];
				classPath[0] = entry;
				System.arraycopy(cp, 0, classPath, 1, cp.length);
			}
		} catch (IOException e) {
			QuickLaunchPlugin.getDefault().logError(e);
		}
		return classPath;
	}
}
