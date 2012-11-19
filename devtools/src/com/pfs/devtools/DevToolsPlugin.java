package com.pfs.devtools;

import com.pfs.base.BasePlugin;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.IStartup;

/**
 * The main plugin class to be used in the desktop.
 */
public class DevToolsPlugin extends BasePlugin implements IStartup {

	public static final String PLUGIN_ID = "com.pfs.devtools";
	private static DevToolsPlugin plugin;

	/**
	 * Returns the shared instance.
	 */
	public static DevToolsPlugin getDefault() {
		return plugin;
	}

	public DevToolsPlugin() {
		super();
		plugin = this;
	}

	@Override
	public void onStop() {
		plugin = null;
	}

	public void earlyStartup() {
		DevToolsLaunchConfigurationListener launchConfigListener = new DevToolsLaunchConfigurationListener();
		DebugPlugin.getDefault().getLaunchManager().addLaunchConfigurationListener(launchConfigListener);

		DevToolsProjectAddedListener resourceListener = new DevToolsProjectAddedListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
		
		DerivedDirectoryMarkerFolderAddedListener derivedDirectoryMarkerListener = new DerivedDirectoryMarkerFolderAddedListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(derivedDirectoryMarkerListener);
	}

	@Override
	public String getPluginId() {
		return PLUGIN_ID;
	}
}
