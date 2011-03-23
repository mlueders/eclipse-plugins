package com.pfs.launcher.launchers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaApplicationLaunchShortcut;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class MainLauncher extends AbstractLauncher {
	public MainLauncher(LauncherConfig config) {
		super(config);
	}

	public void run() {
		LaunchShortcut shortcut = new LaunchShortcut();

		shortcut.launch(getEditor(), getLaunchMode());
	}

	protected String getLauncherType() {
		return IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION;
	}

	protected ILaunchConfigurationType getLaunchConfigurationType() {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();

		return manager.getLaunchConfigurationType(getLauncherType());
	}

	protected void setAdditionalAttributes(ILaunchConfigurationWorkingCopy workingCopy) {
	}

	class LaunchShortcut extends JavaApplicationLaunchShortcut {
		protected ILaunchConfiguration findLaunchConfiguration(IType type, ILaunchConfigurationType configType) {
			try {
				ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);

				for (int i = 0; i < configs.length; i++) {
					if (getLauncherName().equals(configs[i].getName())) {
						return configs[i];
					}
				}
			} catch (CoreException e) {
				JDIDebugUIPlugin.log(e);
			}
			return super.findLaunchConfiguration(type, configType);
		}

		/**
		 * Create & return a new configuration based on the specified <code>IType</code>.
		 */
		protected ILaunchConfiguration createConfiguration(IType type) {
			String launcherName = MainLauncher.this.getLauncherName();
			ILaunchConfigurationType configType = MainLauncher.this.getLaunchConfigurationType();

			ILaunchConfigurationWorkingCopy wc = null;
			try {
				wc = configType.newInstance(null, launcherName);
			} catch (CoreException exception) {
				JDIDebugUIPlugin.log(exception);
				return null;
			}
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, type.getFullyQualifiedName());
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, type.getJavaProject().getElementName());

			MainLauncher.this.setAdditionalAttributes(wc);

			ILaunchConfiguration config = null;
			try {
				config = wc.doSave();
			} catch (CoreException exception) {
				JDIDebugUIPlugin.log(exception);
			}
			return config;
		}
	}
}
