/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;

public class ShowInPackageExplorerAction extends DevToolsAction implements IViewActionDelegate {

	protected void onRun(IAction action) throws Exception {
		IEditorPart editor = getActiveEditor();

		if (editor != null) {
			IEditorInput input = editor.getEditorInput();

			Object resource = input.getAdapter(IJavaElement.class);

			if (resource == null) {
				resource = input.getAdapter(IFile.class);
			}

			if (resource != null) {
				PackageExplorerPart.openInActivePerspective().selectAndReveal(resource);
			}
		}
	}
}
