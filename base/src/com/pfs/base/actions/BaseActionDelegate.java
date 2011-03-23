/*
 * Created on Feb 5, 2005
 * @author mike
 */
package com.pfs.base.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

import com.pfs.base.BasePlugin;

public abstract class BaseActionDelegate extends ActionDelegate implements IWorkbenchWindowActionDelegate,
		IViewActionDelegate {

	protected abstract BasePlugin getPlugin();

	protected abstract void onRun(IAction action) throws Exception;

	private IWorkbenchWindow window;

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void init(IViewPart view) {
		window = view.getSite().getWorkbenchWindow();
	}

	protected IWorkbenchWindow getWindow() {
		return window;
	}

	protected Shell getShell() {
		return getWindow().getShell();
	}

	protected IPreferenceStore getPreferences() {
		return getPlugin().getPreferenceStore();
	}

	protected void logInfo(Object message) {
		getPlugin().logInfo(message);
	}

	protected void logError(Exception ex) {
		getPlugin().logError(ex);
	}

	protected void logError(String message, Exception ex) {
		getPlugin().logError(message, ex);
	}

	protected void showInfo(String message) {
		getPlugin().showInfo(getShell(), message);
	}

	protected void showError(Exception ex) {
		getPlugin().showError(getShell(), ex);
	}

	protected IEditorPart getActiveEditor() {
		IEditorPart activeEditor = getWindow().getActivePage().getActiveEditor();

		return ((activeEditor == null) || (activeEditor.getEditorInput() == null)) ? null : activeEditor;
	}

	protected ITextEditor getActiveTextEditor() {
		IEditorPart activeEditor = getActiveEditor();

		return activeEditor instanceof ITextEditor ? (ITextEditor) activeEditor : null;
	}

	/**
	 * The action has been activated. The argument of the method represents the 'real' action sitting in the workbench
	 * UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
			onRun(action);
		} catch (Exception ex) {
			showError(ex);
		}
	}
}
