package com.pfs.devtools;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

public class DerivedDirectoryMarkerFolderAddedListener implements IResourceChangeListener {
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		DerivedDirectoryMarker marker = new DerivedDirectoryMarker();
		
		if (event.getDelta() != null) {
			IResourceDelta[] children = event.getDelta().getAffectedChildren();

			setAddedFoldersDerived(children, marker);
		}
	}
	
	private void setAddedFoldersDerived(IResourceDelta[] children, DerivedDirectoryMarker marker) {
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if ((children[i].getResource() instanceof IProject)) {
					setAddedFoldersDerived(children[i].getAffectedChildren(), marker);
				} else if ((children[i].getKind() == IResourceDelta.ADDED) && (children[i].getResource() instanceof IFolder)) {
					IFolder folder = (IFolder) children[i].getResource();
					marker.setFolderDerivedIfApplicable(folder);
				}
			}
		}
	}
	
}
