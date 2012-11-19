package com.pfs.devtools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.pfs.devtools.preferences.PreferenceConstants;

public class DerivedDirectoryMarker {
	
	private List<String> derivedFolderNames;
	
	public DerivedDirectoryMarker() {
		derivedFolderNames = getDerivedFolderNamesListFromWorkspacePreferences();
	}
	
	private List<String> getDerivedFolderNamesListFromWorkspacePreferences() {
		String derivedFolderNamesString = DevToolsPlugin.getDefault().getStringPreference(PreferenceConstants.DERIVED_FOLDER_NAMES);
		String[] derivedFolderNames = derivedFolderNamesString.split("\\s*,\\s*");
		return Arrays.asList(derivedFolderNames);
	}
	
	public void setFoldersInProjectDerivedIfApplicable(IProject project) {
		List<IFolder> folders = getFoldersInProject(project);
		
		for (IFolder folder : folders) {
			setFolderDerivedIfApplicable(folder);
		}
	}
	
	private List<IFolder> getFoldersInProject(IProject project) {
		ArrayList<IFolder> folders = new ArrayList<IFolder>();
		
		try {
			for (IResource resource : project.members()) {
				if (resource instanceof IFolder) {
					folders.add((IFolder) resource);
				}
			}
		} catch (CoreException ex) {
			DevToolsPlugin.getDefault().logError("Failed to retrieve members of project=" + project.getName(), ex);
		}
		return folders;
	}
	
	public void setFolderDerivedIfApplicable(IFolder folder) {
		if (shouldMarkDerived(folder)) {
			markDerived(folder);
		}
	}
	
	private void markDerived(IFolder folder) {
		try {
			folder.setDerived(true);
		} catch (CoreException ex) {
			DevToolsPlugin.getDefault().logError("Failed to mark folder [" + folder.getFullPath() + "] as derived", ex);
		}
	}
	
	private boolean shouldMarkDerived(IFolder folder) {
		String relativePath = folder.getProjectRelativePath().toString();
		return derivedFolderNames.contains(relativePath);
	}
	
}
