/*
 * Created on Jan 31, 2005
 * @author mike
 */
package com.pfs.devtools;

import com.pfs.devtools.properties.PropertyConstants;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationListener;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class DevToolsLaunchConfigurationListener implements ILaunchConfigurationListener {

	public void launchConfigurationAdded(ILaunchConfiguration configuration) {
		try {
			setDefaultVMArgs(configuration);
		} catch (CoreException ex) {
			DevToolsPlugin.getDefault().showError(ex);
		}
	}

	private void setDefaultVMArgs(ILaunchConfiguration configuration) throws CoreException {
		IWorkbenchWindow activeWindow = DevToolsPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
		IEditorPart activeEditor = activeWindow.getActivePage().getActiveEditor();
		IResource resource = (IResource) activeEditor.getEditorInput().getAdapter(IResource.class);
		String defaultVMArgs = resource.getProject().getPersistentProperty(PropertyConstants.DEFAULT_VM_ARGS_PROPERTY);

		if (defaultVMArgs != null) {
			ILaunchConfigurationWorkingCopy workingCopy = configuration.getWorkingCopy();
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, defaultVMArgs);
			workingCopy.doSave();
		}
	}

	public void launchConfigurationChanged(ILaunchConfiguration configuration) {
	}

	public void launchConfigurationRemoved(ILaunchConfiguration configuration) {
	}
}
