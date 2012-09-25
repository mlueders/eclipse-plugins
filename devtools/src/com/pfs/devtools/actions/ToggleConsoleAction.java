/*
 * Created on Feb 15, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.presentations.IStackPresentationSite;

public abstract class ToggleConsoleAction extends DevToolsAction {
	public static class Minimize extends ToggleConsoleAction {

		@Override
		protected int getToggleState() {
			return IStackPresentationSite.STATE_MINIMIZED;
		}
	}

	public static class Maximize extends ToggleConsoleAction {

		@Override
		protected int getToggleState() {
			return IStackPresentationSite.STATE_MAXIMIZED;
		}
	}

	
	protected abstract int getToggleState();
	
	private IViewReference getConsoleReference(IWorkbenchPage activePage) {
		return activePage.findViewReference(IConsoleConstants.ID_CONSOLE_VIEW);
	}
	
	protected void onRun(IAction action) throws Exception {
		IWorkbenchPage activePage = getWindow().getActivePage();
		IViewReference activeViewRef = getConsoleReference(activePage);

		if (activeViewRef != null) {
			toggleView((WorkbenchPage) activePage, activeViewRef);
		}
	}
	
	private void toggleView(WorkbenchPage page, IViewReference viewRef) {
		int toggleState = getToggleState();
		int currentState = page.getPartState(viewRef);
		if (currentState != IStackPresentationSite.STATE_RESTORED) {
			page.setPartState(viewRef, IStackPresentationSite.STATE_RESTORED);
		} else {
			page.setPartState(viewRef, toggleState);
		}
	}
}