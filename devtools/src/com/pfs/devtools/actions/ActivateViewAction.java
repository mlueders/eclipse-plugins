package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;

public class ActivateViewAction extends DevToolsAction {
	public static class EditorList extends ActivateViewAction {

		public EditorList() {
			super("org.eclipse.ui.views.EditorList");
		}
	}

	private String viewId;

	public ActivateViewAction(String viewId) {
		this.viewId = viewId;
	}

	protected void onRun(IAction action) throws Exception {
		IViewPart view = getWindow().getActivePage().findView(viewId);

		if (view != null) {
			getWindow().getActivePage().activate(view);
		}
	}
}
