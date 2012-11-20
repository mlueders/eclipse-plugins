/*
 * Created on Feb 6, 2005
 * @author mike
 */
package com.pfs.launcher.launchers;

import com.pfs.launcher.QuickLaunchPlugin;

import org.eclipse.ant.internal.ui.launchConfigurations.AntLaunchConfigurationMessages;
import org.eclipse.ant.internal.ui.launchConfigurations.AntLaunchShortcut;
import org.eclipse.ant.internal.ui.launchConfigurations.IAntLaunchConfigurationConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.debug.ui.DebugUITools;

import java.util.Iterator;
import java.util.List;

public class AntLauncher extends AbstractLauncher {
	private IFile antFile;
	private String targetName;

	public AntLauncher(LauncherConfig config, IFile antFile, String targetName) {
		super(getConfig(config, antFile, targetName));
		this.antFile = antFile;
		this.targetName = targetName;
	}

	private static LauncherConfig getConfig(LauncherConfig config, IFile antFile, String targetName) {
		String launcherName = getLaunchConfigurationName(antFile, targetName);
		config.setLauncherName(launcherName);
		return config;
	}

	private static String getLaunchConfigurationName(IFile file, String targetName) {
		String projectName = file.getProject().getName();

		return getLaunchConfigurationName(file.getFullPath(), projectName, targetName);
	}

	private static String getLaunchConfigurationName(IPath filePath, String projectName, String targetAttribute) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(projectName).append(' ').append(filePath.lastSegment());
		if (targetAttribute.length() > 0) {
			buffer.append(" [").append(targetAttribute).append(']');
		}
		return buffer.toString();
	}

	public boolean supportsLaunchMode() {
		return LaunchManager.RUN_MODE.equals(getLaunchMode());
	}

	public void run() {
		LaunchShortcut shortcut = new LaunchShortcut();

		shortcut.launch(antFile, getLaunchMode(), targetName, getLauncherName());
	}

	static class LaunchShortcut extends AntLaunchShortcut {

		public void launch(IFile file, String mode, String targetName, String launcherName) {
			ILaunchConfiguration configuration = getLaunchConfiguration(file, targetName, launcherName);

			if (configuration == null) {
				antFileNotFound();
			}

			DebugUITools.launch(configuration, mode);
		}

		private ILaunchConfiguration getLaunchConfiguration(IFile file, String targetName, String launcherName) {
			List<?> configurations = findExistingLaunchConfigurations(file);

			for (Iterator<?> i = configurations.iterator(); i.hasNext();) {
				ILaunchConfiguration configuration = (ILaunchConfiguration) i.next();

				if (launcherName.equals(configuration.getName())) {
					return configuration;
				}
			}

			return createDefaultLaunchConfiguration(file, targetName, launcherName);
		}

		private ILaunchConfiguration createDefaultLaunchConfiguration(IFile file, String targetName, String launcherName) {
			ILaunchConfiguration configuration = createDefaultLaunchConfiguration(file);

			try {
				ILaunchConfigurationWorkingCopy workingCopy = configuration.copy(launcherName);
				configuration.delete();
				workingCopy.setAttribute(IAntLaunchConfigurationConstants.ATTR_ANT_TARGETS, targetName);
				configuration = workingCopy.doSave();
			} catch (CoreException ex) {
				QuickLaunchPlugin.getDefault().logError("Failed to save launch configuration", ex);
			}

			return configuration;
		}

		/**
		 * Inform the user that an ant file was not found to run.
		 */
		private void antFileNotFound() {
			reportError(AntLaunchConfigurationMessages.AntLaunchShortcut_Unable, null); //$NON-NLS-1$  
		}
	}
}
