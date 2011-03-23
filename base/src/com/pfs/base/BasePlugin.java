package com.pfs.base;

import static com.pfs.base.PluginSupport.LINE_SEP;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The main plugin class to be used in the desktop.
 */
public abstract class BasePlugin extends AbstractUIPlugin {

	// necessary so we can specify a non-abstract plugin in plugin.xml
	public static class Impl extends BasePlugin {

		@Override
		public String getPluginId() {
			throw new IllegalStateException();
		}

		@Override
		public void onStop() {
		}
	}
	
	public abstract String getPluginId();

	public abstract void onStop();

	// Resource bundle.
	private ResourceBundle resourceBundle;

	/**
	 * The constructor.
	 */
	public BasePlugin() {
		super();
	}

//	public BasePlugin(IPluginDescriptor descriptor) {
//		super(descriptor);
//	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		resourceBundle = null;
		onStop();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not found.
	 */
	public String getResourceString(String key) {
		ResourceBundle bundle = getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	protected String getResourceBundleName() {
		return getPluginId() + ".resources";
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null) {
				resourceBundle = ResourceBundle.getBundle(getResourceBundleName());
			}
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}

	public String getStringPreference(String name) {
		return getPreferenceStore().getString(name);
	}

	private Shell getShell() {
		IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();

		return window == null ? null : window.getShell();
	}

	public void showError(Throwable error) {
		Shell shell = getShell();

		if (shell != null) {
			showError(shell, error);
		} else {
			logError(error);
		}
	}

	public void showError(Shell shell, Throwable error) {
		String title = getName() + " Error";
		String message = error.getClass() 
			+ LINE_SEP + error.getMessage() 
			+ LINE_SEP + getStackTrace(error);

		logError(message, error);
		MessageDialog.openError(shell, title, message);
	}

	private String getStackTrace(Throwable error) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pOut = new PrintStream(out);

		error.printStackTrace(pOut);
		pOut.close();
		String trace = new String(out.toByteArray());
		return trace;
	}

	public void showInfo(String message) {
		Shell shell = getShell();

		if (shell != null) {
			showInfo(shell, message);
		} else {
			logInfo(message);
		}
	}

	public void showInfo(Shell shell, String message) {
		String title = getName();

		MessageDialog.openInformation(shell, title, message);
	}

	private String getName() {
		return getBundle() != null ? getBundle().getSymbolicName() : "<unknown name>";
	}

	public void logInfo(Object msg) {
		Status status = new Status(IStatus.ERROR, getName(), IStatus.ERROR, msg + LINE_SEP, null);
		getLog().log(status);
	}

	public void logError(Throwable ex) {
		logError(null, ex);
	}

	public void logError(String msg, Throwable ex) {
		String message = createMessage(msg, ex);
		Status status = new Status(IStatus.ERROR, getName(), IStatus.ERROR, message, ex);
		getLog().log(status);
	}

	private String createMessage(String message, Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));

		String trace = stringWriter.getBuffer().toString();
		return message != null ? message + LINE_SEP + trace : trace;
	}

	public ImageDescriptor getImageDescriptor(String key) {
		URL url = getBundle().getEntry(key);

		return ImageDescriptor.createFromURL(url);
	}
}
