/*
 * Created on Feb 20, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;

public class ActivatePerspectiveAction extends DevToolsAction {
	public static class MyPerspective extends ActivatePerspectiveAction {

		public MyPerspective() {
			super("com.pfs.lueders.MyPerspective");
		}
	}

	private String perspectiveId;

	public ActivatePerspectiveAction(String perspectiveId) {
		this.perspectiveId = perspectiveId;
	}

	protected void onRun(IAction action) throws Exception {
		IWorkbench workbench = getWindow().getWorkbench();
		IPerspectiveDescriptor perspective = workbench.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);

		if (perspective != null)
			getWindow().getActivePage().setPerspective(perspective);
	}
}
