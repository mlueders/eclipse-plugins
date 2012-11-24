package com.pfs.devtools;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;

import com.pfs.base.BasePlugin;

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
	
	public static boolean isMacOs() {
		String osName = System.getProperty("os.name");
		return osName.toLowerCase().contains("mac");
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
		
		if (DevToolsPlugin.isMacOs()) {
			IWindowListener windowListener = new ExpandTreeWithArrowKeyWorkaround.WindowListener();
			DevToolsPlugin.getDefault().getWorkbench().addWindowListener(windowListener);
		}
	}

	@Override
	public String getPluginId() {
		return PLUGIN_ID;
	}
}
