package org.easyexplore;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The main plugin class to be used in the desktop.
 */
public class EasyExplorePlugin extends AbstractUIPlugin {
	// The shared instance.
	private static EasyExplorePlugin plugin;
	// Resource bundle.
	private ResourceBundle resourceBundle;

	/**
	 * The constructor.
	 */
	public EasyExplorePlugin() {
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("org.easyexplore.EasyExplorePluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static EasyExplorePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = EasyExplorePlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	static public void log(Object msg) {
		ILog log = EasyExplorePlugin.getDefault().getLog();
		Status status = new Status(IStatus.ERROR, EasyExplorePlugin.getDefault().getBundle().getSymbolicName(), IStatus.ERROR, msg + "\n",
				null);
		log.log(status);
	}

	static public void log(Throwable ex) {
		ILog log = EasyExplorePlugin.getDefault().getLog();
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		String msg = stringWriter.getBuffer().toString();
		Status status = new Status(IStatus.ERROR, EasyExplorePlugin.getDefault().getBundle().getSymbolicName(), IStatus.ERROR, msg, null);
		log.log(status);
	}

	/**
	 * Return the target program setted in EasyExplorePreferencePage.
	 * 
	 * @return String
	 */
	public static String getPreferenceString(String propertyName) {
		return getDefault().getPreferenceStore().getString(propertyName);
	}
}
