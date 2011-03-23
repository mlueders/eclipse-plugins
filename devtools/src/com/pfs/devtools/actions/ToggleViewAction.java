/*
 * Created on Feb 20, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.FastViewManager;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.presentations.IStackPresentationSite;

public abstract class ToggleViewAction extends DevToolsAction {

	protected abstract IViewReference getViewReference(IWorkbenchPage activePage);

	protected abstract void toggleView(WorkbenchPage page, IViewPart view, IViewReference viewRef);

	protected void onRun(IAction action) throws Exception {
		IWorkbenchPage activePage = getWindow().getActivePage();
		IViewReference activeViewRef = getViewReference(activePage);

		if (activeViewRef != null) {
			IViewPart activeView = activeViewRef.getView(true);

			if (activeView != null) {
				toggleView((WorkbenchPage) activePage, activeView, activeViewRef);
			}
		}
	}

	protected void toggleMinimize(WorkbenchPage page, IViewPart view, IViewReference viewRef) {
		FastViewManager fastViewMgr = page.getActivePerspective().getFastViewManager();

		String id = fastViewMgr.getIdForRef(viewRef);

		if (id == null) {
			page.setState(viewRef, IStackPresentationSite.STATE_MINIMIZED);
		} else {
			fastViewMgr.restoreToPresentation(id);
		}
	}

	protected void toggleMaximize(WorkbenchPage page, IViewPart view, IViewReference viewRef) {
		if (page.isPartVisible(view) == false) {
			toggleMinimize(page, view, viewRef);
		}

		if (page.isZoomed() == false) {
			page.toggleZoom(viewRef);
		}
	}
}
