package com.pfs.devtools.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;

import com.pfs.devtools.DerivedDirectoryMarker;

public class MarkDerivedDirectoriesInWorkspaceAction extends DevToolsAction {

	@Override
	protected void onRun(IAction action) throws Exception {
		DerivedDirectoryMarker marker = new DerivedDirectoryMarker();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        for (IProject project : workspace.getRoot().getProjects()) {
            if (project.isOpen()) {
            	marker.setFoldersInProjectDerivedIfApplicable(project);
            }
        }        
	}

}
