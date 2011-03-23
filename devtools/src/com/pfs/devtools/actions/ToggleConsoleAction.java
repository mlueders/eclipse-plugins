/*
 * Created on Feb 15, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.internal.WorkbenchPage;

public abstract class ToggleConsoleAction extends ToggleViewAction {
	public static class Minimize extends ToggleConsoleAction {

		@Override
		protected void toggleView(WorkbenchPage page, IViewPart view, IViewReference viewRef) {
			toggleMinimize(page, view, viewRef);
		}
	}

	public static class Maximize extends ToggleConsoleAction {

		@Override
		protected void toggleView(WorkbenchPage page, IViewPart view, IViewReference viewRef) {
			toggleMaximize(page, view, viewRef);
		}
	}

	protected IViewReference getViewReference(IWorkbenchPage activePage) {
		return activePage.findViewReference(IConsoleConstants.ID_CONSOLE_VIEW);
	}
}