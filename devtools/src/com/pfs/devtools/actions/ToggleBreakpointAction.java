package com.pfs.devtools.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.debug.ui.actions.ToggleBreakpointAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

/**
 * Toggle Breakpoint is apparently broken in M5...
 */
public class ToggleBreakpointAction extends DevToolsAction {
	private ToggleBreakpointAdapter adapter;

	public ToggleBreakpointAction() {
		adapter = new ToggleBreakpointAdapter();
	}

	protected void onRun(IAction action) throws Exception {
		IEditorPart activeEditor = getActiveEditor();

		if (activeEditor != null) {
			ISelection selection = getWindow().getSelectionService().getSelection();

			if (selection != null) {
				toggleBreakpoint(activeEditor, selection);
			}
		}
	}

	private void toggleBreakpoint(IEditorPart activeEditor, ISelection selection) throws CoreException {
		if (adapter.canToggleLineBreakpoints(activeEditor, selection)) {
			adapter.toggleLineBreakpoints(activeEditor, selection);
		} else if (adapter.canToggleWatchpoints(activeEditor, selection)) {
			adapter.toggleWatchpoints(activeEditor, selection);
		}
	}
}
