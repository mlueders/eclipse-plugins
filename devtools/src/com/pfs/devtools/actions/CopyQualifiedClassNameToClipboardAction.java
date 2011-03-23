/*
 * Created on Feb 8, 2005
 * @author mike
 */
package com.pfs.devtools.actions;

import com.pfs.base.PluginSupport;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class CopyQualifiedClassNameToClipboardAction extends DevToolsAction {

	private static final Transfer[] TEXT_TRANSFER = new Transfer[] { TextTransfer.getInstance() };
	
	private Clipboard clipboard;

	public void init(IWorkbenchWindow window) {
		super.init(window);

		clipboard = new Clipboard(window.getShell().getDisplay());
	}

	public void dispose() {
		super.dispose();

		if (clipboard != null) {
			clipboard.dispose();
		}
	}

	protected void onRun(IAction action) throws Exception {
		IEditorPart editor = getActiveEditor();
		ICompilationUnit unit = PluginSupport.getCompilationUnit(editor);

		if (unit != null) {
			IType type = unit.getTypes()[0];
			Object[] data = new Object[] { type.getFullyQualifiedName() };
			clipboard.setContents(data, TEXT_TRANSFER);
		}
	}

}
