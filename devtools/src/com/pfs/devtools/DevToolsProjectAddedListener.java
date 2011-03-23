/*
 * Created on Feb 10, 2005
 * @author mike
 */
package com.pfs.devtools;

import com.pfs.devtools.preferences.PreferenceConstants;
import com.pfs.devtools.properties.PropertyConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;

public class DevToolsProjectAddedListener implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getDelta() != null) {
			IResourceDelta[] children = event.getDelta().getAffectedChildren();

			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					if ((children[i].getKind() == IResourceDelta.ADDED) && (children[i].getResource() instanceof IProject)) {
						projectAdded((IProject) children[i].getResource());
					}
				}
			}
		}
	}

	private void projectAdded(IProject project) {
		String defaultValue = DevToolsPlugin.getDefault().getStringPreference(PreferenceConstants.INITIAL_DEFAULT_VM_ARGS);

		try {
			project.setPersistentProperty(PropertyConstants.DEFAULT_VM_ARGS_PROPERTY, defaultValue);
		} catch (CoreException ex) {
			DevToolsPlugin.getDefault().showError(ex);
		}
	}
}
