/*
 * Created on Feb 16, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jdt.internal.ui.actions.OpenTypeAction;
import org.eclipse.jface.action.IAction;

/**
 * Not sure why, but the OpenType action doesn't seem to work in a perspective that's not the standard Java or Debug.
 */
public class OpenTypeAnywhereAction extends DevToolsAction {
	private OpenTypeAction openTypeAction;

	public OpenTypeAnywhereAction() {
		openTypeAction = new OpenTypeAction();
	}

	protected void onRun(IAction action) {
		openTypeAction.run(action);
	}
}
